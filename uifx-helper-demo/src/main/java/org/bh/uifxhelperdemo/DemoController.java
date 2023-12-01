package org.bh.uifxhelperdemo;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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

    private ResourceBundleService resourceBundleTables = new ResourceBundleService(ResourceBundle.getBundle("Tables", new Locale("en")));

    @FXML
    public void init() {

        ScrollPane scrollPane = new ScrollPane();


        BasicEditorUi<Person> basicEditor = new BasicEditorUIBuilder<Person>(Person.class)
                .setResourceBundle(resourceBundleTables)
                .build();
        basicEditor.setTableData(FXCollections.observableList(DemoData.getRandomPerson(5)));
        scrollPane.setContent(basicEditor.getRootPane());
        basicFormWithSplitPane.setContent(scrollPane);
    }

    public void setEnLanguage(ActionEvent actionEvent) {
        resourceBundleTables.changeLocale(ResourceBundle.getBundle("Tables", new Locale("en")));
    }

    public void setSkLanguage(ActionEvent actionEvent) {
        resourceBundleTables.changeLocale(ResourceBundle.getBundle("Tables", new Locale("sk")));
    }
}