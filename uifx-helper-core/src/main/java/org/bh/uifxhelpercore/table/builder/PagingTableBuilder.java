package org.bh.uifxhelpercore.table.builder;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import org.bh.uifxhelpercore.table.PagingTable;
import org.bh.uifxhelpercore.table.ViewType;

public class PagingTableBuilder<V> {

    private TableBuilder<V> tableBuilder;

    private boolean addFirstLastPageButtons;
    private int rowsPerPage;

    public PagingTableBuilder(Class<V> tableObject) {
        tableBuilder = new TableBuilder<V>(tableObject);
        addFirstLastPageButtons = false;
        rowsPerPage = 20;
    }

    public PagingTableBuilder<V> addFirstLastPageButtons(boolean addFirstLastPageButtons) {
        this.addFirstLastPageButtons = addFirstLastPageButtons;
        return this;
    }

    public PagingTableBuilder<V> setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
        return this;
    }

    public PagingTableBuilder<V> setTableBuilder(TableBuilder<V> tableBuilder) {
        this.tableBuilder = tableBuilder;
        return this;
    }

    public TableBuilder<V> getTableBuilder() {
        return tableBuilder;
    }

    public boolean isAddFirstLastPageButtons() {
        return addFirstLastPageButtons;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public PagingTable<V> build() {
        return new PagingTable<V>(this);
    }
}
