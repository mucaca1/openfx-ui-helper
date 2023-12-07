package org.bh.uifxhelpercore.table;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
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
                column = new TableColumn<V, Object>(columnName);
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


    // todo fix this! All should use setTableItems method for settings table content.
    // maybe registerSimpleTextFilter should be moved out of tablecomponent. Or sould i create Filter table component?
    public void setTableItems(ObservableList<V> tableItems) {
        masterData.clear();
        masterData.setAll(tableItems);
    }

    public ObservableList<V> getMasterData() {
        return masterData;
    }

    private ObservableList<V> masterData = FXCollections.observableArrayList();
    private FilteredList<V> filteredData;
    public void registerSimpleTextFilter(StringProperty stringProperty) {
        filteredData = new FilteredList<>(masterData, v -> true);
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
