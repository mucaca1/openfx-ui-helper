package org.bh.uifxhelpercore.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FormField {

    FieldType type();

    String fieldName() default "";

    String fieldLabel() default "";

    String getter() default "";

    String setter() default "";

    String section() default "";

    boolean editable() default true;

    int order() default -1;

}
