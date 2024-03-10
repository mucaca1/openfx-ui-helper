package org.bh.uifxhelpercore.editor.builder;

import org.bh.uifxhelpercore.editor.DataBrowser;
import org.bh.uifxhelpercore.table.builder.PagingTableBuilder;
import org.bh.uifxhelpercore.table.builder.TableBuilder;

public class DataBrowserBuilder<T> {

    private boolean usePagination;
    private boolean addTextFiltering;

    private boolean autoFiltering;
    private TableBuilder<T> tableBuilder;
    private PagingTableBuilder<T> pagingTableBuilder;

    public DataBrowserBuilder(Class<T> tableObject) {
        tableBuilder = new TableBuilder<>(tableObject);
        pagingTableBuilder = new PagingTableBuilder<>(tableObject);
        usePagination = false;
        addTextFiltering = false;
        autoFiltering = true;
    }

    public boolean isAddTextFiltering() {
        return addTextFiltering;
    }

    public DataBrowserBuilder<T> setAddTextFiltering(boolean addTextFiltering) {
        this.addTextFiltering = addTextFiltering;
        return this;
    }

    @SuppressWarnings("Not implemented yet!")
    public DataBrowserBuilder<T> setAutoFilter(boolean autoFiltering) {
        this.autoFiltering = autoFiltering;
        return this;
    }

    public boolean isAutoFiltering() {
        return autoFiltering;
    }

    public boolean isUsePagination() {
        return usePagination;
    }

    public DataBrowserBuilder<T> setUsePagination(boolean usePagination) {
        this.usePagination = usePagination;
        return this;
    }

    public PagingTableBuilder<T> getPagingTableBuilder() {
        return pagingTableBuilder;
    }

    public DataBrowserBuilder<T> setPagingTableBuilder(PagingTableBuilder<T> pagingTableBuilder) {
        this.pagingTableBuilder = pagingTableBuilder;
        return this;
    }

    public TableBuilder<T> getTableBuilder() {
        return tableBuilder;
    }

    public DataBrowserBuilder<T> setTableBuilder(TableBuilder<T> tableBuilder) {
        this.tableBuilder = tableBuilder;
        return this;
    }

    public DataBrowser<T> build() {
        return new DataBrowser<>(this);
    }
}
