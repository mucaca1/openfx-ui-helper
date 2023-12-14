package org.bh.uifxhelpercore.form;

import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.util.ResourceBundleService;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.value.ObservableValue;
import org.bh.uifxhelpercore.FormsFxHelper;

public abstract class FormWrapper<T> {

    protected Form form;
    private FormRenderer formRenderer;

    protected ResourceBundleService resourceBundleService;

    private int labelPercentageSize;

    public FormWrapper(ResourceBundleService resourceBundleService) {
        this.resourceBundleService = resourceBundleService;
        labelPercentageSize = 50;
    }

    public void buildForm() {
        formRenderer = new FormRenderer(form);
        FormsFxHelper.searchAndSetControlsLabelWidth(formRenderer, labelPercentageSize);
    }

    public FormRenderer getFormRenderer() {
        return formRenderer;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Form getForm() {
        return form;
    }

    public void setLabelPercentageSize(int labelPercentageSize) {
        this.labelPercentageSize = labelPercentageSize;
    }

    public abstract T getObjectFromForm();
    public abstract void setFormDataFromObject(T object);
}
