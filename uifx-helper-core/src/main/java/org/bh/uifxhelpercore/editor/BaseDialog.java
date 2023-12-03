package org.bh.uifxhelpercore.editor;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bh.uifxhelpercore.button.ButtonAdvancedBar;
import org.bh.uifxhelpercore.button.ButtonType;

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
        ButtonAdvancedBar buttonBar = new ButtonAdvancedBar();
        buttonBar.addButtons(ButtonType.OK, ButtonType.CANCEL);

        vBox.getChildren().addAll(content, buttonBar);

        buttonBar.addActionListener(ButtonType.OK, event -> {
            okFlag = true;
            close();
        });
        buttonBar.addActionListener(ButtonType.CANCEL, event -> {
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
