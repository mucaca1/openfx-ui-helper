package org.bh.uifxhelpercore.locale;

import com.dlsc.formsfx.model.util.ResourceBundleService;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Helper for holding resources in singleton instance.
 */
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

    /**
     * return resource bundle service for key.
     * @param key key
     * @return Resource Bundle Service
     */
    public ResourceBundleService getResourceBundleService(String key) {
        return resourceBundleServiceMap.get(key);
    }


}
