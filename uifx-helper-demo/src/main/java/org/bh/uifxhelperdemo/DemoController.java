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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import org.bh.uifxhelpercore.editor.BasicEditor;
import org.bh.uifxhelpercore.editor.DataBrowser;
import org.bh.uifxhelpercore.editor.SimpleObjectTranslator;
import org.bh.uifxhelpercore.editor.builder.BasicEditorBuilder;
import org.bh.uifxhelpercore.editor.builder.DataBrowserBuilder;
import org.bh.uifxhelpercore.field.FieldHelper;
import org.bh.uifxhelpercore.field.entity.SimpleEntityChooserField;
import org.bh.uifxhelpercore.form.DynamicFormWrapper;
import org.bh.uifxhelpercore.form.FieldTypeValueMapper;
import org.bh.uifxhelpercore.form.FormField;
import org.bh.uifxhelpercore.form.builder.FormBuilder;
import org.bh.uifxhelpercore.listcomponent.ListChooserComponent;
import org.bh.uifxhelpercore.locale.LocalizationHelper;
import org.bh.uifxhelpercore.table.builder.PagingTableBuilder;
import org.bh.uifxhelpercore.table.builder.TableBuilder;

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
    public Tab dynamicTableFilterPane;
    @FXML
    public Tab dynamicPagingTablePane;
    @FXML
    public Tab dynamicFormTablePane;
    @FXML
    private Tab basicFormWithSplitPane;
    @FXML
    private Tab basicEditorWithFormPane;
    @FXML
    private Tab listChooseComponentPane;

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

            /* This code register default resource bundle for buttons.
             * In builder, we do not need set up bundle. Will be automatically took from default button bundle.
             */
            LocalizationHelper.get().registerDefaultButtonBundleService("en", ResourceBundle.getBundle("Buttons", new Locale("en")));
            LocalizationHelper.get().registerDefaultButtonBundleService("sk", ResourceBundle.getBundle("Buttons", new Locale("sk")));

        }

        // Init dynamic table
        {
            DataBrowser<Person> simpleDataBrowser = new DataBrowserBuilder<Person>(Person.class).setTableBuilder(new TableBuilder<>(Person.class)).build();
            simpleDataBrowser.setTableData(DemoDataFactory.getRandomPerson(5));
            dynamicTablePane.setContent(simpleDataBrowser);
        }

        // Init dynamic table with basic text filter
        {
            DataBrowser<Person> simpleDataBrowser = new DataBrowserBuilder<Person>(Person.class).setAddTextFiltering(true).setAutoFilter(true).setTableBuilder(new TableBuilder<>(Person.class)).build();
            simpleDataBrowser.setData(DemoDataFactory.getRandomPerson(5));
            dynamicTableFilterPane.setContent(simpleDataBrowser);
        }

        // Init dynamic paging table
        {
            DataBrowser<Person> simpleDataBrowser = new DataBrowserBuilder<Person>(Person.class).setAddTextFiltering(true).setUsePagination(true).setPagingTableBuilder(new PagingTableBuilder<>(Person.class).addFirstLastPageButtons(true)).build();
            simpleDataBrowser.setData(DemoDataFactory.getRandomPerson(50));

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
                        PersonTableSelectorDialog entityPopupPicker = new PersonTableSelectorDialog(FXCollections.observableList(DemoDataFactory.getRandomPerson(5)));
                        ((Dialog<?>) entityPopupPicker).showAndWait();
                        System.out.println("Btn:" + entityPopupPicker.getResult());
                        if (ButtonType.OK.equals(entityPopupPicker.getResult())) {
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
            formWrapper = new FormBuilder<Person>(Person.class)
                    .setResourceBundleService(LocalizationHelper.get().getResourceBundleService("Form"))
                    .addFormFieldMapper("parent", mapper)
                    .build();

            formWrapper.getFormDynamicData().setValueOfField("name", "Random name");
            ((DataField<?, ?, ?>) formWrapper.getFormDynamicData().getFields().get("age")).setBindingMode(BindingMode.CONTINUOUS);
            ((IntegerField) formWrapper.getFormDynamicData().getFields().get("age")).addEventHandler(FieldEvent.EVENT_FIELD_PERSISTED, (a) -> {
                formWrapper.getFormDynamicData().setValueOfField("name", "Random name " + ((IntegerField) a.getField()).getValue());
            });
            formWrapper.getFieldByFieldId().get("name").editable(false);
            ((IntegerField) formWrapper.getFieldByFieldId().get("age")).validate(IntegerRangeValidator.atLeast(10, "low_number_msg"));
            dynamicFormTablePane.setContent(formWrapper.getFormRenderer());
        }


        // Init table and form
        {

            BasicEditor<Person, Person> basicEditor = new BasicEditorBuilder<>(Person.class, Person.class, new SimpleObjectTranslator<>())
                    .setButtonResourceBundle(LocalizationHelper.get().getResourceBundleService("Buttons"))
                    .setFormBuilder(new FormBuilder<>(Person.class).addFormFieldMapper("parent", mapper))
                    .setFormBuilder(new FormBuilder<>(Person.class)
                            .addFormFieldMapper("parent", mapper)
                            .setResourceBundleService(LocalizationHelper.get().getResourceBundleService("Form"))
                    )
                    .setDataBrowserBuilder(new DataBrowserBuilder<>(Person.class)
                            .setTableBuilder(new TableBuilder<>(Person.class)
                                    .setResourceBundleService(LocalizationHelper.get().getResourceBundleService("Tables"))
                            )
                    )
                    .setShowForm(true).build();
            basicEditor.setTableData(FXCollections.observableList(DemoDataFactory.getRandomPerson(5)));
            basicFormWithSplitPane.setContent(basicEditor);
        }

        // Init table and pop-up form
        {

            BasicEditor<Person, Person> basicEditor = new BasicEditorBuilder<>(Person.class, Person.class, new SimpleObjectTranslator<>())
                    .setButtonResourceBundle(LocalizationHelper.get().getResourceBundleService("Buttons"))
                    .setFormBuilder(new FormBuilder<>(Person.class)
                            .addFormFieldMapper("parent", mapper)
                            .setResourceBundleService(LocalizationHelper.get().getResourceBundleService("Form"))
                    )
                    .setDataBrowserBuilder(new DataBrowserBuilder<>(Person.class)
                            .setTableBuilder(new TableBuilder<>(Person.class)
                                    .setResourceBundleService(LocalizationHelper.get().getResourceBundleService("Tables"))
                            )
                    )
                    .setShowForm(false).build();
            basicEditor.setTableData(FXCollections.observableList(DemoDataFactory.getRandomPerson(5)));
            basicEditorWithFormPane.setContent(basicEditor);
        }

        // Init list choose component
        {
            DataBrowserBuilder<Person> leftBuilder = new DataBrowserBuilder<Person>(Person.class).setAddTextFiltering(true).setUsePagination(true).setPagingTableBuilder(new PagingTableBuilder<>(Person.class).addFirstLastPageButtons(true));
            ListChooserComponent<Person> listChooserComponent = new ListChooserComponent<Person>(leftBuilder, leftBuilder);
            listChooserComponent.setData(DemoDataFactory.getRandomPerson(5));
            listChooseComponentPane.setContent(listChooserComponent);
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