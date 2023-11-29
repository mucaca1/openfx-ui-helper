package org.bh.uifxhelpercore.field;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import org.bh.uifxhelpercore.field.entity.SimpleEntityChooserField;

/**
 * Simple helper for construct or manage form field
 */
public class FieldHelper {

    /**
     * Create form field for selecting complex object.
     * @param items Static objects
     * @param singleSelection Enable or disable single selection.
     * @return Field
     * @param <T> Type of selected object
     */
    public static <T> SimpleEntityChooserField<T> ofEntityChooser(ListProperty<T> items, boolean singleSelection) {
        return new SimpleEntityChooserField<T>(new SimpleListProperty<>(items.getValue()), singleSelection).bind(items);
    }
}
