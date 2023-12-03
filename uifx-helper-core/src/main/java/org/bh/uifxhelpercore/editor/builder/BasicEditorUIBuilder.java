package org.bh.uifxhelpercore.editor.builder;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import org.bh.uifxhelpercore.editor.BasicEditorUi;
import org.bh.uifxhelpercore.editor.ObjectTranslator;
import org.bh.uifxhelpercore.table.ViewType;

/**
 * Builder class for BasicEditorUi
 * @param <TABLE_OBJECT>
 */
public class BasicEditorUIBuilder<TABLE_OBJECT, FORM_OBJECT> {
    private Class<TABLE_OBJECT> tableObjectClass;
    private Class<FORM_OBJECT> formObjectClass;

    private ObjectTranslator<TABLE_OBJECT, FORM_OBJECT> objectTranslator;
    private ViewType viewType;
    private String tableDescriptor;
    private ResourceBundleService tableResourceBundle;
    private ResourceBundleService formResourceBundle;
    private boolean multiSelection;

    private boolean initFormDynamic;

    private boolean showForm;

    public BasicEditorUIBuilder(Class<TABLE_OBJECT> tableObjectClass, Class<FORM_OBJECT> formObjectClass, ObjectTranslator<TABLE_OBJECT, FORM_OBJECT> objectTranslator) {
        this.objectTranslator = objectTranslator;
        this.tableObjectClass = tableObjectClass;
        this.formObjectClass = formObjectClass;
        viewType = ViewType.Default;
        tableDescriptor = "";
        tableResourceBundle = null;
        formResourceBundle = null;
        multiSelection = false;
        initFormDynamic = false;
        showForm = false;
    }

    public BasicEditorUIBuilder<TABLE_OBJECT, FORM_OBJECT> setShowForm(boolean showForm) {
        this.showForm = showForm;
        return this;
    }

    public BasicEditorUIBuilder<TABLE_OBJECT, FORM_OBJECT> setInitFormDynamic(boolean initFormDynamic) {
        this.initFormDynamic = initFormDynamic;
        return this;
    }

    public BasicEditorUIBuilder<TABLE_OBJECT, FORM_OBJECT> setViewType(ViewType viewType) {
        this.viewType = viewType;
        return this;
    }

    public BasicEditorUIBuilder<TABLE_OBJECT, FORM_OBJECT> setTableDescriptor(String tableDescriptor) {
        this.tableDescriptor = tableDescriptor;
        return this;
    }

    public BasicEditorUIBuilder<TABLE_OBJECT, FORM_OBJECT> setTableResourceBundle(ResourceBundleService resourceBundle) {
        this.tableResourceBundle = resourceBundle;
        return this;
    }
    public BasicEditorUIBuilder<TABLE_OBJECT, FORM_OBJECT> setFormResourceBundle(ResourceBundleService resourceBundle) {
        this.formResourceBundle = resourceBundle;
        return this;
    }

    public BasicEditorUIBuilder<TABLE_OBJECT, FORM_OBJECT> setMultiSelection(boolean multiSelection) {
        this.multiSelection = multiSelection;
        return this;
    }

    public BasicEditorUi<TABLE_OBJECT, FORM_OBJECT> build() {
        return new BasicEditorUi<TABLE_OBJECT, FORM_OBJECT>(tableObjectClass,
                formObjectClass,
                objectTranslator,
                viewType,
                tableDescriptor,
                tableResourceBundle,
                formResourceBundle,
                multiSelection,
                initFormDynamic,
                showForm);
    }



}