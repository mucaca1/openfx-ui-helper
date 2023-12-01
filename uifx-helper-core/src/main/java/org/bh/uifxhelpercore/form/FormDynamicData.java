package org.bh.uifxhelpercore.form;

import com.dlsc.formsfx.model.structure.Element;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Group;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormDynamicData {

    private Map<String, ObservableValue<?>> data;

    private Map<String,FieldTypeValueMapper> valueMappers = new HashMap<>();

    public FormDynamicData() {
        data = new HashMap<>();
    }

    public void registerMapper(java.lang.reflect.Field field, FieldTypeValueMapper mapper) {
        valueMappers.put(field.getName(), mapper);
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
                case USER_DEFINED -> valueMappers.get(field.getName()).getValueFromField(field);
            };

            data.put(field.getName(), property);
        }
    }

    public Group getGroupOfDynamicData() {
        List<Element<?>> elements = new ArrayList<>();

        data.forEach((fieldName, property) -> {
            if (property instanceof SimpleStringProperty) {
                elements.add(Field.ofStringType((StringProperty) property).label(fieldName));
            } else if (property instanceof SimpleIntegerProperty) {
                elements.add(Field.ofIntegerType(((IntegerProperty) property)).label(fieldName));
            }
        });

        return Group.of(
                elements.toArray(new Element[0])
        );
    }

    public Object getValueOfField(String fieldName) {
        ObservableValue<?> observableValue = data.get(fieldName);
        if (observableValue instanceof SimpleStringProperty) {
            return ((SimpleStringProperty) observableValue).get();
        } else if (observableValue instanceof SimpleIntegerProperty) {
            return ((SimpleIntegerProperty) observableValue).get();
        } else {
            return null;
        }
    }

}
