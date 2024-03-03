package org.bh.uifxhelpercore.form.builder;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import org.bh.uifxhelpercore.form.DynamicFormWrapper;
import org.bh.uifxhelpercore.form.FieldTypeValueMapper;
import org.bh.uifxhelpercore.form.FieldValueMapper;

import java.util.HashMap;
import java.util.Map;

public class FormBuilder<T> {
    private Class<T> formObject;
    private ResourceBundleService resourceBundleService;

    private Map<String, FieldTypeValueMapper> formFieldMappers;

    private Map<String, FieldValueMapper> formFieldValueMappers;

    public FormBuilder(Class<T> formObject) {
        this.formObject = formObject;
        resourceBundleService = null;
        formFieldMappers = new HashMap<>();
        formFieldValueMappers = new HashMap<>();
    }

    public FormBuilder<T> setResourceBundleService(ResourceBundleService resourceBundleService) {
        this.resourceBundleService = resourceBundleService;
        return this;
    }

    public FormBuilder<T> setFormFieldMapper(Map<String, FieldTypeValueMapper> formFieldMappers) {
        this.formFieldMappers = formFieldMappers;
        return this;
    }

    public FormBuilder<T> setFormFieldValueMapper(Map<String, FieldValueMapper> formFieldValueMappers) {
        this.formFieldValueMappers = formFieldValueMappers;
        return this;
    }

    public FormBuilder<T> addFormFieldMapper(String fieldName, FieldTypeValueMapper mapper) {
        formFieldMappers.put(fieldName, mapper);
        return this;
    }

    public FormBuilder<T> addFormFieldValueMapper(String fieldName, FieldValueMapper mapper) {
        formFieldValueMappers.put(fieldName, mapper);
        return this;
    }

    public Map<String, FieldTypeValueMapper> getFormFieldMappers() {
        return formFieldMappers;
    }

    public Map<String, FieldValueMapper> getFormFieldValueMappers() {
        return formFieldValueMappers;
    }

    public Class<T> getFormObject() {
        return formObject;
    }

    public ResourceBundleService getResourceBundleService() {
        return resourceBundleService;
    }

    public DynamicFormWrapper<T> build() {
        return new DynamicFormWrapper<>(this);
    }
}
