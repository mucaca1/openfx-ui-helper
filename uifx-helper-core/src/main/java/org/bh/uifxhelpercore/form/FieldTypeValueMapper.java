package org.bh.uifxhelpercore.form;

import javafx.beans.value.ObservableValue;

public interface FieldTypeValueMapper {

    ObservableValue<?> getValueFromField(java.lang.reflect.Field field);
}
