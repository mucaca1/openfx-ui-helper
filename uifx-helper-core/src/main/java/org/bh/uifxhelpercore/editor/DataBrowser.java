package org.bh.uifxhelpercore.editor;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.BorderPane;
import org.bh.uifxhelpercore.DataFilter;
import org.bh.uifxhelpercore.editor.builder.DataBrowserBuilder;
import org.bh.uifxhelpercore.editor.searcher.SimpleTextSearcher;
import org.bh.uifxhelpercore.table.PagingTable;
import org.bh.uifxhelpercore.table.TableViewComponent;
import org.bh.uifxhelpercore.table.ViewType;
import org.bh.uifxhelpercore.table.builder.PagingTableBuilder;
import org.bh.uifxhelpercore.table.builder.TableBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple data browser witch show data in table.
 * @param <T>
 */
public class DataBrowser<T> extends BorderPane {

    private final boolean enableTextFiltering;
    private final boolean enablePaging;

    private SimpleTextSearcher simpleTextSearcher = new SimpleTextSearcher();

    private PagingTable<T> pagingTable;
    private TableViewComponent<T> tableComponent;

    private DataFilter<T> dataFilter = new DataFilter<>();

    public DataBrowser(DataBrowserBuilder<T> builder) {
        this.enablePaging = builder.isUsePagination();
        this.enableTextFiltering = builder.isAddTextFiltering();

        if (enablePaging) {
            pagingTable = builder.getPagingTableBuilder().build();
            tableComponent = pagingTable.getTableComponent();
            setCenter(pagingTable);
        } else {
            tableComponent = builder.getTableBuilder().build();
            setCenter(tableComponent);
        }

        if (enableTextFiltering) {
            setTop(simpleTextSearcher);
            initSimpleTextFilter();
        }
    }

    private void initSimpleTextFilter() {
        dataFilter.initAsBasicTextFilterForTable(simpleTextSearcher.getSearchTextProperty(), tableComponent);
        dataFilter.getSortedData().addListener((ListChangeListener<? super T>) change -> {
            setTableData(new ArrayList<>(change.getList()));
        });
    }

    public void setData(List<T> data) {
        if (enableTextFiltering) {
            dataFilter.setData(data);
        } else {
            setTableData(data);
        }
    }

    public void setTableData(List<T> data) {
        if (enablePaging) {
            pagingTable.setData(data);
        } else {
            tableComponent.setItems(FXCollections.observableList(data));
        }
    }

    public TableViewComponent<T> getTableComponent() {
        return tableComponent;
    }
}
