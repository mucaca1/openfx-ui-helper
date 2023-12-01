package org.bh.uifxhelpercore.form;

public enum FieldType {

    STRING(String.class),
    INTEGER(Integer.class),
    ;

    private final Class<?> classType;

    FieldType(Class<?> classType) {
        this.classType = classType;
    }

    public Class<?> getClassType() {
        return classType;
    }
}
