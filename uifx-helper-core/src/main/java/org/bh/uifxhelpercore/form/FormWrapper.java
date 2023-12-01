package org.bh.uifxhelpercore.form;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.util.ResourceBundleService;
import com.dlsc.formsfx.model.validators.DoubleRangeValidator;
import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import org.bh.uifxhelpercore.FormsFxHelper;

public class FormWrapper {

    private Form form;
    private FormRenderer formRenderer;

    private FormDynamicData formDynamicData = new FormDynamicData();

    private ResourceBundleService resourceBundleService;

    private int labelPercentageSize;

    public FormWrapper(ResourceBundleService resourceBundleService) {
        this.resourceBundleService = resourceBundleService;
        labelPercentageSize = 50;
    }


    public void buildDynamicForm(Class<?> formObject) {
        formDynamicData.parseFormObjectAsFields(formObject);
        form = Form.of(
                formDynamicData.getGroupOfDynamicData()
        ).title("form_label").i18n(resourceBundleService);

        buildForm();
    }

    public void buildForm() {
        formRenderer = new FormRenderer(form);
        FormsFxHelper.searchAndSetControlsLabelWidth(formRenderer, labelPercentageSize);
    }

    public FormRenderer getFormRenderer() {
        return formRenderer;
    }

}
