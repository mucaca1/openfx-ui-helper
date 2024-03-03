package org.bh.uifxhelpercore.editor.builder;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import org.bh.uifxhelpercore.editor.BasicEditor;
import org.bh.uifxhelpercore.editor.EditorObjectEventHandler;
import org.bh.uifxhelpercore.editor.ObjectTranslator;
import org.bh.uifxhelpercore.form.FieldTypeValueMapper;
import org.bh.uifxhelpercore.form.builder.FormBuilder;

/**
 * Builder class for BasicEditorUi
 * @param <TABLE_OBJECT>
 */
public class BasicEditorBuilder<TABLE_OBJECT, FORM_OBJECT> {
    private Class<TABLE_OBJECT> tableObjectClass;
    private Class<FORM_OBJECT> formObjectClass;

    private FormBuilder<FORM_OBJECT> formBuilder;

    private DataBrowserBuilder<TABLE_OBJECT> dataBrowserBuilder;

    private ObjectTranslator<TABLE_OBJECT, FORM_OBJECT> objectTranslator;
    private ResourceBundleService buttonResourceBundle;
    private boolean showForm;

    private EditorObjectEventHandler<FORM_OBJECT> eventHandler;

    public BasicEditorBuilder(Class<TABLE_OBJECT> tableObjectClass, Class<FORM_OBJECT> formObjectClass, ObjectTranslator<TABLE_OBJECT, FORM_OBJECT> objectTranslator) {
        this.objectTranslator = objectTranslator;
        this.tableObjectClass = tableObjectClass;
        this.formObjectClass = formObjectClass;
        showForm = false;
        formBuilder = new FormBuilder<>(formObjectClass);
        dataBrowserBuilder = new DataBrowserBuilder<>(tableObjectClass);
    }

    public BasicEditorBuilder<TABLE_OBJECT, FORM_OBJECT> setShowForm(boolean showForm) {
        this.showForm = showForm;
        return this;
    }

    public BasicEditorBuilder<TABLE_OBJECT, FORM_OBJECT> setButtonResourceBundle(ResourceBundleService buttonResourceBundle) {
        this.buttonResourceBundle = buttonResourceBundle;
        return this;
    }

    public BasicEditorBuilder<TABLE_OBJECT, FORM_OBJECT> setEditorEventHandler(EditorObjectEventHandler<FORM_OBJECT> eventHandler) {
        this.eventHandler = eventHandler;
        return this;
    }

    public FormBuilder<FORM_OBJECT> getFormBuilder() {
        return formBuilder;
    }

    public BasicEditorBuilder<TABLE_OBJECT, FORM_OBJECT> setFormBuilder(FormBuilder<FORM_OBJECT> formBuilder) {
        this.formBuilder = formBuilder;
        return this;
    }

    public DataBrowserBuilder<TABLE_OBJECT> getDataBrowserBuilder() {
        return dataBrowserBuilder;
    }

    public BasicEditorBuilder<TABLE_OBJECT, FORM_OBJECT> setDataBrowserBuilder(DataBrowserBuilder<TABLE_OBJECT> dataBrowserBuilder) {
        this.dataBrowserBuilder = dataBrowserBuilder;
        return this;
    }

    public Class<TABLE_OBJECT> getTableObjectClass() {
        return tableObjectClass;
    }

    public Class<FORM_OBJECT> getFormObjectClass() {
        return formObjectClass;
    }

    public ObjectTranslator<TABLE_OBJECT, FORM_OBJECT> getObjectTranslator() {
        return objectTranslator;
    }

    public ResourceBundleService getButtonResourceBundle() {
        return buttonResourceBundle;
    }

    public boolean isShowForm() {
        return showForm;
    }

    public EditorObjectEventHandler<FORM_OBJECT> getEventHandler() {
        return eventHandler;
    }

    public BasicEditor<TABLE_OBJECT, FORM_OBJECT> build() {
        return new BasicEditor<TABLE_OBJECT, FORM_OBJECT>(this);
    }

}
