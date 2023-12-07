package org.bh.uifxhelpercore.form;

import com.dlsc.formsfx.model.structure.Element;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.util.ResourceBundleService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicFormWrapper<T> extends FormWrapper<T> {

    private Class<T> formClass;

    private Map<String, com.dlsc.formsfx.model.structure.Field<?>> fieldByFieldId;

    public DynamicFormWrapper(ResourceBundleService resourceBundleService, Class<T> formClass) {
        super(resourceBundleService);

        this.fieldByFieldId = new HashMap<>();
        this.formClass = formClass;
    }

    public void initForm() {
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

    @Override
    public T getObjectFromForm() {
        try {
            form.persist();
            T result = formClass.getDeclaredConstructor().newInstance();

            for (Field declaredField : formClass.getDeclaredFields()) {
                FormField formField = declaredField.getAnnotation(FormField.class);
                if (formField == null) {
                    continue;
                }
                Object value = formDynamicData.getValueOfField(declaredField.getName());
                Method setter =
                        formClass.getMethod(
                                formField.setter().isBlank() ?
                                "set" + declaredField.getName().substring(0, 1).toUpperCase() + declaredField.getName().substring(1)
                                : formField.setter(), declaredField.getType());
                setter.invoke(result, value);
            }

            return result;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setFormDataFromObject(T object) {
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
