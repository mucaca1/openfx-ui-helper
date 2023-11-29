package org.bh.uifxhelperdemo;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import org.bh.uifxhelpercore.editor.BasicEditorUi;

public class DemoController {
    @FXML
    private Tab basicFormWithSplitPane;

    @FXML
    private Tab basicEditorWithFormPane;

    @FXML
    public void init() {
        ScrollPane scrollPane = new ScrollPane();

        BasicEditorUi basicEditor = new BasicEditorUi(true);
        scrollPane.setContent(basicEditor.getRootPane());
        basicFormWithSplitPane.setContent(scrollPane);
    }
}