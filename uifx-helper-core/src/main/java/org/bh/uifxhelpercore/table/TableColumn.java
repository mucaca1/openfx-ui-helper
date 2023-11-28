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
     *
     * @return
     */
    String[] descriptor() default "";
}
