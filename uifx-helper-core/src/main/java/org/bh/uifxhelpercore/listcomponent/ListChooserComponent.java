package org.bh.uifxhelpercore.listcomponent;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.bh.uifxhelpercore.editor.DataBrowser;
import org.bh.uifxhelpercore.editor.builder.DataBrowserBuilder;

import java.util.ArrayList;
import java.util.List;

public class ListChooserComponent<T> extends BorderPane {

    private DataBrowser<T> leftPanel;
    private DataBrowser<T> rightPanel;

    public ListChooserComponent(DataBrowserBuilder<T> leftBuilder, DataBrowserBuilder<T> rightBuilder) {
        VBox vBox = new VBox();
        HBox hBox = new HBox();

        Button moveRight = new Button("->");
        Button moveLeft = new Button("<-");
        vBox.getChildren().addAll(moveRight, moveLeft);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        VBox vBoxLeft = new VBox();
        VBox vBoxRight = new VBox();
        Label allData = new Label("Data");
        Label selectedData = new Label("Selected");

        leftPanel = new DataBrowser<>(leftBuilder);
        rightPanel = new DataBrowser<>(rightBuilder);

        vBoxLeft.getChildren().addAll(allData, leftPanel);
        vBoxRight.getChildren().addAll(selectedData, rightPanel);

        hBox.getChildren().addAll(vBoxLeft, vBox, vBoxRight);

        HBox.setHgrow(vBoxLeft, Priority.ALWAYS);
        HBox.setHgrow(vBoxRight, Priority.ALWAYS);

        moveLeft.addEventHandler(ActionEvent.ACTION, event -> {
            T item = rightPanel.getTableComponent().getSelectionModel().getSelectedItem();
            if (item == null) {
                return;
            }
            List<T> l = new ArrayList<>(leftPanel.getData());
            l.add(item);
            leftPanel.setData(l);

            List<T> ll = rightPanel.getData().stream().filter(t -> !t.equals(item)).toList();
            rightPanel.setData(ll);
        });
        moveRight.addEventHandler(ActionEvent.ACTION, event -> {
            T item = leftPanel.getTableComponent().getSelectionModel().getSelectedItem();
            if (item == null) {
                return;
            }
            List<T> l = new ArrayList<>(rightPanel.getData());
            l.add(item);
            rightPanel.setData(l);

            List<T> ll = leftPanel.getData().stream().filter(t -> !t.equals(item)).toList();
            leftPanel.setData(ll);
        });

        setCenter(hBox);
    }

    public void setData(List<T> data) {
        leftPanel.setData(data);
    }

    public void setSelectedData(List<T> data) {
        rightPanel.setData(data);
    }
}
