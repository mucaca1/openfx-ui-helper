package org.bh.uifxhelpercore.locale;

import com.dlsc.formsfx.model.util.ResourceBundleService;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LocalizationHelper {

    private static LocalizationHelper singleton;

    private String locale;

    private Map<String, Map<String, ResourceBundle>> resourceBundleMap;
    private Map<String, ResourceBundleService> resourceBundleServiceMap;

    public LocalizationHelper() {
        locale = "en";
        resourceBundleServiceMap = new HashMap<>();
        resourceBundleMap = new HashMap<>();
    }

    public static LocalizationHelper get() {
        if (singleton == null) {
            singleton = new LocalizationHelper();
        }
        return singleton;
    }

    public boolean isResourceBundleRegistered(String key) {
        return resourceBundleServiceMap.containsKey(key);
    }

    public void setLocale(String locale) {
        this.locale = locale;
        for (String resourceKey : resourceBundleServiceMap.keySet()) {
            ResourceBundle resourceBundle = resourceBundleMap.get(resourceKey).get(this.locale);
            resourceBundleServiceMap.get(resourceKey).changeLocale(resourceBundle);
        }
    }

    public void registerResourceBundleService(String key, String language, ResourceBundle resourceBundle) {
        ResourceBundleService resourceBundleService = new ResourceBundleService(resourceBundle);

        if (!resourceBundleMap.containsKey(key)) {
            resourceBundleMap.put(key, new HashMap<>());
        }
        if (resourceBundleMap.containsKey(language)) {
            return;
        }
        Map<String, ResourceBundle> stringResourceBundleMap = resourceBundleMap.get(key);
        stringResourceBundleMap.put(language, resourceBundle);

        resourceBundleServiceMap.put(key, resourceBundleService);
    }

    public ResourceBundleService getResourceBundleService(String key) {
        return resourceBundleServiceMap.get(key);
    }


}
