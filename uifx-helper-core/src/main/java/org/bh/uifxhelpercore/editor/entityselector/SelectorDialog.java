package org.bh.uifxhelpercore.editor.entityselector;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.bh.uifxhelpercore.table.TableViewComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectorDialog<T> extends Dialog<T> {
    protected TableViewComponent<T> table;
    protected TextField searchTextField;

    /**
     * Mark if dialog was closed by close button (cancel operation)
     */
    private boolean closeFlag;

    /**
     * Mark if dialog was closed by ok button (apply/save operation)
     */
    private boolean okFlag;

    private List<T> selectedTableObjects;

    public SelectorDialog() {
        this(false, true);
    }

    public SelectorDialog(boolean multiSelection, boolean canSelectNone) {
        super();
        closeFlag = false;
        okFlag = false;
        table = new TableViewComponent<T>(null);
        searchTextField = new TextField();

        selectedTableObjects = new ArrayList<>();

        searchTextField.setPromptText("Type for filtering...");

        VBox box = new VBox();
        box.setPadding(new Insets(10));

        Pane paddingDivider = new Pane();
        paddingDivider.setMinHeight(5);
        paddingDivider.setMaxHeight(5);
        box.getChildren().addAll(searchTextField, paddingDivider, table);
        getDialogPane().setContent(box);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        Button button = (Button) getDialogPane().lookupButton(ButtonType.OK);
        button.addEventFilter(ActionEvent.ACTION, event -> {
            closeFlag = false;
            okFlag = true;
        });

        if (canSelectNone) {
            ButtonType noneBtnType = new ButtonType("None");
            getDialogPane().getButtonTypes().addAll(noneBtnType);
            Button button1 = (Button) getDialogPane().lookupButton(noneBtnType);
            button1.addEventFilter(ActionEvent.ACTION, event -> {
                selectedTableObjects.clear();
                closeFlag = false;
                okFlag = true;
            });
        }

        Button button2 = (Button) getDialogPane().lookupButton(ButtonType.CANCEL);
        button2.addEventFilter(ActionEvent.ACTION, event -> {
            closeFlag = true;
            okFlag = false;
        });

        if (multiSelection) {
            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
        table.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getSource() == table) { // handle on table click.
                ObservableList<T> selectedItems = table.getSelectionModel().getSelectedItems();
                selectedTableObjects.clear();
                selectedTableObjects.addAll(selectedItems);
            }
        });
        table.registerSimpleTextFilter(searchTextField.textProperty());
    }

    public boolean isCloseFlag() {
        return closeFlag;
    }

    public boolean isOkFlag() {
        return okFlag;
    }

    public List<T> getSelectedTableObjects() {
        return selectedTableObjects;
    }
}
