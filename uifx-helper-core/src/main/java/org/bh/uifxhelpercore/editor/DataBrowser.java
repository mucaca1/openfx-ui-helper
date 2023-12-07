package org.bh.uifxhelpercore.editor;

import javafx.scene.layout.VBox;
import org.bh.uifxhelpercore.editor.searcher.SimpleTextSearcher;
import org.bh.uifxhelpercore.table.TableViewComponent;
import org.bh.uifxhelpercore.table.ViewType;

public class DataBrowser<T> extends VBox {

    private SimpleTextSearcher simpleTextSearcher = new SimpleTextSearcher();
    private TableViewComponent<T> tableComponent = new TableViewComponent<>();

    public DataBrowser(Class<T> tableObject, ViewType viewType) {
        tableComponent.initialize(tableObject, viewType);

        getChildren().addAll(simpleTextSearcher, tableComponent);
    }

    public void initSimpleTextFilter() {
        tableComponent.registerSimpleTextFilter(simpleTextSearcher.getSearchTextProperty());
    }

    public SimpleTextSearcher getSimpleTextSearcher() {
        return simpleTextSearcher;
    }

    public TableViewComponent<T> getTableComponent() {
        return tableComponent;
    }
}
