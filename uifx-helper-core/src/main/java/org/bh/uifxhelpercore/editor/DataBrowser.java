package org.bh.uifxhelpercore.editor;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.bh.uifxhelpercore.editor.searcher.SimpleTextSearcher;
import org.bh.uifxhelpercore.table.TableViewComponent;
import org.bh.uifxhelpercore.table.ViewType;

public class DataBrowser<T> extends VBox {

    private SimpleTextSearcher simpleTextSearcher = new SimpleTextSearcher();
    private TableViewComponent<T> tableComponent = new TableViewComponent<>();

    public DataBrowser(Class<T> tableObject, ViewType viewType) {
        tableComponent.initialize(tableObject, viewType);

        Pane paddingDivider = new Pane();
        paddingDivider.setMinHeight(5);
        paddingDivider.setMaxHeight(5);

        getChildren().addAll(simpleTextSearcher, paddingDivider, tableComponent);
    }

    public void initSimpleTextFilter() {
        tableComponent.registerSimpleTextFilter(simpleTextSearcher.getSearchTextProperty());
    }

    public TableViewComponent<T> getTableComponent() {
        return tableComponent;
    }
}
