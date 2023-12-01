package org.bh.uifxhelpercore.form;

import com.dlsc.formsfx.model.structure.Element;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.Section;
import javafx.beans.property.*;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import org.bh.uifxhelpercore.table.TableColumn;

import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormDynamicData {

    private Map<String, ObservableValue<?>> data;

    public FormDynamicData() {
        data = new HashMap<>();
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
            ObservableValue<?> property = null;

            switch (formField.type()) {
                case STRING:
                    property = new SimpleStringProperty("");
                    break;
                case INTEGER:
                    property = new SimpleIntegerProperty(0);
                    break;
            }

            if (property == null) {
                continue;
            }
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
