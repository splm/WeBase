package me.splm.app.inject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface WeInjectFeature {
    int style();
}
