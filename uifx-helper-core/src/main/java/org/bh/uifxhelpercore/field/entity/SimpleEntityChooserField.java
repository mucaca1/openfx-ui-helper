package org.bh.uifxhelpercore.field.entity;

import com.dlsc.formsfx.model.event.FieldEvent;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.util.BindingMode;
import com.dlsc.formsfx.model.validators.Validator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SimpleEntityChooserField<V> extends Field<SimpleEntityChooserField<V>> {

    protected final ListProperty<V> persistentSelection = new SimpleListProperty<V>(FXCollections.observableArrayList());
    protected final ListProperty<V> selection = new SimpleListProperty<V>(FXCollections.observableArrayList());
    protected final List<Validator<V>> validators = new ArrayList<>();

    protected final boolean singleSelection;

    private ActionListener selectEntityAction;

    public SimpleEntityChooserField(ListProperty<V> selection, boolean singleSelection) {
        super();
        this.singleSelection = singleSelection;

        this.selection.addAll(selection);
        persistentSelection.setValue(this.selection.getValue());

        changed.bind(Bindings.createBooleanBinding(() -> !persistentSelection.equals(this.selection), this.selection, persistentSelection));

        this.selection.addListener((observable, oldValue, newValue) -> validate());

        renderer = new SimpleEntitySelectionControl<>();
    }
    
    public void setBindingMode(BindingMode newValue) {
        if (BindingMode.CONTINUOUS.equals(newValue)) {
            selection.addListener(bindingModeListener);
        } else {
            selection.removeListener(bindingModeListener);
        }
    }

    public void persist() {
        if (!isValid()) {
            return;
        }

        persistentSelection.setValue(selection.getValue());

        fireEvent(FieldEvent.fieldPersistedEvent(this));
    }

    public void reset() {
        if (!hasChanged()) {
            return;
        }

        selection.setValue(persistentSelection.getValue());

        fireEvent(FieldEvent.fieldResetEvent(this));
    }

    public boolean validate() {
        // Check all validation rules and collect any error messages.
        return true;
    }

    public ObservableList<V> getSelectionItems() {
        return selection.get();
    }

    public SimpleEntityChooserField<V> bind(ListProperty<V> selectionBinding) {
        selection.bindBidirectional(selectionBinding);
        return this;
    }

    public SimpleEntityChooserField<V> registerSelectionAction(ActionListener e) {
        selectEntityAction = e;
        return this;
    }

    public void openSelectionPopup() {
        if (selectEntityAction != null) {
            selectEntityAction.actionPerformed(null);
        }
    }

}
