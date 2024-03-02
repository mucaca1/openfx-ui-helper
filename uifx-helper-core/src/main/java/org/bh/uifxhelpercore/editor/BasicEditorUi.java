package org.bh.uifxhelpercore.editor;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.bh.uifxhelpercore.button.ButtonAdvancedBar;
import org.bh.uifxhelpercore.button.ButtonType;
import org.bh.uifxhelpercore.form.DynamicFormWrapper;
import org.bh.uifxhelpercore.form.FieldTypeValueMapper;
import org.bh.uifxhelpercore.form.FieldValueMapper;
import org.bh.uifxhelpercore.form.FormWrapper;
import org.bh.uifxhelpercore.table.TableViewComponent;
import org.bh.uifxhelpercore.table.ViewType;

import java.util.Map;

/**
 * This object represent basic editor, witch contain data in table and data representation as form for specific object.
 * This editor contains simple actions for add, update and delete with basic implementation.
 * @param <TABLE_OBJECT>
 * @param <FORM_OBJECT>
 */
public class BasicEditorUi<TABLE_OBJECT, FORM_OBJECT> {

    protected VBox rootPane;
    protected TableViewComponent<TABLE_OBJECT> table;
    protected ButtonAdvancedBar tableButtonBar;

    protected FormWrapper<FORM_OBJECT> formWrapper;

    /**
     * This translator can translate table object, into form object.
     */
    private final ObjectTranslator<TABLE_OBJECT, FORM_OBJECT> translator;

    private final EditorObjectEventHandler<FORM_OBJECT> eventHandler;

    public BasicEditorUi(Class<TABLE_OBJECT> tableObjectClass,
                         Class<FORM_OBJECT> formObjectClass,
                         ObjectTranslator<TABLE_OBJECT, FORM_OBJECT> objectTranslator,
                         ViewType viewType,
                         String tableDescriptor,
                         ResourceBundleService tableResourceBundle,
                         ResourceBundleService formResourceBundle,
                         ResourceBundleService buttonResourceBundle,
                         boolean multiSelection,
                         boolean initFormDynamic,
                         boolean showForm,
                         Map<String, FieldTypeValueMapper> formFieldMappers,
                         Map<String, FieldValueMapper> formFieldValueMappers,
                         EditorObjectEventHandler<FORM_OBJECT> eventHandler) {
        this.eventHandler = eventHandler;
        this.translator = objectTranslator;
        {
            rootPane = new VBox();
            rootPane.setAlignment(Pos.CENTER);
            VBox.setVgrow(rootPane, Priority.ALWAYS);
        }

        // Init main component table.

        BorderPane tableBorderPane = new BorderPane();
        AnchorPane.setBottomAnchor(tableBorderPane, 0.0d);
        AnchorPane.setLeftAnchor(tableBorderPane, 0.0d);
        AnchorPane.setRightAnchor(tableBorderPane, 0.0d);
        AnchorPane.setTopAnchor(tableBorderPane, 0.0d);

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
                ((DynamicFormWrapper<?>)formWrapper).getFormDynamicData().registerMapper(s, fieldTypeValueMapper);
            });
            formFieldValueMappers.forEach((s, fieldValueMapper) -> {
                ((DynamicFormWrapper<?>)formWrapper).getFormDynamicData().registerValueMapper(s, fieldValueMapper);
            });
            ((DynamicFormWrapper<?>) formWrapper).initForm();
            formWrapper.buildForm();
        }

        {
            if (showForm) {
                SplitPane splitPane = new SplitPane();
                splitPane.setDividerPositions(0.55d);
                splitPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                AnchorPane anchorPaneForm = new AnchorPane();
                anchorPaneForm.setMinHeight(0.0);
                anchorPaneForm.setMinWidth(0.0);

                BorderPane formBorderPane = new BorderPane();
                AnchorPane.setBottomAnchor(formBorderPane, 0.0d);
                AnchorPane.setLeftAnchor(formBorderPane, 0.0d);
                AnchorPane.setRightAnchor(formBorderPane, 0.0d);
                AnchorPane.setTopAnchor(formBorderPane, 0.0d);

                anchorPaneForm.getChildren().add(formBorderPane);
                splitPane.getItems().addAll(tableBorderPane, anchorPaneForm);

                ScrollPane formScrollPane = new ScrollPane();
                formScrollPane.setFitToWidth(true);

                ButtonAdvancedBar formButtonBar = new ButtonAdvancedBar();
                formScrollPane.setContent(formWrapper.getFormRenderer());

                formBorderPane.setCenter(formScrollPane);
                formBorderPane.setBottom(formButtonBar);
                
                formButtonBar.addButtons(ButtonType.OK, ButtonType.CANCEL);

                formButtonBar.addActionListener(ButtonType.OK, event -> {
                    FORM_OBJECT newObject = formWrapper.getObjectFromForm();
                    if (this.eventHandler != null) {
                        newObject = this.eventHandler.handleEvent(ObjectEvent.CREATE, newObject);
                    }
                    int selectedTableObjectIndex = table.getSelectionModel().getSelectedIndex();
                    if (selectedTableObjectIndex != -1) {
                        table.getItems().remove(selectedTableObjectIndex);
                        table.getItems().add(selectedTableObjectIndex, translator.getFirstObject(newObject));
                    } else {
                        table.getItems().add(translator.getFirstObject(newObject));
                    }

                    table.refresh();
                });
                formButtonBar.addActionListener(ButtonType.CANCEL, event -> {
                    formWrapper.getForm().reset();
                });
                formButtonBar.setResourceBundleService(buttonResourceBundle);

                table.setOnMouseClicked(mouseEvent -> {
                    TABLE_OBJECT selectedItem = table.getSelectionModel().getSelectedItem();
                    formWrapper.setFormDataFromObject(translator.getSecondObject(selectedItem));
                });

                formWrapper.getFormRenderer().prefWidthProperty().bind(formBorderPane.prefWidthProperty());

//                formRenderer.prefWidthProperty().bind(form.getRightBorderPane().prefWidthProperty());
                rootPane.getChildren().add(splitPane);
            } else {
                rootPane.getChildren().add(tableBorderPane);
            }
        }


        // Initialize buttons
        {
            // todo make this button dynamic.
            // todo add resource for buttons
            if (showForm) {
                tableButtonBar.addButtons(ButtonType.CREATE, ButtonType.DELETE);
            } else {
                tableButtonBar.addButtons(ButtonType.CREATE, ButtonType.UPDATE, ButtonType.DELETE);
            }

            tableButtonBar.addActionListener(ButtonType.CREATE, event -> {
                table.getSelectionModel().clearSelection();
                if (showForm) {
                    if (initFormDynamic) {
                        ((DynamicFormWrapper<?>)formWrapper).clearForm();
                    }
                } else {
                    BaseDialog baseDialog = new BaseDialog();
                    baseDialog.setContent(formWrapper.getFormRenderer());
                    baseDialog.showAndWait();

                    if (baseDialog.isOkFlag()) {
                        FORM_OBJECT newObject = formWrapper.getObjectFromForm();
                        if (this.eventHandler != null) {
                            newObject = this.eventHandler.handleEvent(ObjectEvent.CREATE, newObject);
                        }
                        table.getItems().add(translator.getFirstObject(newObject));
                        table.refresh();
                    }
                }
            });

            if (!showForm) {
                tableButtonBar.addActionListener(ButtonType.UPDATE, event -> {
                    BaseDialog baseDialog = new BaseDialog();
                    TABLE_OBJECT selectedObject = table.getSelectionModel().getSelectedItem();
                    formWrapper.setFormDataFromObject(translator.getSecondObject(selectedObject));
                    baseDialog.setContent(formWrapper.getFormRenderer());
                    baseDialog.showAndWait();

                    if (baseDialog.isOkFlag()) {
                        int index = table.getSelectionModel().getSelectedIndex();
                        table.getItems().remove(table.getSelectionModel().getSelectedItem());
                        FORM_OBJECT newObject = formWrapper.getObjectFromForm();
                        if (this.eventHandler != null) {
                            newObject = this.eventHandler.handleEvent(ObjectEvent.UPDATE, newObject);
                        }
                        table.getItems().add(index, translator.getFirstObject(newObject));
                        table.refresh();
                    }
                });
            }


            tableButtonBar.addActionListener(ButtonType.DELETE, event -> {
                TABLE_OBJECT t = table.getSelectionModel().getSelectedItem();
                if (this.eventHandler != null) {
                    this.eventHandler.handleEvent(ObjectEvent.DELETE, translator.getSecondObject(t));
                }
                table.getItems().remove(table.getSelectionModel().getSelectedItem());
            });

            tableButtonBar.setResourceBundleService(buttonResourceBundle);
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

    public FormWrapper<FORM_OBJECT> getFormWrapper() {
        return formWrapper;
    }
}
