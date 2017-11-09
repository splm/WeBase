package me.splm.app.inject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface WeInjectBeadle {
    String serial() default "";
    String taskID() default "";
    long delay() default 0L;
}
