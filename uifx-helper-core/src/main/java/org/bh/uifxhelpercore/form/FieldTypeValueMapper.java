package org.bh.uifxhelpercore.form;

import com.dlsc.formsfx.model.structure.Element;
import javafx.beans.value.ObservableValue;

public interface FieldTypeValueMapper {

    ObservableValue<?> getValueFromField(java.lang.reflect.Field field);

    Element<?> getElement(FormField formField, ObservableValue<?> property);

    void setValue(ObservableValue<?> observableValue, Object value);

    Object getValue(ObservableValue<?> observableValue);
}
