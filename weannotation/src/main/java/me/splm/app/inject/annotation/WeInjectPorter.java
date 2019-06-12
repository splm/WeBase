package me.splm.app.inject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface WeInjectPorter {

    int parentViewID() default -1;//父容器控件id

    int viewID() default -1;//控件id

    String parentView() default "";

    int value() default -1;//布局文件id

    Whether noTitle() default Whether.NO;//default value is not showing title

    Whether fullScreen() default Whether.NO;//defaut is not fullScreen
}
