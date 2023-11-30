package org.bh.uifxhelpercore.editor;

import org.bh.uifxhelpercore.table.ViewType;

/**
 * Builder class for BasicEditorUi
 * @param <TABLE_OBJECT>
 */
public class BasicEditorUIBuilder<TABLE_OBJECT> {
    private Class<TABLE_OBJECT> tableObjectClass;
    private ViewType viewType;
    private String tableDescriptor;

    public BasicEditorUIBuilder(Class<TABLE_OBJECT> tableObjectClass) {
        this.tableObjectClass = tableObjectClass;
        viewType = ViewType.Default;
        tableDescriptor = "";
    }

    public BasicEditorUIBuilder<TABLE_OBJECT> setViewType(ViewType viewType) {
        this.viewType = viewType;
        return this;
    }

    public BasicEditorUIBuilder<TABLE_OBJECT> setTableDescriptor(String tableDescriptor) {
        this.tableDescriptor = tableDescriptor;
        return this;
    }

    public BasicEditorUi<TABLE_OBJECT> build() {
        return new BasicEditorUi<TABLE_OBJECT>(tableObjectClass, viewType, tableDescriptor);
    }



}
