package me.splm.app.inject.processor.code;


import com.squareup.javapoet.TypeSpec;

public class WeCodeModel {
    private WeClass mWeClass;

    public WeClass createClass(int modifier,String pkg,String name){
        return mWeClass=new WeClass(modifier,pkg,name);
    }

    public TypeSpec build(){
        return mWeClass.build();
    }
}
