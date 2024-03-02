package org.bh.uifxhelpercore.table;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TableViewComponent<V> extends TableView<V> {

    private final ResourceBundleService resourceBundleService;

    public TableViewComponent() {
        resourceBundleService = null;
    }

    public TableViewComponent(ResourceBundleService resourceBundleService) {
        this.resourceBundleService = resourceBundleService;
    }

    public TableViewComponent(ResourceBundleService resourceBundleService, Class<V> tableObject, ViewType viewType) {
        this.resourceBundleService = resourceBundleService;
        initialize(tableObject, viewType);
    }

    public void initialize(Class<V> tableObject, ViewType viewType) {
        initialize(tableObject, viewType, "");
    }

    public void initialize(Class<V> tableObject, ViewType viewType, String descriptor) {
        List<ColumnData> columnData = TableHelper.getColumnIdAndTranslateKey(tableObject, viewType, descriptor);
        recreateColumns(columnData);
        if (resourceBundleService != null) {
            resourceBundleService.addListener(() -> {
                recreateColumns(TableHelper.getColumnIdAndTranslateKey(tableObject, viewType, descriptor));
            });
        }
    }

    private void recreateColumns(List<ColumnData> columnData) {
        getColumns().clear();
        for (ColumnData data : columnData) {
            String columnName = data.getId();
            if (resourceBundleService != null) {
                columnName = resourceBundleService.translate(data.getColumnName());
            }
            Class<?> t = data.getColumnType();
            TableColumn<V, ?> column = null;
            if (t == Long.class) {
                column = new TableColumn<V, Long>(columnName);
            } else if (t == String.class) {
                column = new TableColumn<V, String>(columnName);
            } else if (t == Date.class) {
                column = new TableColumn<V, Date>(columnName);
            } else if (t == BigDecimal.class) {
                column = new TableColumn<V, BigDecimal>(columnName);
            } else {
                column = new TableColumn<>(columnName);
            }

            column.setMinWidth(100);
            column.setCellValueFactory(new PropertyValueFactory<>(data.getId()));

            getColumns().add(column);
        }
    }

    public void enableMultiSelection(boolean multiSelection) {
        if (multiSelection) {
            getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } else {
            getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        }
    }
}
