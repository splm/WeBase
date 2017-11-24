package me.splm.app.inject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface WeInjectPorter {
    int layoutId();

    Whether noTitle() default Whether.NO;//default value is not showing title

    Whether fullScreen() default Whether.NO;//defaut is not fullScreen
}
