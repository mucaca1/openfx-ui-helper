package org.bh.uifxhelpercore.editor;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @param <B>
 * @param <T>
 */
public abstract class EditorController<B, T> {

    protected BasicEditorUi form;

    private T selectedEntity;
    private B selectedObject;

    private Map<B, T> businessToEntityMap;

    private boolean enableSearch = false;

    public EditorController() throws IOException {
//        form = new BasicEditorUi();
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
        form.getDataBrowser().setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getSource() == form.getDataBrowser()) { // handle on table click.
                ObservableList<B> selectedItems = form.getDataBrowser().getSelectionModel().getSelectedItems();
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
        form.getDataBrowser().getSelectionModel().clearSelection();
        selectedObject = null;
        selectedEntity = null;
        itemSelectedNotification();
    }

    protected void removeActualSelectedItemFromMap() {
        businessToEntityMap.remove(selectedObject);
        form.getDataBrowser().getItems().remove(selectedObject);
        selectedObject = null;
        selectedEntity = null;
        itemSelectedNotification();
    }

    protected void addActualSelectedItemToMap(B businessObject, T entityObject) {
        if (this.selectedObject != null) {
            form.getDataBrowser().getItems().remove(this.selectedObject);
        }
        this.selectedObject = businessObject;
        this.selectedEntity = entityObject;
        businessToEntityMap.put(businessObject, entityObject);
        form.getDataBrowser().getItems().add(businessObject);
        itemSelectedNotification();
    }

    public T getSelectedEntity() {
        return selectedEntity;
    }

    protected abstract void loadAllData();

    protected abstract void itemSelectedNotification();

    protected abstract void initialiseTableHeaders();

    protected abstract void initialiseForm();

}
