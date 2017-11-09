package me.splm.app.inject.processor.code;


import com.squareup.javapoet.TypeSpec;

public class WeCodeModel {
    private WeClass mWeClass;
    public WeClass createClass(String name){
        return this.createClass(WeMod.PUBLIC,name);
    }

    public WeClass createClass(int modifier,String name){
        return this.createClass(modifier,false,false,false,name);
    }

    public WeClass createClass(int modifier,boolean isSychronized,boolean isFinal,boolean isStatic,String name){
        return mWeClass=new WeClass(modifier,name);
    }

    public TypeSpec build(){
        return mWeClass.build();
    }
}
