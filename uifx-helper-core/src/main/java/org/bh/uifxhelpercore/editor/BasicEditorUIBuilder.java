package org.bh.uifxhelpercore.editor;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import org.bh.uifxhelpercore.table.ViewType;

import java.util.ResourceBundle;

/**
 * Builder class for BasicEditorUi
 * @param <TABLE_OBJECT>
 */
public class BasicEditorUIBuilder<TABLE_OBJECT> {
    private Class<TABLE_OBJECT> tableObjectClass;
    private ViewType viewType;
    private String tableDescriptor;
    private ResourceBundleService resourceBundle;
    private boolean multiSelection;

    public BasicEditorUIBuilder(Class<TABLE_OBJECT> tableObjectClass) {
        this.tableObjectClass = tableObjectClass;
        viewType = ViewType.Default;
        tableDescriptor = "";
        resourceBundle = null;
        multiSelection = false;
    }

    public BasicEditorUIBuilder<TABLE_OBJECT> setViewType(ViewType viewType) {
        this.viewType = viewType;
        return this;
    }

    public BasicEditorUIBuilder<TABLE_OBJECT> setTableDescriptor(String tableDescriptor) {
        this.tableDescriptor = tableDescriptor;
        return this;
    }

    public BasicEditorUIBuilder<TABLE_OBJECT> setResourceBundle(ResourceBundleService resourceBundle) {
        this.resourceBundle = resourceBundle;
        return this;
    }

    public BasicEditorUIBuilder<TABLE_OBJECT> setMultiSelection(boolean multiSelection) {
        this.multiSelection = multiSelection;
        return this;
    }

    public BasicEditorUi<TABLE_OBJECT> build() {
        return new BasicEditorUi<TABLE_OBJECT>(tableObjectClass,
                viewType,
                tableDescriptor,
                resourceBundle,
                multiSelection);
    }



}
