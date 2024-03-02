package org.bh.uifxhelpercore.table;

import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import org.bh.uifxhelpercore.pagination.AdvancedPagination;

import java.util.ArrayList;
import java.util.List;

public class PagingTable<T> extends BorderPane {

    private final static int rowsPerPage = 20;
    private AdvancedPagination pagination;
    private TableViewComponent<T> table;

    private List<T> data = new ArrayList<>();

    public PagingTable(Class<T> tableObject, ViewType viewType) {
        pagination = new AdvancedPagination((data.size() / rowsPerPage + 1), 0);
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
