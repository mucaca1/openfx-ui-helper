package org.bh.uifxhelpercore.editor;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.bh.uifxhelpercore.form.DynamicFormWrapper;
import org.bh.uifxhelpercore.form.FormWrapper;
import org.bh.uifxhelpercore.table.TableViewComponent;
import org.bh.uifxhelpercore.table.ViewType;

import java.util.ResourceBundle;

public class BasicEditorUi<TABLE_OBJECT> {

    private VBox rootPane;
    private AnchorPane anchorPane;
    private TableViewComponent<TABLE_OBJECT> table;
    private ButtonBar tableButtonBar;

    private FormWrapper<TABLE_OBJECT> formWrapper;

    public BasicEditorUi(Class<TABLE_OBJECT> tableObjectClass,
                         ViewType viewType,
                         String tableDescriptor,
                         ResourceBundleService tableResourceBundle,
                         ResourceBundleService formResourceBundle,
                         boolean multiSelection,
                         boolean initFormDynamic) {
        {
            rootPane = new VBox();
            rootPane.setAlignment(Pos.CENTER);
            VBox.setVgrow(rootPane, Priority.ALWAYS);

            anchorPane = new AnchorPane();
            anchorPane.maxHeight(-1d);
            anchorPane.maxWidth(-1d);
            anchorPane.prefHeight(-1d);
            anchorPane.prefWidth(-1d);
            VBox.setVgrow(anchorPane, Priority.ALWAYS);
        }

        // Init main component table.
        AnchorPane anchorPaneLeft = new AnchorPane();
        anchorPaneLeft.setMinHeight(0.0);
        anchorPaneLeft.setMinWidth(0.0);

        BorderPane leftBorderPane = new BorderPane();
        AnchorPane.setBottomAnchor(leftBorderPane, 0.0d);
        AnchorPane.setLeftAnchor(leftBorderPane, 0.0d);
        AnchorPane.setRightAnchor(leftBorderPane, 0.0d);
        AnchorPane.setTopAnchor(leftBorderPane, 0.0d);

        anchorPaneLeft.getChildren().add(leftBorderPane);


        anchorPane.getChildren().add(anchorPaneLeft);


        rootPane.getChildren().add(anchorPane);

        ScrollPane leftScrollPane = new ScrollPane();
        tableButtonBar = new ButtonBar();
        table = new TableViewComponent(tableResourceBundle);
        leftScrollPane.setContent(table);
        leftBorderPane.setCenter(leftScrollPane);
        leftBorderPane.setBottom(tableButtonBar);

        table.enableMultiSelection(multiSelection);
        table.initialize(tableObjectClass, viewType, tableDescriptor);

        if (initFormDynamic) {
            formWrapper = new DynamicFormWrapper<>(formResourceBundle, tableObjectClass);
        }

        // Initialize buttons
        {
            Button createBtn = new Button("Create");
            Button updateBtn = new Button("Update");
            Button deleteBtn = new Button("Delete");
            tableButtonBar.getButtons().addAll(createBtn, updateBtn, deleteBtn);


        }
    }

    public VBox getRootPane() {
        return rootPane;
    }

    public TableViewComponent getTable() {
        return table;
    }

    public void setTableData(ObservableList<TABLE_OBJECT> tebleObservableList) {
        table.setItems(tebleObservableList);
    }
}
