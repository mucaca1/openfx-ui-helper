package org.bh.uifxhelpercore.editor;

import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.bh.uifxhelpercore.button.ButtonAdvancedBar;
import org.bh.uifxhelpercore.button.ButtonType;
import org.bh.uifxhelpercore.editor.builder.BasicEditorBuilder;
import org.bh.uifxhelpercore.form.DynamicFormWrapper;
import org.bh.uifxhelpercore.form.FormWrapper;
import org.bh.uifxhelpercore.table.TableViewComponent;

/**
 * This object represent basic editor, witch contain data in table and data representation as form for specific object.
 * This editor contains simple actions for add, update and delete with basic implementation.
 * @param <TABLE_OBJECT>
 * @param <FORM_OBJECT>
 */
public class BasicEditor<TABLE_OBJECT, FORM_OBJECT> extends BorderPane {

    protected DataBrowser<TABLE_OBJECT> dataBrowser;
    protected ButtonAdvancedBar tableButtonBar;

    protected DynamicFormWrapper<FORM_OBJECT> formWrapper;

    /**
     * This translator can translate table object, into form object.
     */
    private final ObjectTranslator<TABLE_OBJECT, FORM_OBJECT> translator;

    private final EditorObjectEventHandler<FORM_OBJECT> eventHandler;

    public BasicEditor(BasicEditorBuilder<TABLE_OBJECT, FORM_OBJECT> builder) {
        this.eventHandler = builder.getEventHandler();
        this.translator = builder.getObjectTranslator();

        // Init main component table.

        tableButtonBar = new ButtonAdvancedBar(builder.getButtonResourceBundle());
        dataBrowser = builder.getDataBrowserBuilder().build();

        BorderPane tableBorderPane = new BorderPane();
        tableBorderPane.setCenter(dataBrowser);
        tableBorderPane.setBottom(tableButtonBar);

        formWrapper = builder.getFormBuilder().build();

        {
            if (builder.isShowForm()) {
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

                ButtonAdvancedBar formButtonBar = new ButtonAdvancedBar(builder.getButtonResourceBundle());
                formScrollPane.setContent(formWrapper.getFormRenderer());

                formBorderPane.setCenter(formScrollPane);
                formBorderPane.setBottom(formButtonBar);

                formButtonBar.addButtons(ButtonType.OK, ButtonType.CANCEL);

                formButtonBar.addActionListener(ButtonType.OK, event -> {
                    FORM_OBJECT newObject = formWrapper.getObjectFromForm();
                    if (this.eventHandler != null) {
                        newObject = this.eventHandler.handleEvent(ObjectEvent.CREATE, newObject);
                    }
                    boolean isEmptySelection = dataBrowser.getTableComponent().getSelectionModel().isEmpty();
                    TABLE_OBJECT newOrUpdatedTableObject = translator.getFirstObject(newObject);
                    if (!isEmptySelection) {
                        this.dataBrowser.getData().replaceAll(tableObj -> {
                            if (tableObj.equals(newOrUpdatedTableObject)) {
                                return newOrUpdatedTableObject;
                            }
                            return tableObj;
                        });

                    } else {
                        dataBrowser.getData().add(newOrUpdatedTableObject);
                    }
                    dataBrowser.refreshData();
                });
                formButtonBar.addActionListener(ButtonType.CANCEL, event -> {
                    formWrapper.getForm().reset();
                });

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
            if (builder.isShowForm()) {
                tableButtonBar.addButtons(ButtonType.CREATE, ButtonType.DELETE);
            } else {
                tableButtonBar.addButtons(ButtonType.CREATE, ButtonType.UPDATE, ButtonType.DELETE);
            }

            tableButtonBar.addActionListener(ButtonType.CREATE, event -> {
                dataBrowser.getTableComponent().getSelectionModel().clearSelection();
                formWrapper.createNewFormObject();
                if (builder.isShowForm()) {
                    formWrapper.clearForm();
                } else {
                    BaseDialog baseDialog = new BaseDialog();
                    baseDialog.setContent(formWrapper.getFormRenderer());
                    baseDialog.showAndWait();

                    if (!baseDialog.isOkFlag()) {
                        return;
                    }

                    FORM_OBJECT newObject = formWrapper.getObjectFromForm();
                    if (this.eventHandler != null) {
                        newObject = this.eventHandler.handleEvent(ObjectEvent.CREATE, newObject);
                    }
                    dataBrowser.getData().add(translator.getFirstObject(newObject));
                    dataBrowser.refreshData();
                }
            });

            if (!builder.isShowForm()) {
                tableButtonBar.addActionListener(ButtonType.UPDATE, event -> {
                    BaseDialog baseDialog = new BaseDialog();
                    TABLE_OBJECT selectedObject = dataBrowser.getTableComponent().getSelectionModel().getSelectedItem();
                    formWrapper.setFormDataFromObject(translator.getSecondObject(selectedObject));
                    baseDialog.setContent(formWrapper.getFormRenderer());
                    baseDialog.showAndWait();

                    if (!baseDialog.isOkFlag()) {
                        return;
                    }

                    FORM_OBJECT newObject = formWrapper.getObjectFromForm();
                    if (this.eventHandler != null) {
                        newObject = this.eventHandler.handleEvent(ObjectEvent.UPDATE, newObject);
                    }
                    TABLE_OBJECT updatedTableObject = translator.getFirstObject(newObject);

                    this.dataBrowser.getData().replaceAll(tableObj -> {
                        if (tableObj.equals(updatedTableObject)) {
                            return updatedTableObject;
                        }
                        return tableObj;
                    });
                });
            }


            tableButtonBar.addActionListener(ButtonType.DELETE, event -> {
                TABLE_OBJECT t = dataBrowser.getTableComponent().getSelectionModel().getSelectedItem();
                if (this.eventHandler != null) {
                    this.eventHandler.handleEvent(ObjectEvent.DELETE, translator.getSecondObject(t));
                }
                dataBrowser.getData().remove(dataBrowser.getTableComponent().getSelectionModel().getSelectedItem());
                dataBrowser.refreshData();
            });
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
