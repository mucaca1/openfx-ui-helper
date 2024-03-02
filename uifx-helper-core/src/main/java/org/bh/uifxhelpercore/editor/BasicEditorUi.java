package org.bh.uifxhelpercore.editor;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
public class BasicEditorUi<TABLE_OBJECT, FORM_OBJECT> extends BorderPane {

    protected DataBrowser<TABLE_OBJECT> dataBrowser;
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

        // Init main component table.

        tableButtonBar = new ButtonAdvancedBar();
        dataBrowser = new DataBrowser<>(tableObjectClass, viewType);

        BorderPane tableBorderPane = new BorderPane();
        tableBorderPane.setCenter(dataBrowser);
        tableBorderPane.setBottom(tableButtonBar);

        dataBrowser.getTableComponent().enableMultiSelection(multiSelection);

        if (initFormDynamic) {
            formWrapper = new DynamicFormWrapper<>(formResourceBundle, formObjectClass);
            formFieldMappers.forEach((s, fieldTypeValueMapper) -> {
                ((DynamicFormWrapper<?>)formWrapper).getFormDynamicData().registerMapper(s, fieldTypeValueMapper);
            });
            formFieldValueMappers.forEach((s, fieldValueMapper) -> {
                ((DynamicFormWrapper<?>)formWrapper).getFormDynamicData().registerValueMapper(s, fieldValueMapper);
            });
            ((DynamicFormWrapper<?>) formWrapper).buildForm();
            formWrapper.renderForm();
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
                    int selectedTableObjectIndex = dataBrowser.getTableComponent().getSelectionModel().getSelectedIndex();
                    if (selectedTableObjectIndex != -1) {
                        dataBrowser.getTableComponent().getItems().remove(selectedTableObjectIndex);
                        dataBrowser.getTableComponent().getItems().add(selectedTableObjectIndex, translator.getFirstObject(newObject));
                    } else {
                        dataBrowser.getTableComponent().getItems().add(translator.getFirstObject(newObject));
                    }

                    dataBrowser.getTableComponent().refresh();
                    dataBrowser.setData(dataBrowser.getTableComponent().getItems());
                });
                formButtonBar.addActionListener(ButtonType.CANCEL, event -> {
                    formWrapper.getForm().reset();
                });
                formButtonBar.setResourceBundleService(buttonResourceBundle);

                dataBrowser.getTableComponent().setOnMouseClicked(mouseEvent -> {
                    TABLE_OBJECT selectedItem = dataBrowser.getTableComponent().getSelectionModel().getSelectedItem();
                    formWrapper.setFormDataFromObject(translator.getSecondObject(selectedItem));
                });

                formWrapper.getFormRenderer().prefWidthProperty().bind(formBorderPane.prefWidthProperty());

                setCenter(splitPane);
            } else {
                setCenter(tableBorderPane);
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
                dataBrowser.getTableComponent().getSelectionModel().clearSelection();
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
                        dataBrowser.getTableComponent().getItems().add(translator.getFirstObject(newObject));
                        dataBrowser.getTableComponent().refresh();
                        dataBrowser.setData(dataBrowser.getTableComponent().getItems());
                    }
                }
            });

            if (!showForm) {
                tableButtonBar.addActionListener(ButtonType.UPDATE, event -> {
                    BaseDialog baseDialog = new BaseDialog();
                    TABLE_OBJECT selectedObject = dataBrowser.getTableComponent().getSelectionModel().getSelectedItem();
                    formWrapper.setFormDataFromObject(translator.getSecondObject(selectedObject));
                    baseDialog.setContent(formWrapper.getFormRenderer());
                    baseDialog.showAndWait();

                    if (baseDialog.isOkFlag()) {
                        int index = dataBrowser.getTableComponent().getSelectionModel().getSelectedIndex();
                        dataBrowser.getTableComponent().getItems().remove(dataBrowser.getTableComponent().getSelectionModel().getSelectedItem());
                        FORM_OBJECT newObject = formWrapper.getObjectFromForm();
                        if (this.eventHandler != null) {
                            newObject = this.eventHandler.handleEvent(ObjectEvent.UPDATE, newObject);
                        }
                        dataBrowser.getTableComponent().getItems().add(index, translator.getFirstObject(newObject));
                        dataBrowser.getTableComponent().refresh();
                        dataBrowser.setData(dataBrowser.getTableComponent().getItems());
                    }
                });
            }


            tableButtonBar.addActionListener(ButtonType.DELETE, event -> {
                TABLE_OBJECT t = dataBrowser.getTableComponent().getSelectionModel().getSelectedItem();
                if (this.eventHandler != null) {
                    this.eventHandler.handleEvent(ObjectEvent.DELETE, translator.getSecondObject(t));
                }
                dataBrowser.getTableComponent().getItems().remove(dataBrowser.getTableComponent().getSelectionModel().getSelectedItem());
                dataBrowser.setData(dataBrowser.getTableComponent().getItems());
            });

            tableButtonBar.setResourceBundleService(buttonResourceBundle);
        }
    }

    public ButtonAdvancedBar getTableButtonBar() {
        return tableButtonBar;
    }

    public TableViewComponent getDataBrowser() {
        return dataBrowser.getTableComponent();
    }

    public void setTableData(ObservableList<TABLE_OBJECT> tebleObservableList) {
        dataBrowser.setData(tebleObservableList);
    }

    public FormWrapper<FORM_OBJECT> getFormWrapper() {
        return formWrapper;
    }
}
