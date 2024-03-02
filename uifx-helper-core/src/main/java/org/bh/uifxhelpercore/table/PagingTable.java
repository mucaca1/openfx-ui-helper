package org.bh.uifxhelpercore.table;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import org.bh.uifxhelpercore.pagination.AdvancedPagination;

import java.util.ArrayList;
import java.util.List;

/**
 * Paging table contains basic {@link TableViewComponent} but add pagination bar.
 *
 * @param <T>
 */
public class PagingTable<T> extends BorderPane {

    private final int rowsPerPage;
    private Pagination pagination;
    private TableViewComponent<T> table;

    private List<T> data = new ArrayList<>();

    public PagingTable(Class<T> tableObject, ViewType viewType) {
        this(tableObject, viewType, 20, true);
    }

    public PagingTable(Class<T> tableObject, ViewType viewType, int rowsPerPage, boolean addFirstLastPaginationOption) {
        this.rowsPerPage = rowsPerPage;
        if (addFirstLastPaginationOption) {
            pagination = new AdvancedPagination((data.size() / this.rowsPerPage + 1), 0);
        } else {
            pagination = new Pagination((data.size() / this.rowsPerPage + 1), 0);
        }
        pagination.setPageFactory(this::createPage);
        table = new TableViewComponent<>();
        table.initialize(tableObject, viewType);
        setCenter(pagination);
    }

    public void setData(List<T> data) {
        this.data = data;
        pagination.setPageCount((data.size() / rowsPerPage + 1));
        pagination.setCurrentPageIndex(0);
        fillPage(pagination.getCurrentPageIndex());
    }

    public TableViewComponent<T> getTableComponent() {
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
