package org.bh.uifxhelpercore.table.builder;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import javafx.scene.Node;
import org.bh.uifxhelpercore.form.builder.FormBuilder;

public class TableBuilder<T> {

    private Class<T> tableObject;

    private ResourceBundleService resourceBundleService;

    private boolean enableSearchBar;

    private boolean enablePaging;

    public TableBuilder() {
        tableObject = null;
        resourceBundleService = null;
        enableSearchBar = false;
        enablePaging = false;
    }

    public TableBuilder<T> setResourceBundleService(ResourceBundleService resourceBundleService) {
        this.resourceBundleService = resourceBundleService;
        return this;
    }

    public TableBuilder<T> createDynamicFormFromObject(Class<T> tableObject) {
        this.tableObject = tableObject;
        return this;
    }

    public TableBuilder<T> enableSearchBar(boolean enableSearchBar) {
        this.enableSearchBar = enableSearchBar;
        return this;
    }

    public TableBuilder<T> enablePaging(boolean enablePaging) {
        this.enablePaging = enablePaging;
        return this;
    }

    public Node build() {
        return null;
    }
}
