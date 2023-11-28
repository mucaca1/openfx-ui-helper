package org.bh.uifxhelpercore.editor;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.bh.uifxhelpercore.table.TableViewComponent;

public class BasicEditorForm {

    private BorderPane leftBorderPane;

    private BorderPane rightBorderPane;

    private VBox rootPane;

    private AnchorPane anchorPane;

    private TableViewComponent table;

    private ButtonBar tableButtonBar;

    private ButtonBar formButtonBar;

    private ScrollPane leftScrollPane;

    private ScrollPane rightScrollPane;

    public BorderPane getLeftBorderPane() {
        return leftBorderPane;
    }

    public BorderPane getRightBorderPane() {
        return rightBorderPane;
    }

    public VBox getRootPane() {
        return rootPane;
    }

    public TableViewComponent getTable() {
        return table;
    }

    public ButtonBar getTableButtonBar() {
        return tableButtonBar;
    }

    public ButtonBar getFormButtonBar() {
        return formButtonBar;
    }

    public ScrollPane getLeftScrollPane() {
        return leftScrollPane;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public ScrollPane getRightScrollPane() {
        return rightScrollPane;
    }

    public BasicEditorForm(ResourceBundleService resourceBundleService) {
        rootPane = new VBox();
        rootPane.setAlignment(Pos.CENTER);
        VBox.setVgrow(rootPane, Priority.ALWAYS);

        anchorPane = new AnchorPane();
        anchorPane.maxHeight(-1d);
        anchorPane.maxWidth(-1d);
        anchorPane.prefHeight(-1d);
        anchorPane.prefWidth(-1d);
        VBox.setVgrow(anchorPane, Priority.ALWAYS);

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.55d);

        AnchorPane anchorPaneLeft = new AnchorPane();
        anchorPaneLeft.setMinHeight(0.0);
        anchorPaneLeft.setMinWidth(0.0);

        leftBorderPane = new BorderPane();
        AnchorPane.setBottomAnchor(leftBorderPane, 0.0d);
        AnchorPane.setLeftAnchor(leftBorderPane, 0.0d);
        AnchorPane.setRightAnchor(leftBorderPane, 0.0d);
        AnchorPane.setTopAnchor(leftBorderPane, 0.0d);

        AnchorPane anchorPaneRight = new AnchorPane();
        anchorPaneRight.setMinHeight(0.0);
        anchorPaneRight.setMinWidth(0.0);

        rightBorderPane = new BorderPane();
        AnchorPane.setBottomAnchor(rightBorderPane, 0.0d);
        AnchorPane.setLeftAnchor(rightBorderPane, 0.0d);
        AnchorPane.setRightAnchor(rightBorderPane, 0.0d);
        AnchorPane.setTopAnchor(rightBorderPane, 0.0d);


        anchorPaneLeft.getChildren().add(leftBorderPane);
        anchorPaneRight.getChildren().add(rightBorderPane);

        splitPane.getItems().addAll(anchorPaneLeft, anchorPaneRight);

        anchorPane.getChildren().add(splitPane);

        rootPane.getChildren().add(anchorPane);

        //------------------------------------------//

        leftScrollPane = new ScrollPane();
        rightScrollPane = new ScrollPane();

        tableButtonBar = new ButtonBar();
        formButtonBar = new ButtonBar();

        table = new TableViewComponent(resourceBundleService);

        leftScrollPane.setContent(table);

        leftBorderPane.setCenter(leftScrollPane);
        leftBorderPane.setBottom(tableButtonBar);
        rightBorderPane.setCenter(rightScrollPane);
        rightBorderPane.setBottom(formButtonBar);
    }
}
