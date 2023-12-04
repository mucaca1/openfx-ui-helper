package org.bh.uifxhelpercore.editor;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.bh.uifxhelpercore.button.ButtonAdvancedBar;
import org.bh.uifxhelpercore.form.DynamicFormWrapper;
import org.bh.uifxhelpercore.form.FieldTypeValueMapper;
import org.bh.uifxhelpercore.form.FormWrapper;
import org.bh.uifxhelpercore.table.TableViewComponent;
import org.bh.uifxhelpercore.table.ViewType;

import java.util.Map;

public class BasicEditorUi<TABLE_OBJECT, FORM_OBJECT> {

    private VBox rootPane;
    private AnchorPane anchorPane;
    private TableViewComponent<TABLE_OBJECT> table;
    private ButtonAdvancedBar tableButtonBar;

    private FormWrapper<FORM_OBJECT> formWrapper;

    private final ObjectTranslator<TABLE_OBJECT, FORM_OBJECT> translator;

    public BasicEditorUi(Class<TABLE_OBJECT> tableObjectClass,
                         Class<FORM_OBJECT> formObjectClass,
                         ObjectTranslator<TABLE_OBJECT, FORM_OBJECT> objectTranslator,
                         ViewType viewType,
                         String tableDescriptor,
                         ResourceBundleService tableResourceBundle,
                         ResourceBundleService formResourceBundle,
                         boolean multiSelection,
                         boolean initFormDynamic,
                         boolean showForm,
                         Map<String, FieldTypeValueMapper> formFieldMappers) {
        this.translator = objectTranslator;
        {
            rootPane = new VBox();
            rootPane.setAlignment(Pos.CENTER);
            VBox.setVgrow(rootPane, Priority.ALWAYS);

            anchorPane = new AnchorPane();
            anchorPane.maxHeight(-1d);
            anchorPane.maxWidth(-1d);
            anchorPane.prefHeight(-1d);
            anchorPane.prefWidth(-1d);
            VBox.setVgrow(anchorPane, Priority.ALWAYS);
        }

        // Init main component table.
        AnchorPane anchorPaneTable = new AnchorPane();
        anchorPaneTable.setMinHeight(0.0);
        anchorPaneTable.setMinWidth(0.0);

        BorderPane tableBorderPane = new BorderPane();
        AnchorPane.setBottomAnchor(tableBorderPane, 0.0d);
        AnchorPane.setLeftAnchor(tableBorderPane, 0.0d);
        AnchorPane.setRightAnchor(tableBorderPane, 0.0d);
        AnchorPane.setTopAnchor(tableBorderPane, 0.0d);

        anchorPaneTable.getChildren().add(tableBorderPane);

        ScrollPane tableScrollPane = new ScrollPane();
        tableButtonBar = new ButtonAdvancedBar();
        table = new TableViewComponent<>(tableResourceBundle);
        tableScrollPane.setContent(table);
        tableBorderPane.setCenter(tableScrollPane);
        tableBorderPane.setBottom(tableButtonBar);

        table.enableMultiSelection(multiSelection);
        table.initialize(tableObjectClass, viewType, tableDescriptor);

        if (initFormDynamic) {
            formWrapper = new DynamicFormWrapper<>(formResourceBundle, formObjectClass);
            formFieldMappers.forEach((s, fieldTypeValueMapper) -> {
                formWrapper.getFormDynamicData().registerMapper(s, fieldTypeValueMapper);
            });
            ((DynamicFormWrapper<?>) formWrapper).initForm();
            formWrapper.buildForm();
        }

        {
            if (showForm) {
                SplitPane splitPane = new SplitPane();
                splitPane.setDividerPositions(0.55d);

                AnchorPane anchorPaneForm = new AnchorPane();
                anchorPaneForm.setMinHeight(0.0);
                anchorPaneForm.setMinWidth(0.0);

                BorderPane formBorderPane = new BorderPane();
                AnchorPane.setBottomAnchor(formBorderPane, 0.0d);
                AnchorPane.setLeftAnchor(formBorderPane, 0.0d);
                AnchorPane.setRightAnchor(formBorderPane, 0.0d);
                AnchorPane.setTopAnchor(formBorderPane, 0.0d);

                anchorPaneForm.getChildren().add(formBorderPane);
                splitPane.getItems().addAll(anchorPaneTable, anchorPaneForm);

                ScrollPane formScrollPane = new ScrollPane();
                anchorPane.getChildren().add(splitPane);

                ButtonBar formButtonBar = new ButtonBar();
                formScrollPane.setContent(formWrapper.getFormRenderer());

                formBorderPane.setCenter(formScrollPane);
                formBorderPane.setBottom(formButtonBar);

                Button formOkBtn = new Button("Ok");
                Button formCancelBtn = new Button("Cancel");
                formButtonBar.getButtons().addAll(formOkBtn, formCancelBtn);

                formOkBtn.addEventHandler(ActionEvent.ACTION, event -> {
                    FORM_OBJECT newObject = formWrapper.getObjectFromForm();
                    table.getItems().add(translator.getFirstObject(newObject));
                    table.refresh();
                });
                formCancelBtn.addEventHandler(ActionEvent.ACTION, event -> {
                    formWrapper.getForm().reset();
                });

                table.setOnMouseClicked(mouseEvent -> {
                    TABLE_OBJECT selectedItem = table.getSelectionModel().getSelectedItem();
                    formWrapper.setFormDataFromObject(translator.getSecondObject(selectedItem));
                });
            } else {
                anchorPane.getChildren().add(anchorPaneTable);
            }
        }

        rootPane.getChildren().add(anchorPane);

        // Initialize buttons
        {
            // todo make this button dynamic.
            // todo add resource for buttons
            Button createBtn = new Button("Create");
            Button updateBtn = new Button("Update");
            Button deleteBtn = new Button("Delete");

            if (showForm) {
                tableButtonBar.getButtons().addAll(createBtn, deleteBtn);
            } else {
                tableButtonBar.getButtons().addAll(createBtn, updateBtn, deleteBtn);
            }

            createBtn.addEventHandler(ActionEvent.ACTION, event -> {
                if (showForm) {
                    formWrapper.clearForm();
                } else {
                    BaseDialog baseDialog = new BaseDialog();
                    baseDialog.setContent(formWrapper.getFormRenderer());
                    baseDialog.showAndWait();

                    if (baseDialog.isOkFlag()) {
                        FORM_OBJECT newObject = formWrapper.getObjectFromForm();
                        table.getItems().add(translator.getFirstObject(newObject));
                        table.refresh();
                    }
                }
            });

            updateBtn.addEventHandler(ActionEvent.ACTION, event -> {
                BaseDialog baseDialog = new BaseDialog();
                TABLE_OBJECT selectedObject = table.getSelectionModel().getSelectedItem();
                formWrapper.setFormDataFromObject(translator.getSecondObject(selectedObject));
                baseDialog.setContent(formWrapper.getFormRenderer());
                baseDialog.showAndWait();

                if (baseDialog.isOkFlag()) {
                    int index = table.getSelectionModel().getSelectedIndex();
                    table.getItems().remove(table.getSelectionModel().getSelectedItem());
                    FORM_OBJECT newObject = formWrapper.getObjectFromForm();
                    table.getItems().add(index, translator.getFirstObject(newObject));
                    table.refresh();
                }
            });

            deleteBtn.addEventHandler(ActionEvent.ACTION, event -> {
                table.getItems().remove(table.getSelectionModel().getSelectedItem());
            });
        }
    }

    public ButtonAdvancedBar getTableButtonBar() {
        return tableButtonBar;
    }

    public VBox getRootPane() {
        return rootPane;
    }

    public TableViewComponent getTable() {
        return table;
    }

    public void setTableData(ObservableList<TABLE_OBJECT> tebleObservableList) {
        table.setItems(tebleObservableList);
    }
}
