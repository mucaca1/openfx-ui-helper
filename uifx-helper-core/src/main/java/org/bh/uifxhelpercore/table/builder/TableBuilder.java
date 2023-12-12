package org.bh.uifxhelpercore.table.builder;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.scene.Node;
import org.bh.uifxhelpercore.form.builder.FormBuilder;

public class TableBuilder<T> {

    private Class<T> tableObject;

    private ResourceBundleService resourceBundleService;

    public TableBuilder() {
        tableObject = null;
        resourceBundleService = null;
    }

    public TableBuilder<T> setResourceBundleService(ResourceBundleService resourceBundleService) {
        this.resourceBundleService = resourceBundleService;
        return this;
    }

    public TableBuilder<T> createDynamicFormFromObject(Class<T> tableObject) {
        this.tableObject = tableObject;
        return this;
    }

    public Node build() {
        return null;
    }
}
