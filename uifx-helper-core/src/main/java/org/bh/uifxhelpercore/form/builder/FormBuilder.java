package org.bh.uifxhelpercore.form.builder;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import org.bh.uifxhelpercore.form.DynamicFormWrapper;
import org.bh.uifxhelpercore.form.FormWrapper;

public class FormBuilder<T> {
    private Class<T> formObject;
    private ResourceBundleService resourceBundleService;

    public FormBuilder() {
        formObject = null;
        resourceBundleService = null;
    }

    public FormBuilder<T> setResourceBundleService(ResourceBundleService resourceBundleService) {
        this.resourceBundleService = resourceBundleService;
        return this;
    }

    public FormBuilder<T> createDynamicFormFromObject(Class<T> formObject) {
        this.formObject = formObject;
        return this;
    }

    public FormWrapper<T> build() {
        return new DynamicFormWrapper<>(resourceBundleService, formObject);
    }
}
