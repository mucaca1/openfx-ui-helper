package org.bh.uifxhelperdemo;

import com.dlsc.formsfx.model.event.FieldEvent;
import com.dlsc.formsfx.model.structure.DataField;
import com.dlsc.formsfx.model.structure.Element;
import com.dlsc.formsfx.model.structure.IntegerField;
import com.dlsc.formsfx.model.util.BindingMode;
import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import org.bh.uifxhelpercore.editor.BasicEditorUi;
import org.bh.uifxhelpercore.editor.DataBrowser;
import org.bh.uifxhelpercore.editor.SimpleObjectTranslator;
import org.bh.uifxhelpercore.editor.builder.BasicEditorUIBuilder;
import org.bh.uifxhelpercore.field.FieldHelper;
import org.bh.uifxhelpercore.field.entity.SimpleEntityChooserField;
import org.bh.uifxhelpercore.form.DynamicFormWrapper;
import org.bh.uifxhelpercore.form.FieldTypeValueMapper;
import org.bh.uifxhelpercore.form.FormField;
import org.bh.uifxhelpercore.locale.LocalizationHelper;
import org.bh.uifxhelpercore.table.PagingTable;
import org.bh.uifxhelpercore.table.ViewType;

import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DemoController {
    @FXML
    public Tab dynamicTablePane;
    @FXML
    public Tab dynamicPagingTablePane;
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

            LocalizationHelper.get().registerResourceBundleService("Buttons", "en", ResourceBundle.getBundle("Buttons", new Locale("en")));
            LocalizationHelper.get().registerResourceBundleService("Buttons", "sk", ResourceBundle.getBundle("Buttons", new Locale("sk")));

        }

        // Init dynamic table
        {
            DataBrowser<Person> simpleDataBrowser = new DataBrowser<>(Person.class, ViewType.Default);
            simpleDataBrowser.initSimpleTextFilter();
            simpleDataBrowser.getTableComponent().setTableItems(FXCollections.observableList(DemoData.getRandomPerson(5)));

            dynamicTablePane.setContent(simpleDataBrowser);
        }

        // Init dynamic paging table
        {
            PagingTable<Person> simpleDataBrowser = new PagingTable<>(Person.class, ViewType.Default);
            simpleDataBrowser.getTableComponent().setTableItems(FXCollections.observableList(DemoData.getRandomPerson(5)));

            dynamicPagingTablePane.setContent(simpleDataBrowser);
        }

        FieldTypeValueMapper mapper = new FieldTypeValueMapper() {
            @Override
            public ObservableValue<?> getValueFromField(Field field) {
                return new SimpleListProperty<Person>(FXCollections.observableArrayList());
            }

            @Override
            public Element<?> getElement(FormField formField, ObservableValue<?> property) {
                SimpleEntityChooserField<?> entityChooserField = FieldHelper.ofEntityChooser((SimpleListProperty<?>) property, true);
                entityChooserField.label(formField.fieldName());
                entityChooserField.registerSelectionAction(new ActionListener() {

                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        PersonTableSelectorDialog entityPopupPicker = new PersonTableSelectorDialog(FXCollections.observableList(DemoData.getRandomPerson(5)));
                        ((Dialog<?>) entityPopupPicker).showAndWait();
                        System.out.println("Close:" + entityPopupPicker.isCloseFlag() + ", Accept:" + entityPopupPicker.isOkFlag());
                        if (entityPopupPicker.isOkFlag()) {
                            List<Person> selectedObject = entityPopupPicker.getSelectedObjectsFromTable();
                            if (selectedObject != null) {
                                setValue(property, selectedObject);
                            }
                        }
                    }
                });
                return entityChooserField;
            }

            @Override
            public void setValue(ObservableValue<?> observableValue, Object value) {
                if (value == null) {
                    ((SimpleListProperty) observableValue).setValue(FXCollections.observableArrayList());
                } else {
                    if (value instanceof List) {
                        ((SimpleListProperty) observableValue).setValue(FXCollections.observableList((List) value));
                    } else {
                        ((SimpleListProperty) observableValue).setValue(FXCollections.observableList(Arrays.asList(value)));
                    }
                }
            }

            @Override
            public Object getValue(ObservableValue<?> observableValue) {
                if (((SimpleListProperty) observableValue).get().isEmpty()) {
                    return null;
                } else {
                    return ((SimpleListProperty) observableValue).get().get(0);
                }
            }
        };

        // Init dynamic form
        {
            formWrapper = new DynamicFormWrapper<Person>(LocalizationHelper.get().getResourceBundleService("Form"), Person.class);

            formWrapper.getFormDynamicData().registerMapper("parent", mapper);
            formWrapper.initForm();
            formWrapper.getFormDynamicData().setValueOfField("name", "Random name");
            ((DataField<?, ?, ?>) formWrapper.getFormDynamicData().getFields().get("age")).setBindingMode(BindingMode.CONTINUOUS);
            ((IntegerField) formWrapper.getFormDynamicData().getFields().get("age")).addEventHandler(FieldEvent.EVENT_FIELD_PERSISTED, (a) -> {
                formWrapper.getFormDynamicData().setValueOfField("name", "Random name " + ((IntegerField)a.getField()).getValue());
            });
            formWrapper.getFieldByFieldId().get("name").editable(false);
            ((IntegerField) formWrapper.getFieldByFieldId().get("age")).validate(IntegerRangeValidator.atLeast(10, "low_number_msg"));
            formWrapper.buildForm();
            dynamicFormTablePane.setContent(formWrapper.getFormRenderer());
        }


        // Init table and form
        {
            BasicEditorUi<Person, Person> basicEditor = new BasicEditorUIBuilder<>(Person.class, Person.class, new SimpleObjectTranslator<>())
                    .setTableResourceBundle(LocalizationHelper.get().getResourceBundleService("Tables"))
                    .setFormResourceBundle(LocalizationHelper.get().getResourceBundleService("Form"))
                    .setButtonResourceBundle(LocalizationHelper.get().getResourceBundleService("Buttons"))
                    .addFormFieldMapper("parent", mapper)
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
                    .setButtonResourceBundle(LocalizationHelper.get().getResourceBundleService("Buttons"))
                    .addFormFieldMapper("parent", mapper)
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