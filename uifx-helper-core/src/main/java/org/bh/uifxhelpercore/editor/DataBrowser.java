package org.bh.uifxhelpercore.editor;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.BorderPane;
import org.bh.uifxhelpercore.DataFilter;
import org.bh.uifxhelpercore.editor.searcher.SimpleTextSearcher;
import org.bh.uifxhelpercore.table.PagingTable;
import org.bh.uifxhelpercore.table.TableViewComponent;
import org.bh.uifxhelpercore.table.ViewType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataBrowser<T> extends BorderPane {

    private final boolean enableTextFiltering;
    private final boolean enablePaging;

    private SimpleTextSearcher simpleTextSearcher = new SimpleTextSearcher();

    private PagingTable<T> pagingTable;
    private TableViewComponent<T> tableComponent;

    private DataFilter<T> dataFilter = new DataFilter<>();

    public DataBrowser(Class<T> tableObject, ViewType viewType, boolean enableTextFiltering, boolean enablePaging) {
        this.enablePaging = enablePaging;
        this.enableTextFiltering = enableTextFiltering;

        if (enablePaging) {
            pagingTable = new PagingTable<>(tableObject, viewType);
            tableComponent = pagingTable.getTableComponent();
            setCenter(pagingTable);
        } else {
            tableComponent = new TableViewComponent<>();
            tableComponent.initialize(tableObject, viewType);
            setCenter(tableComponent);
        }

        if (enableTextFiltering) {
            setTop(simpleTextSearcher);
            initSimpleTextFilter();
        }
    }

    public DataBrowser(Class<T> tableObject, ViewType viewType) {
        this(tableObject, viewType, false, false);
    }

    private void initSimpleTextFilter() {
        dataFilter.addBasicTextFilterForTable(simpleTextSearcher.getSearchTextProperty(), tableComponent);
        dataFilter.getSortedData().addListener((ListChangeListener<? super T>)  change -> {
            setData(new ArrayList<>(change.getList()));
        });
    }

    public void setMasterData(List<T> data) {
        if (enableTextFiltering) {
            dataFilter.setMasterData(FXCollections.observableList(data));
        }
    }

    public void setData(List<T> data) {
        if (enablePaging) {
            pagingTable.setData(data);
        } else {
            tableComponent.setItems(FXCollections.observableList(data));
        }
    }
}
