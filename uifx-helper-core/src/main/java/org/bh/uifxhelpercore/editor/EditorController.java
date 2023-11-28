package org.bh.uifxhelpercore.editor;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @param <B>
 * @param <T>
 */
public abstract class EditorController<B, T> {

    protected BasicEditorForm form;

    private T selectedEntity;
    private B selectedObject;

    private Map<B, T> businessToEntityMap;

    private boolean enableSearch = false;

    public EditorController(ResourceBundleService resourceBundleService) throws IOException {
        form = new BasicEditorForm(resourceBundleService);
        businessToEntityMap = new HashMap<>();

        initialiseTableListeners();
    }

    protected void loadBusinessToEntityMap(Map<B, T> businessToEntityMap) {
        this.businessToEntityMap = businessToEntityMap;
    }

    protected void enableSearch(boolean enableSearch) {
        this.enableSearch = enableSearch;
    }

    private void initialiseTableListeners() {
        form.getTable().setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getSource() == form.getTable()) { // handle on table click.
                ObservableList<B> selectedItems = form.getTable().getSelectionModel().getSelectedItems();
                if (selectedItems.size() == 1) {
                    B businessObject = selectedItems.get(0);
                    T entityObject = businessToEntityMap.get(businessObject);
                    selectedObject = businessObject;
                    selectedEntity = entityObject;
                    itemSelectedNotification();
                }
            }
        });
    }

    protected void unselectData() {
        form.getTable().getSelectionModel().clearSelection();
        selectedObject = null;
        selectedEntity = null;
        itemSelectedNotification();
    }

    protected void removeActualSelectedItemFromMap() {
        businessToEntityMap.remove(selectedObject);
        form.getTable().getItems().remove(selectedObject);
        selectedObject = null;
        selectedEntity = null;
        itemSelectedNotification();
    }

    protected void addActualSelectedItemToMap(B businessObject, T entityObject){
        if (this.selectedObject != null) {
            form.getTable().getItems().remove(this.selectedObject);
        }
        this.selectedObject = businessObject;
        this.selectedEntity = entityObject;
        businessToEntityMap.put(businessObject, entityObject);
        form.getTable().getItems().add(businessObject);
        itemSelectedNotification();
    }

    public T getSelectedEntity() {
        return selectedEntity;
    }

    protected abstract void loadAllData();

    protected abstract void itemSelectedNotification();

    protected abstract void initialiseTableHeaders();

    protected abstract void initialiseForm();

    public Node getRoot() {
        return form.getRootPane();
    }

}
