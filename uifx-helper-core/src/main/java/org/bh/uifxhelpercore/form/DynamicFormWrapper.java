package org.bh.uifxhelpercore.form;

import com.dlsc.formsfx.model.structure.Element;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.util.ResourceBundleService;
import org.bh.uifxhelpercore.form.builder.FormBuilder;
import org.bh.uifxhelpercore.locale.LocalizationHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicFormWrapper<T> extends FormWrapper<T> {

    private final Class<T> formClass;

    protected FormDynamicData formDynamicData = new FormDynamicData();

    private Map<String, com.dlsc.formsfx.model.structure.Field<?>> fieldByFieldId;


    public DynamicFormWrapper(FormBuilder<T> builder) {
        this.resourceBundleService = builder.getResourceBundleService();
        if (this.resourceBundleService == null && LocalizationHelper.get().getDefaultFormBundleService() != null) {
            this.resourceBundleService = LocalizationHelper.get().getDefaultFormBundleService();
        }

        this.fieldByFieldId = new HashMap<>();
        this.formClass = builder.getFormObject();

        builder.getFormFieldMappers().forEach((s, fieldTypeValueMapper) -> {
            getFormDynamicData().registerMapper(s, fieldTypeValueMapper);
        });
        builder.getFormFieldValueMappers().forEach((s, fieldValueMapper) -> {
            getFormDynamicData().registerValueMapper(s, fieldValueMapper);
        });
        buildForm();
        renderForm();
    }

    public void buildForm() {
        buildDynamicFormForClass(formClass);
    }

    private void buildDynamicFormForClass(Class<?> formObject) {
        formDynamicData.parseFormObjectAsFields(formObject);

        FormObject formData = formObject.getAnnotation(FormObject.class);
        String formTitle = "form_title";
        if (formData != null) {
            formTitle = formData.formTitle();
        }
        List<Group> groups = formDynamicData.getGroupOfDynamicData(formObject.getDeclaredFields());
        for (Group group : groups) {
            for (Element<?> element : group.getElements()) {
                fieldByFieldId.put(element.getID(), (com.dlsc.formsfx.model.structure.Field<?>) element);
            }
        }
        form = Form.of(
                groups.toArray(new Group[0])
        ).title(formTitle).i18n(resourceBundleService);
    }

    public void clearForm() {
        formDynamicData.clearData();
    }

    public FormDynamicData getFormDynamicData() {
        return formDynamicData;
    }

    @Override
    public T getObjectFromForm() {
        try {
            form.persist();
            T result = null;
            try {
                result = formClass.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            for (Field declaredField : formClass.getDeclaredFields()) {
                FormField formField = declaredField.getAnnotation(FormField.class);
                if (formField == null) {
                    continue;
                }
                Object value = formDynamicData.getValueOfField(declaredField.getName());
                String methodName = formField.setter().isBlank() ?
                        "set" + declaredField.getName().substring(0, 1).toUpperCase() + declaredField.getName().substring(1)
                        : formField.setter();
                try {
                    Method setter = formClass.getMethod(methodName, declaredField.getType());
                    setter.invoke(result, value);
                } catch (NoSuchMethodException e) {
                    System.out.println("No method " + methodName + " for field: " + formField.fieldName());
                    throw new RuntimeException(e);
                }
            }

            return result;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setFormDataFromObject(T object) {
        if (object == null) {
            return;
        }
        for (Field declaredField : formClass.getDeclaredFields()) {
            FormField formField = declaredField.getAnnotation(FormField.class);
            if (formField == null) {
                continue;
            }
            Method getter = null;
            try {
                getter = formClass.getMethod(
                        formField.getter().isBlank() ?
                        "get" + declaredField.getName().substring(0, 1).toUpperCase() + declaredField.getName().substring(1)
                        : formField.getter());
                Object value = getter.invoke(object);
                formDynamicData.setValueOfField(declaredField.getName(), value);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Map<String, com.dlsc.formsfx.model.structure.Field<?>> getFieldByFieldId() {
        return fieldByFieldId;
    }
}
