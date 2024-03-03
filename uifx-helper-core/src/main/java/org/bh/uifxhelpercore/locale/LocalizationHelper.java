package org.bh.uifxhelpercore.locale;

import com.dlsc.formsfx.model.util.ResourceBundleService;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Helper for holding resources in singleton instance.
 */
public class LocalizationHelper {

    public static final String DEFAULT_TABLE_RESOURCE_BUNDLE_KEY = "default-table";
    public static final String DEFAULT_BUTTON_RESOURCE_BUNDLE_KEY = "default-buttons";
    public static final String DEFAULT_FORM_RESOURCE_BUNDLE_KEY = "default-form";

    private static LocalizationHelper singleton;

    private String locale;

    private Map<String, Map<String, ResourceBundle>> resourceBundleMap;
    private Map<String, ResourceBundleService> resourceBundleServiceMap;

    public LocalizationHelper() {
        locale = "en";
        resourceBundleServiceMap = new HashMap<>();
        resourceBundleMap = new HashMap<>();
    }

    /**
     * Return singleton of localization helper.
     */
    public static LocalizationHelper get() {
        if (singleton == null) {
            singleton = new LocalizationHelper();
        }
        return singleton;
    }

    public boolean isResourceBundleRegistered(String key) {
        return resourceBundleServiceMap.containsKey(key);
    }

    /**
     * Set locale for all registered resources. All listeners on resource bundle get notification.
     * @param locale locale (like: 'en', 'de', ...)
     */
    public void setLocale(String locale) {
        this.locale = locale;
        for (String resourceKey : resourceBundleServiceMap.keySet()) {
            ResourceBundle resourceBundle = resourceBundleMap.get(resourceKey).get(this.locale);
            resourceBundleServiceMap.get(resourceKey).changeLocale(resourceBundle);
        }
    }

    /**
     * Register new resource bundle
     * @param key key
     * @param language language of resource bundle
     * @param resourceBundle resource bundle
     */
    public void registerResourceBundleService(String key, String language, ResourceBundle resourceBundle) {
        ResourceBundleService resourceBundleService = new ResourceBundleService(resourceBundle);

        if (!resourceBundleMap.containsKey(key)) {
            resourceBundleMap.put(key, new HashMap<>());
        }
        Map<String, ResourceBundle> stringResourceBundleMap = resourceBundleMap.get(key);
        if (stringResourceBundleMap.containsKey(language)) {
            return;
        }
        stringResourceBundleMap.put(language, resourceBundle);

        resourceBundleServiceMap.put(key, resourceBundleService);
    }

    public void registerDefaultTableBundleService(String language, ResourceBundle resourceBundle) {
        registerResourceBundleService(DEFAULT_TABLE_RESOURCE_BUNDLE_KEY, language, resourceBundle);
    }

    public void registerDefaultButtonBundleService(String language, ResourceBundle resourceBundle) {
        registerResourceBundleService(DEFAULT_BUTTON_RESOURCE_BUNDLE_KEY, language, resourceBundle);
    }

    public void registerDefaultFormBundleService(String language, ResourceBundle resourceBundle) {
        registerResourceBundleService(DEFAULT_FORM_RESOURCE_BUNDLE_KEY, language, resourceBundle);
    }

    /**
     * return resource bundle service for key.
     * @param key key
     * @return Resource Bundle Service
     */
    public ResourceBundleService getResourceBundleService(String key) {
        return resourceBundleServiceMap.get(key);
    }

    public ResourceBundleService getDefaultTableBundleService() {
        return resourceBundleServiceMap.get(DEFAULT_TABLE_RESOURCE_BUNDLE_KEY);
    }

    public ResourceBundleService getDefaultButtonBundleService() {
        return resourceBundleServiceMap.get(DEFAULT_BUTTON_RESOURCE_BUNDLE_KEY);
    }

    public ResourceBundleService getDefaultFormBundleService() {
        return resourceBundleServiceMap.get(DEFAULT_BUTTON_RESOURCE_BUNDLE_KEY);
    }
}
