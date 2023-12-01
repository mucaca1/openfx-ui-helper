package org.bh.uifxhelpercore.editor;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BaseDialog extends Stage {

    /**
     * Mark if dialog was closed by close button (cancel operation)
     */
    private boolean closeFlag;

    /**
     * Mark if dialog was closed by ok button (apply/save operation)
     */
    private boolean okFlag;

    BaseDialog() {
        closeFlag = false;
        okFlag = false;
    }

    public void setContent(Parent content) {
        VBox vBox = new VBox();
        ButtonBar buttonBar = new ButtonBar();

        Button okBtn = new Button("Ok");
        Button cancelBtn = new Button("Cancel");
        buttonBar.getButtons().addAll(okBtn, cancelBtn);

        vBox.getChildren().addAll(content, buttonBar);

        okBtn.addEventHandler(ActionEvent.ACTION, event -> {
            okFlag = true;
            close();
        });
        cancelBtn.addEventHandler(ActionEvent.ACTION, event -> {
            closeFlag = true;
            close();
        });

        setMinWidth(400);
        setMinHeight(400);
        setScene(new Scene(vBox));
        setTitle("Base dialog");
        initModality(Modality.APPLICATION_MODAL);
    }

    public boolean isCloseFlag() {
        return closeFlag;
    }

    public boolean isOkFlag() {
        return okFlag;
    }
}
