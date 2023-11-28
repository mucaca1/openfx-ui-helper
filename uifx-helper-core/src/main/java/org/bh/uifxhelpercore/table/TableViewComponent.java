package org.bh.uifxhelpercore.table;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TableViewComponent<V> extends TableView<V> {

    private final ResourceBundleService resourceBundleService;

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
        getColumns().clear();
        List<ColumnData> columnData = TableHelper.getColumnIdAndTranslateKey(tableObject, viewType, descriptor);
        for (ColumnData data : columnData) {
            String columnName = data.getKeyToTranslate();
            if (resourceBundleService != null) {
                columnName = resourceBundleService.translate(data.getKeyToTranslate());
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
                column = new TableColumn<V, Object>(columnName);
            }

            column.setMinWidth(100);
            column.setCellValueFactory(new PropertyValueFactory<>(data.getId()));

            getColumns().add(column);
        }
    }

    public void registerFilter(StringProperty stringProperty) {
        FilteredList<V> filteredData = new FilteredList<>(getItems());
        filteredData.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            String text = stringProperty.getValue();

            if (text == null || text.isEmpty()) {
                return null;
            }
            final String filterText = text.toLowerCase();

            return o -> {
                for (TableColumn<V, ?> col : getColumns()) {
                    ObservableValue<?> observable = col.getCellObservableValue(o);
                    if (observable != null) {
                        Object value = observable.getValue();
                        if (value != null && value.toString().toLowerCase().contains(filterText)) {
                            return true;
                        }
                    }
                }
                return false;
            };
        }, stringProperty));

        SortedList<V> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(comparatorProperty());
        setItems(sortedData);
    }

    protected void reapplyTableSortOrder() {
        ArrayList<TableColumn<V, ?>> sortOrder = new ArrayList<>(getSortOrder());
        getSortOrder().clear();
        getSortOrder().addAll(sortOrder);
    }
}