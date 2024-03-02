package org.bh.uifxhelpercore.table;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import org.bh.uifxhelpercore.pagination.AdvancedPagination;
import org.bh.uifxhelpercore.table.builder.PagingTableBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Paging table contains basic {@link TableViewComponent} but add pagination bar.
 *
 * @param <V>
 */
public class PagingTable<V> extends BorderPane {

    private final int rowsPerPage;
    private Pagination pagination;
    private TableViewComponent<V> table;

    private List<V> data = new ArrayList<>();

    public PagingTable() {
        rowsPerPage = 20;
    }


    public PagingTable(PagingTableBuilder<V> builder) {
        this.rowsPerPage = builder.getRowsPerPage();
        if (builder.isAddFirstLastPageButtons()) {
            pagination = new AdvancedPagination((data.size() / this.rowsPerPage + 1), 0);
        } else {
            pagination = new Pagination((data.size() / this.rowsPerPage + 1), 0);
        }
        pagination.setPageFactory(this::createPage);
        table = builder.getTableBuilder().build();

        setCenter(pagination);
    }

    public void setData(List<V> data) {
        this.data = data;
        pagination.setPageCount((data.size() / rowsPerPage + 1));
        pagination.setCurrentPageIndex(0);
        fillPage(pagination.getCurrentPageIndex());
    }

    public TableViewComponent<V> getTableComponent() {
        return table;
    }

    private Node createPage(int pageIndex) {
        fillPage(pageIndex);
        return new BorderPane(table);
    }

    private void fillPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
        table.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
    }

}
