package org.bh.uifxhelpercore.form;

import javafx.beans.value.ObservableValue;

public interface FieldValueMapper {

    void setValue(ObservableValue<?> observableValue, Object value);

    Object getValue(ObservableValue<?> observableValue);

}
