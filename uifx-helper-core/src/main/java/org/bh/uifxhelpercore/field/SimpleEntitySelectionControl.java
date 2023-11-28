package org.bh.uifxhelpercore.field;

import com.dlsc.formsfx.view.controls.SimpleControl;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class SimpleEntitySelectionControl<V> extends SimpleControl<SimpleEntityChooserField<V>> {

    protected Label fieldLabel;
    protected Label readOnlyLabel;
    protected StackPane stack;
    protected Button selectEntityButton;
    protected Label dataReadLabel;

    public SimpleEntitySelectionControl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeParts() {
        super.initializeParts();

        getStyleClass().add("simple-select-control");

        fieldLabel = new Label(field.labelProperty().getValue());
        readOnlyLabel = new Label();
        stack = new StackPane();
        dataReadLabel = new Label();
        dataReadLabel.setText("empty");
        selectEntityButton = new Button();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void layoutParts() {
        super.layoutParts();

        int columns = field.getSpan();
        readOnlyLabel.getStyleClass().add("read-only-label");

        selectEntityButton.setMaxWidth(Double.MAX_VALUE);

        stack.setAlignment(Pos.CENTER_LEFT);
        stack.getChildren().addAll(selectEntityButton, readOnlyLabel);

        Node labelDescription = field.getLabelDescription();
        Node valueDescription = field.getValueDescription();

        add(fieldLabel, 0, 0, 2, 1);
        if (labelDescription != null) {
            GridPane.setValignment(labelDescription, VPos.TOP);
            add(labelDescription, 0, 1, 2, 1);
        }
        add(dataReadLabel, 2, 0, columns - 4, 1);
        add(stack, columns - 2, 0, columns - 2, 1);
        if (valueDescription != null) {
            GridPane.setValignment(valueDescription, VPos.TOP);
            add(valueDescription, 2, 1, columns - 2, 1);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupBindings() {
        super.setupBindings();
        selectEntityButton.visibleProperty().bind(field.editableProperty());
        fieldLabel.textProperty().bind(field.labelProperty());
        readOnlyLabel.visibleProperty().bind(field.editableProperty().not());
        dataReadLabel.visibleProperty().bind(field.editableProperty());
//        readOnlyLabel.textProperty().bind(entityPicker.valueProperty().asString()); todo
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupValueChangedListeners() {
        super.setupValueChangedListeners();

        this.field.selection.addListener((observable, oldValue, newValue) -> {
            ObservableList<V> v = this.field.selection.getValue();
            if (v == null) {
                dataReadLabel.setText("");
            } else {
                dataReadLabel.setText(v.toString());
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupEventHandlers() {
        selectEntityButton.setOnMouseClicked(mouseEvent -> {
            field.openSelectionPopup();
        });
    }

}
