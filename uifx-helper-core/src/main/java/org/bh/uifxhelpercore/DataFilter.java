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

import java.util.List;

/**
 * Simple filter holder, witch hold data and can filter them.
 * @param <V>
 */
public class DataFilter<V> {

    private ObservableList<V> data = FXCollections.observableArrayList();
    private FilteredList<V> filteredData;

    private SortedList<V> sortedData;

    public void setData(List<V> items) {
        data.clear();
        data.setAll(items);
    }

    public FilteredList<V> getFilteredData() {
        return filteredData;
    }

    public SortedList<V> getSortedData() {
        return sortedData;
    }

    /**
     * Create filter as table filter.
     * This filter will filter all rows from all columns where text contain string in string property.
     * @param stringProperty
     * @param table the table to which the filters will be applied
     */
    public void initAsBasicTextFilterForTable(StringProperty stringProperty, TableView<V> table) {
        filteredData = new FilteredList<>(data, v -> true);

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
