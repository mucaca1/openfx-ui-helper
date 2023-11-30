package org.bh.uifxhelperdemo;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import org.bh.uifxhelpercore.editor.BasicEditorUIBuilder;
import org.bh.uifxhelpercore.editor.BasicEditorUi;

import java.util.Locale;
import java.util.ResourceBundle;

public class DemoController {
    @FXML
    private Tab basicFormWithSplitPane;

    @FXML
    private Tab basicEditorWithFormPane;

    @FXML
    public void init() {



        ScrollPane scrollPane = new ScrollPane();

        ResourceBundle tables = ResourceBundle.getBundle("Tables", new Locale("en"));

        BasicEditorUi<Person> basicEditor = new BasicEditorUIBuilder<Person>(Person.class)
                .setResourceBundle(new ResourceBundleService(tables))
                .build();
        basicEditor.setTableData(FXCollections.observableList(DemoData.getRandomPerson(5)));
        scrollPane.setContent(basicEditor.getRootPane());
        basicFormWithSplitPane.setContent(scrollPane);
    }
}