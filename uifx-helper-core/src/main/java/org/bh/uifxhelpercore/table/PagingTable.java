package org.bh.uifxhelpercore.table;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PagingTable<T> extends Pane {

    private PagingBar pagingBar;
    private TableViewComponent<T> table;

    public PagingTable(Class<T> tableObject, ViewType viewType) {
        VBox vBox = new VBox();
        pagingBar = new PagingBar();
        table = new TableViewComponent<>();
        table.initialize(tableObject, viewType);
        vBox.getChildren().addAll(pagingBar, table);
        getChildren().add(vBox);
    }

    public TableViewComponent<T> getTableComponent() {
        return table;
    }
}
