package org.bh.uifxhelpercore.editor;

import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.bh.uifxhelpercore.table.TableViewComponent;
import org.bh.uifxhelpercore.table.ViewType;

import javax.swing.text.View;

public class BasicEditorUi<TABLE_OBJECT> {

    private VBox rootPane;
    private BorderPane leftBorderPane;
    private AnchorPane anchorPane;
    private TableViewComponent<TABLE_OBJECT> table;
    private ButtonBar tableButtonBar;
    private ScrollPane leftScrollPane;

    public BasicEditorUi(Class tableObjectClass, ViewType viewType, String tableDescriptor) {
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


        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.55d);

        // Init main component table.
        AnchorPane anchorPaneLeft = new AnchorPane();
        anchorPaneLeft.setMinHeight(0.0);
        anchorPaneLeft.setMinWidth(0.0);

        leftBorderPane = new BorderPane();
        AnchorPane.setBottomAnchor(leftBorderPane, 0.0d);
        AnchorPane.setLeftAnchor(leftBorderPane, 0.0d);
        AnchorPane.setRightAnchor(leftBorderPane, 0.0d);
        AnchorPane.setTopAnchor(leftBorderPane, 0.0d);

        anchorPaneLeft.getChildren().add(leftBorderPane);


        anchorPane.getChildren().add(anchorPaneLeft);


        rootPane.getChildren().add(anchorPane);


        leftScrollPane = new ScrollPane();
        tableButtonBar = new ButtonBar();
        table = new TableViewComponent();
        leftScrollPane.setContent(table);
        leftBorderPane.setCenter(leftScrollPane);
        leftBorderPane.setBottom(tableButtonBar);

        table.initialize(tableObjectClass, viewType, tableDescriptor);
    }

    public VBox getRootPane() {
        return rootPane;
    }

    public TableViewComponent getTable() {
        return table;
    }
}
