package org.bh.uifxhelpercore;

import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DataFilter<V> {

    private ObservableList<V> masterData = FXCollections.observableArrayList();
    private FilteredList<V> filteredData;

    private SortedList<V> sortedData;

    public void setMasterData(ObservableList<V> items) {
        masterData.clear();
        masterData.setAll(items);
    }

    public FilteredList<V> getFilteredData() {
        return filteredData;
    }

    public SortedList<V> getSortedData() {
        return sortedData;
    }

    public void addBasicTextFilterForTable(StringProperty stringProperty, TableView<V> table) {
        filteredData = new FilteredList<>(masterData, v -> true);

        filteredData.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            String text = stringProperty.getValue();

            if (text == null || text.isEmpty()) {
                return null;
            }
            final String filterText = text.toLowerCase();

            return o -> {
                for (TableColumn<V, ?> col : table.getColumns()) {
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

        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
    }
}
