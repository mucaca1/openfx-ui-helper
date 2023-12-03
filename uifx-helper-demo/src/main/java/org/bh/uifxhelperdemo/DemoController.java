package org.bh.uifxhelperdemo;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import org.bh.uifxhelpercore.editor.BasicEditorUi;
import org.bh.uifxhelpercore.editor.SimpleObjectTranslator;
import org.bh.uifxhelpercore.editor.builder.BasicEditorUIBuilder;
import org.bh.uifxhelpercore.form.DynamicFormWrapper;
import org.bh.uifxhelpercore.locale.LocalizationHelper;
import org.bh.uifxhelpercore.table.TableViewComponent;
import org.bh.uifxhelpercore.table.ViewType;

import java.util.Locale;
import java.util.ResourceBundle;

public class DemoController {
    @FXML
    public Tab dynamicTablePane;
    @FXML
    public Tab dynamicFormTablePane;
    @FXML
    private Tab basicFormWithSplitPane;
    @FXML
    private Tab basicEditorWithFormPane;

    // Data
    DynamicFormWrapper<Person> formWrapper;

    @FXML
    public void init() {

        // Init localization
        {
            LocalizationHelper.get().registerResourceBundleService("Tables", "en", ResourceBundle.getBundle("Tables", new Locale("en")));
            LocalizationHelper.get().registerResourceBundleService("Tables", "sk", ResourceBundle.getBundle("Tables", new Locale("sk")));

            LocalizationHelper.get().registerResourceBundleService("Form", "en", ResourceBundle.getBundle("Form", new Locale("en")));
            LocalizationHelper.get().registerResourceBundleService("Form", "sk", ResourceBundle.getBundle("Form", new Locale("sk")));

        }

        // Init dynamic table
        {
            TableViewComponent<Person> tableViewComponent = new TableViewComponent<>(LocalizationHelper.get().getResourceBundleService("Tables"));
            tableViewComponent.initialize(Person.class, ViewType.Default);
            tableViewComponent.setItems(FXCollections.observableList(DemoData.getRandomPerson(5)));

            dynamicTablePane.setContent(tableViewComponent);
        }

        // Init dynamic form
        {
            formWrapper = new DynamicFormWrapper<Person>(LocalizationHelper.get().getResourceBundleService("Form"), Person.class);
            dynamicFormTablePane.setContent(formWrapper.getFormRenderer());
        }

        // Init table and form
        {
            BasicEditorUi<Person, Person> basicEditor = new BasicEditorUIBuilder<>(Person.class, Person.class, new SimpleObjectTranslator<>())
                    .setTableResourceBundle(LocalizationHelper.get().getResourceBundleService("Tables"))
                    .setFormResourceBundle(LocalizationHelper.get().getResourceBundleService("Form"))
                    .setShowForm(true)
                    .setInitFormDynamic(true)
                    .build();
            ScrollPane scrollPane = new ScrollPane();
            basicEditor.setTableData(FXCollections.observableList(DemoData.getRandomPerson(5)));
            scrollPane.setContent(basicEditor.getRootPane());
            basicFormWithSplitPane.setContent(scrollPane);
        }

        // Init table and pop-up form
        {
            BasicEditorUi<Person, Person> basicEditor = new BasicEditorUIBuilder<>(Person.class, Person.class, new SimpleObjectTranslator<>())
                    .setTableResourceBundle(LocalizationHelper.get().getResourceBundleService("Tables"))
                    .setFormResourceBundle(LocalizationHelper.get().getResourceBundleService("Form"))
                    .setInitFormDynamic(true)
                    .build();
            ScrollPane scrollPane = new ScrollPane();
            basicEditor.setTableData(FXCollections.observableList(DemoData.getRandomPerson(5)));
            scrollPane.setContent(basicEditor.getRootPane());
            basicEditorWithFormPane.setContent(scrollPane);
        }
    }

    public void setEnLanguage(ActionEvent actionEvent) {
        LocalizationHelper.get().setLocale("en");
    }

    public void setSkLanguage(ActionEvent actionEvent) {
        LocalizationHelper.get().setLocale("sk");
    }

    public void okActon(ActionEvent actionEvent) {
        System.out.println(formWrapper.getObjectFromForm());
    }
}