package org.bh.uifxhelpercore.form;

import com.dlsc.formsfx.model.structure.Element;
import javafx.beans.value.ObservableValue;

public interface FieldTypeValueMapper {

    ObservableValue<?> getValueFromField(java.lang.reflect.Field field);

    Element<?> getElement();

    void setValue(Object value);

    Object getValue();
}
