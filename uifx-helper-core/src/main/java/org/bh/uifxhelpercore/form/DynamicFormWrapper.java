package org.bh.uifxhelpercore.form;

import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.util.ResourceBundleService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DynamicFormWrapper<T> extends FormWrapper {

    private Class<T> formClass;

    public DynamicFormWrapper(ResourceBundleService resourceBundleService, Class<T> formClass) {
        super(resourceBundleService);

        this.formClass = formClass;
        buildDynamicForm(formClass);
    }

    public void buildDynamicForm(Class<?> formObject) {
        formDynamicData.parseFormObjectAsFields(formObject);

        FormObject formData = formObject.getAnnotation(FormObject.class);
        String formTitle = "form_label";
        if (formData != null) {
            formTitle = formData.formTitle();
        }
        form = Form.of(
                formDynamicData.getGroupOfDynamicData()
        ).title(formTitle).i18n(resourceBundleService);

        buildForm();
    }

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
                Method setter = formClass.getMethod("set" + declaredField.getName().substring(0, 1).toUpperCase() + declaredField.getName().substring(1), declaredField.getType());
                setter.invoke(result, value);
            }

            return result;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
