package org.bh.uifxhelpercore.table;

public class ColumnData {

    private String id;

    /**
     * Key witch need to be defined in resource bundle to translate column header
     */
    private String keyToTranslate;

    /**
     * Type of column (Long, String, ...)
     */
    private Class<?> columnType;

    public ColumnData() {}

    public ColumnData(String id, String keyToTranslate, Class<?> columnType) {
        this.id = id;
        this.keyToTranslate = keyToTranslate;
        this.columnType = columnType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyToTranslate() {
        return keyToTranslate;
    }

    public void setKeyToTranslate(String keyToTranslate) {
        this.keyToTranslate = keyToTranslate;
    }

    public Class getColumnType() {
        return columnType;
    }

    public void setColumnType(Class columnType) {
        this.columnType = columnType;
    }
}
