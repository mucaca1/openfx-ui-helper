package org.bh.uifxhelpercore.form;

import com.dlsc.formsfx.model.structure.Element;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.Section;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;
import java.util.*;

/**
 * Class help create and hold form data.
 */
public class FormDynamicData {

    /**
     * Data mapped on form fields.
     */
    private Map<String, ObservableValue<?>> data;

    /**
     * Init data values. Data are kept for clear option.
     */
    private Map<String, Object> initFormData;

    /**
     * Hold customized field data for custom object.
     */
    private Map<String, FieldTypeValueMapper> customFieldMappers;

    private Map<String, FieldValueMapper> customValueMappers = new HashMap<>();

    /**
     * Hold all created form elements (real rendered fields)
     */
    private Map<String, Element<?>> fields = new HashMap<>();


    public FormDynamicData() {
        data = new HashMap<>();
        initFormData = new HashMap<>();
        customFieldMappers = new HashMap<>();
        customValueMappers = new HashMap<>();
        fields = new HashMap<>();
    }

    public void registerMapper(String fieldName, FieldTypeValueMapper mapper) {
        customFieldMappers.put(fieldName, mapper);
    }

    public void registerValueMapper(String fieldName, FieldValueMapper mapper) {
        customValueMappers.put(fieldName, mapper);
    }

    /**
     * Scan for {@link FormField} annotation on {@param formObject}.
     * Take all data from {@link FormField} and create {@link ObservableValue} values and store them.
     * Also create initialization values for form.
     * @param formObject object to parse.
     */
    public void parseFormObjectAsFields(Class<?> formObject) {
        data.clear();
        initFormData.clear();
        for (java.lang.reflect.Field field : formObject.getDeclaredFields()) {
            FormField formField = field.getAnnotation(FormField.class);
            if (formField == null) {
                continue;
            }
            if (data.containsKey(field.getName())) {
                continue;
            }
            ObservableValue<?> property;
            switch (formField.type()) {
                case STRING: property = new SimpleStringProperty(""); break;
                case INTEGER: property = new SimpleIntegerProperty(0); break;
                case DOUBLE: property = new SimpleDoubleProperty(); break;
                case BOOLEAN: property = new SimpleBooleanProperty(); break;
                case DATE: property = new SimpleObjectProperty<LocalDate>(); break;
                case USER_DEFINED: property = customFieldMappers.get(field.getName()).getValueFromField(field); break;
                default: throw new RuntimeException("Unsupported field type: " + formField.type());
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

    /**
     * Return all groups that can be used for creating form.
     * @param fields
     * @return
     */
    public List<Group> getGroupOfDynamicData(java.lang.reflect.Field[] fields) {
        List<Element<?>> elements = new ArrayList<>();
        Map<String, List<Element<?>>> sections = new HashMap<>();
        this.fields.clear();
        for (java.lang.reflect.Field field : fields) {
            if (!data.containsKey(field.getName())) {
                continue;
            }
            ObservableValue<?> property = data.get(field.getName());
            FormField formField = field.getAnnotation(FormField.class);
            List<Element<?>> list = elements;
            if (!formField.section().isEmpty()) {
                if (!sections.containsKey(formField.section())) {
                    sections.put(formField.section(), new ArrayList<>());
                }
                list = sections.get(formField.section());
            }
            String fieldName = formField.fieldName().isBlank() ? field.getName() : formField.fieldName();
            String fieldLabel = formField.fieldLabel().isBlank() ? field.getName() : formField.fieldLabel();
            if (property instanceof SimpleStringProperty) {
                list.add(Field.ofStringType((StringProperty) property).label(fieldLabel).editable(formField.editable()).id(fieldName));
            } else if (property instanceof SimpleIntegerProperty) {
                list.add(Field.ofIntegerType(((IntegerProperty) property)).label(fieldLabel).editable(formField.editable()).id(fieldName));
            } else if (property instanceof SimpleDoubleProperty) {
                list.add(Field.ofDoubleType(((SimpleDoubleProperty) property)).label(fieldLabel).editable(formField.editable()).id(fieldName));
            } else if (property instanceof SimpleBooleanProperty) {
                list.add(Field.ofBooleanType(((SimpleBooleanProperty) property)).label(fieldLabel).editable(formField.editable()).id(fieldName));
            } else if (property instanceof SimpleObjectProperty) {
                if (FieldType.DATE.equals(formField.type())) {
                    list.add(Field.ofDate(((SimpleObjectProperty) property)).label(fieldLabel).editable(formField.editable()).id(fieldName));
                } else {
                    throw new RuntimeException("Property for field [" + fieldName + "] does not implemented! Property can not be added to elements. Implement code for " + property.getClass().getName() + " class");
                }
            } else if (customFieldMappers.containsKey(field.getName())) {
                list.add(customFieldMappers.get(field.getName()).getElement(formField, property).id(fieldName));
            } else {
                throw new RuntimeException("Property for field [" + fieldName + "] does not implemented! Property can not be added to elements. Implement code for " + property.getClass().getName() + " class");
            }
            this.fields.put(fieldName, list.get(list.size() - 1));
        }

        List<Group> result = new ArrayList<>();
        result.add(Group.of(
                elements.toArray(new Element[0])
        ));
        sections.forEach((sectionName, sectionElements) -> {
            result.add(Section.of(sectionElements.toArray(new Element[0])).title(sectionName));
        });

        return result;
    }

    /**
     * Set value to field.
     */
    public void setValueOfField(String fieldName, Object value) {
        ObservableValue<?> observableValue = data.get(fieldName);
        if (customValueMappers.containsKey(fieldName)) {
            customValueMappers.get(fieldName).setValue(observableValue, value);
        } else if (observableValue instanceof SimpleStringProperty) {
            ((SimpleStringProperty) observableValue).set((String) value);
        } else if (observableValue instanceof SimpleIntegerProperty) {
            ((SimpleIntegerProperty) observableValue).set((Integer) value);
        } else if (observableValue instanceof SimpleDoubleProperty) {
            ((SimpleDoubleProperty) observableValue).set((Double) value);
        } else if (observableValue instanceof SimpleBooleanProperty) {
            ((SimpleBooleanProperty) observableValue).set((Boolean) value);
        } else if (observableValue instanceof SimpleObjectProperty) {
            ((SimpleObjectProperty) observableValue).set(value);
        } else if (customFieldMappers.containsKey(fieldName)) {
            customFieldMappers.get(fieldName).setValue(observableValue, value);
        } else {
            throw new RuntimeException("Field [" + fieldName + "] does not implemented setter. Implement setter for " + observableValue.getClass().getName() + " class.");
        }
    }

    /**
     * Return value from field
     */
    public Object getValueOfField(String fieldName) {
        ObservableValue<?> observableValue = data.get(fieldName);
        if (customValueMappers.containsKey(fieldName)) {
            return customValueMappers.get(fieldName).getValue(observableValue);
        } else if (observableValue instanceof SimpleStringProperty) {
            return ((SimpleStringProperty) observableValue).get();
        } else if (observableValue instanceof SimpleIntegerProperty) {
            return ((SimpleIntegerProperty) observableValue).get();
        } else if (observableValue instanceof SimpleDoubleProperty) {
            return ((SimpleDoubleProperty) observableValue).get();
        } else if (observableValue instanceof SimpleBooleanProperty) {
            return ((SimpleBooleanProperty) observableValue).get();
        } else if (observableValue instanceof SimpleObjectProperty) {
            return ((SimpleObjectProperty) observableValue).get();
        } else if (customFieldMappers.containsKey(fieldName)) {
            return customFieldMappers.get(fieldName).getValue(observableValue);
        } else {
            throw new RuntimeException("Field [" + fieldName + "] does not implemented getter. Implement getter for " + observableValue.getClass().getName() + " class.");
        }
    }

    public Map<String, ObservableValue<?>> getData() {
        return data;
    }

    public Map<String, Element<?>> getFields() {
        return fields;
    }
}
