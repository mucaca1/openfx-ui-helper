package org.bh.uifxhelpercore.table.builder;

import com.dlsc.formsfx.model.util.ResourceBundleService;
import org.bh.uifxhelpercore.table.TableViewComponent;
import org.bh.uifxhelpercore.table.ViewType;

public class TableBuilder<V> {

    private final Class<V> tableObject;

    private ResourceBundleService resourceBundleService;

    private ViewType viewType;

    private String descriptor;

    private boolean multiSelectionEnabled;

    public TableBuilder(Class<V> tableObject) {
        this.tableObject = tableObject;
        viewType = ViewType.Default;
        resourceBundleService = null;
        descriptor = "";
        multiSelectionEnabled = false;
    }

    public TableBuilder<V> setResourceBundleService(ResourceBundleService resourceBundleService) {
        this.resourceBundleService = resourceBundleService;
        return this;
    }

    public TableBuilder<V> setDescriptor(String descriptor) {
        this.descriptor = descriptor;
        return this;
    }

    public TableBuilder<V> setViewType(ViewType viewType) {
        this.viewType = viewType;
        return this;
    }

    public TableBuilder<V> setMultiSelectionEnabled(boolean multiSelectionEnabled) {
        this.multiSelectionEnabled = multiSelectionEnabled;
        return this;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public Class<V> getTableObject() {
        return tableObject;
    }

    public ResourceBundleService getResourceBundleService() {
        return resourceBundleService;
    }

    public boolean isMultiSelectionEnabled() {
        return multiSelectionEnabled;
    }

    public TableViewComponent<V> build() {
        return new TableViewComponent<>(this);
    }
}
