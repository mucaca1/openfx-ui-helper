package org.bh.uifxhelpercore.table;

public class ColumnData {

    private String id;

    /**
     * Column name. If this name is defined in resource bundle, name will be translated
     */
    private String columnName;

    /**
     * Type of column (Long, String, ...)
     */
    private Class<?> columnType;

    public ColumnData() {}

    public ColumnData(String id, String columnName, Class<?> columnType) {
        this.id = id;
        this.columnName = columnName;
        this.columnType = columnType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Class getColumnType() {
        return columnType;
    }

    public void setColumnType(Class columnType) {
        this.columnType = columnType;
    }
}
