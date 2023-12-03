package org.bh.uifxhelpercore.form;

import com.dlsc.formsfx.model.structure.Element;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Group;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormDynamicData {

    private Map<String, ObservableValue<?>> data;

    private Map<String, Object> initFormData;

    private Map<String, FieldTypeValueMapper> valueMappers = new HashMap<>();

    public FormDynamicData() {
        data = new HashMap<>();
        initFormData = new HashMap<>();
        valueMappers = new HashMap<>();
    }

    public void registerMapper(String fieldName, FieldTypeValueMapper mapper) {
        valueMappers.put(fieldName, mapper);
    }

    public void parseFormObjectAsFields(Class<?> formObject) {
        for (java.lang.reflect.Field field : formObject.getDeclaredFields()) {
            FormField formField = field.getAnnotation(FormField.class);
            if (formField == null) {
                continue;
            }
            if (data.containsKey(field.getName())) {
                continue;
            }
            ObservableValue<?> property = switch (formField.type()) {
                case STRING -> new SimpleStringProperty("");
                case INTEGER -> new SimpleIntegerProperty(0);
                case BOOLEAN -> new SimpleBooleanProperty();
                case USER_DEFINED -> valueMappers.get(field.getName()).getValueFromField(field);
            };

            data.put(field.getName(), property);
            initFormData.put(field.getName(), property.getValue());
        }
    }

    public void clearData() {
        for (String s : data.keySet()) {
            setValueOfField(s, initFormData.get(s));
        }
    }

    public Group getGroupOfDynamicData(java.lang.reflect.Field[] fields) {
        List<Element<?>> elements = new ArrayList<>();
        for (java.lang.reflect.Field field : fields) {
            if (!data.containsKey(field.getName())) {
                continue;
            }
            ObservableValue<?> property = data.get(field.getName());
            FormField formField = field.getAnnotation(FormField.class);
            String fieldName = formField.fieldName().isBlank() ? field.getName() : formField.fieldName();
            if (property instanceof SimpleStringProperty) {
                elements.add(Field.ofStringType((StringProperty) property).label(fieldName));
            } else if (property instanceof SimpleIntegerProperty) {
                elements.add(Field.ofIntegerType(((IntegerProperty) property)).label(fieldName));
            } else if (property instanceof SimpleBooleanProperty) {
                elements.add(Field.ofBooleanType(((SimpleBooleanProperty) property)).label(fieldName));
            } else if (valueMappers.containsKey(field.getName())) {
                elements.add(valueMappers.get(field.getName()).getElement(formField, property));
            } else {
                throw new RuntimeException("Property for field [" + fieldName + "] does not implemented! Property can not be added to elements. Implement code for " + property.getClass().getName() + " class");
            }
        }

        return Group.of(
                elements.toArray(new Element[0])
        );
    }

    public void setValueOfField(String fieldName, Object value) {
        ObservableValue<?> observableValue = data.get(fieldName);
        if (observableValue instanceof SimpleStringProperty) {
            ((SimpleStringProperty) observableValue).set((String) value);
        } else if (observableValue instanceof SimpleIntegerProperty) {
            ((SimpleIntegerProperty) observableValue).set((Integer) value);
        } else if (observableValue instanceof SimpleBooleanProperty) {
            ((SimpleBooleanProperty) observableValue).set((Boolean) value);
        } else if (valueMappers.containsKey(fieldName)) {
            valueMappers.get(fieldName).setValue(observableValue, value);
        } else {
            throw new RuntimeException("Field [" + fieldName + "] does not implemented setter. Implement setter for " + observableValue.getClass().getName() + " class.");
        }
    }

    public Object getValueOfField(String fieldName) {
        ObservableValue<?> observableValue = data.get(fieldName);
        if (observableValue instanceof SimpleStringProperty) {
            return ((SimpleStringProperty) observableValue).get();
        } else if (observableValue instanceof SimpleIntegerProperty) {
            return ((SimpleIntegerProperty) observableValue).get();
        } else if (observableValue instanceof SimpleBooleanProperty) {
            return ((SimpleBooleanProperty) observableValue).get();
        } else if (valueMappers.containsKey(fieldName)) {
            return valueMappers.get(fieldName).getValue(observableValue);
        } else {
            throw new RuntimeException("Field [" + fieldName + "] does not implemented getter. Implement getter for " + observableValue.getClass().getName() + " class.");
        }
    }

}
