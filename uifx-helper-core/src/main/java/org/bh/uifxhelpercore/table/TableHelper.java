package org.bh.uifxhelpercore.table;

import java.lang.reflect.Field;
import java.util.*;


/**
 * Contains basic methods for constrain dynamic table from annotations.
 */
public class TableHelper {

    private static boolean isTableObject(Class<?> obj) {
        return obj.isAnnotationPresent(TableObject.class);
    }

    private static boolean isTableColumn(Field obj) {
        return obj.isAnnotationPresent(TableColumn.class);
    }

    /**
     * Analyse object and process table annotations.
     *
     * @param obj      Object to show in table
     * @param viewType Type of table view
     * @return All columns data to show
     */
    public static List<ColumnData> getColumnIdAndTranslateKey(Class<?> obj, ViewType viewType) {
        return getColumnIdAndTranslateKey(obj, viewType, "");
    }


    public static List<ColumnData> getColumnIdAndTranslateKey(Class<?> obj, ViewType viewType, String... descriptor) {
        List<ColumnData> returnList = new ArrayList<>();
        if (descriptor == null) {
            descriptor = new String[]{""};
        }
        Set<String> descriptors = new HashSet<>(Arrays.asList(descriptor));
        if (isTableObject(obj)) {
            for (Field declaredField : obj.getDeclaredFields()) {
                if (isTableColumn(declaredField)) {
                    TableColumn columnData = declaredField.getAnnotation(TableColumn.class);
                    for (ViewType type : columnData.viewType()) {
                        for (String s : columnData.descriptor()) {
                            if (type.equals(viewType) && descriptors.contains(s)) {
                                returnList.add(new ColumnData(declaredField.getName(), obj.getSimpleName() + "." + declaredField.getName(), declaredField.getType()));
                                break;
                            }
                        }
                    }
                }
            }
        }

        return returnList;
    }
}
