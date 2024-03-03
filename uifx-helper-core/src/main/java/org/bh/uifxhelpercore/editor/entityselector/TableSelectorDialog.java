package org.bh.uifxhelpercore.editor.entityselector;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.bh.uifxhelpercore.editor.DataBrowser;
import org.bh.uifxhelpercore.editor.builder.DataBrowserBuilder;
import org.bh.uifxhelpercore.table.TableViewComponent;
import org.bh.uifxhelpercore.table.ViewType;
import org.bh.uifxhelpercore.table.builder.TableBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * This is dialog for showing data in table view.
 * Dialog contain simple text search for finding data in table.
 *
 * @param <T> object type witch will be showed in table
 */
public abstract class TableSelectorDialog<T> extends Dialog<ButtonType> {

    protected DataBrowser<T> dataBrowser;

    private List<T> selectedObjectsFromTable;

    public TableSelectorDialog(Class<T> tableObject, ViewType viewType) {
        this(tableObject, viewType, false, false);
    }

    public TableSelectorDialog(Class<T> tableObject, ViewType viewType, boolean multiSelection, boolean canSelectNone) {
        super();
        dataBrowser = new DataBrowserBuilder<T>(tableObject)
                .setAddTextFiltering(true)
                .setTableBuilder(new TableBuilder<>(tableObject).setViewType(viewType).setMultiSelectionEnabled(multiSelection))
                .build();

        selectedObjectsFromTable = new ArrayList<>();

        VBox box = new VBox();
        box.setPadding(new Insets(10));

        Pane paddingDivider = new Pane();
        paddingDivider.setMinHeight(5);
        paddingDivider.setMaxHeight(5);
        box.getChildren().add(dataBrowser);
        getDialogPane().setContent(box);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        Button button = (Button) getDialogPane().lookupButton(ButtonType.OK);
        button.addEventFilter(ActionEvent.ACTION, event -> {
            ObservableList<T> selectedItems = dataBrowser.getTableComponent().getSelectionModel().getSelectedItems();
            selectedObjectsFromTable.clear();
            selectedObjectsFromTable.addAll(selectedItems);
            setResult(ButtonType.OK);
        });

        if (canSelectNone) {
            ButtonType noneBtnType = new ButtonType("None");
            getDialogPane().getButtonTypes().addAll(noneBtnType);
            Button button1 = (Button) getDialogPane().lookupButton(noneBtnType);
            button1.addEventFilter(ActionEvent.ACTION, event -> {
                setResult(noneBtnType);
            });
        }

        Button button2 = (Button) getDialogPane().lookupButton(ButtonType.CANCEL);
        button2.addEventFilter(ActionEvent.ACTION, event -> {
            setResult(ButtonType.CANCEL);
        });

        if (multiSelection) {
            dataBrowser.getTableComponent().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    public List<T> getSelectedObjectsFromTable() {
        return selectedObjectsFromTable;
    }
}
