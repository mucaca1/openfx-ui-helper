package org.bh.uifxhelpercore.table;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate field, witch will be showed in table. This value will be showed as column.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableColumn {

    /**
     * Each table represent some view type. Annotated value will be used in associated view type.
     * @return type of view
     */
    ViewType[] viewType() default {ViewType.Default};

    /**
     * {@link TableViewComponent} can be initialized with specifis descriptor.
     * If field contains descriptor matched with used initialized descriptor, field will be used as column.
     * This descriptor is way, as user can deffined self specified viewType.
     * Empty value is always evaluated as true (as column) if {@link TableColumn#viewType()} match as well.
     * @return descriptor value
     */
    String[] descriptor() default "";

    /**
     * Index of current column. Default value is -1. This means column does not have specific order.
     * @return
     */
    short index() default -1;
}
