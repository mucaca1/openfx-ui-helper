package org.bh.uifxhelpercore.editor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.BorderPane;
import org.bh.uifxhelpercore.DataFilter;
import org.bh.uifxhelpercore.button.ButtonType;
import org.bh.uifxhelpercore.datarequest.DataRequestPojo;
import org.bh.uifxhelpercore.datarequest.IDataRequest;
import org.bh.uifxhelpercore.editor.builder.DataBrowserBuilder;
import org.bh.uifxhelpercore.editor.searcher.SearcherBar;
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

    private ListProperty<T> data = new SimpleListProperty<T>(FXCollections.observableArrayList());

    private final boolean enableTextFiltering;
    private final boolean autoFiltering;
    private final boolean enablePaging;

    private PagingTable<T> pagingTable;
    private TableViewComponent<T> tableComponent;

    SearcherBar searcherBar;

    private DataFilter<T> dataFilter = new DataFilter<>();

    public DataBrowser(DataBrowserBuilder<T> builder) {
        this.enablePaging = builder.isUsePagination();
        this.enableTextFiltering = builder.isAddTextFiltering();
        this.autoFiltering = builder.isAutoFiltering();

        if (enablePaging) {
            pagingTable = builder.getPagingTableBuilder().build();
            tableComponent = pagingTable.getTableComponent();
            setCenter(pagingTable);
        } else {
            tableComponent = builder.getTableBuilder().build();
            setCenter(tableComponent);
        }

        if (enableTextFiltering) {
            searcherBar = new SearcherBar(this.autoFiltering);
            setTop(searcherBar);
            initSimpleTextFilter();
        }
    }

    private void initSimpleTextFilter() {
        dataFilter.bindData(data);
        dataFilter.initAsBasicTextFilterForTable(searcherBar.getSimpleTextSearcher().getSearchTextProperty(), tableComponent);
        dataFilter.getSortedData().addListener((ListChangeListener<? super T>) change -> {
            setTableData(new ArrayList<>(change.getList()));
        });
    }

    public ListProperty<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);

        if (!enableTextFiltering) {
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

    public void refreshData() {
        if (enablePaging) {
            pagingTable.setData(data);
        } else {
            tableComponent.setItems(FXCollections.observableList(data));
            tableComponent.refresh();
        }
    }

    public TableViewComponent<T> getTableComponent() {
        return tableComponent;
    }
}
