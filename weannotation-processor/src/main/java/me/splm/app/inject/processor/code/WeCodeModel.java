package me.splm.app.inject.processor.code;


import com.squareup.javapoet.TypeSpec;

public class WeCodeModel {
    private WeClass mWeClass;

    /**
     * Do create a class which you'd like to use without delegating any Modifiers.
     * @param pkg the class will be settled
     * @param name class's name
     * @return What class you wanna create.
     */
    public WeClass createClass(String pkg,String name){
        return this.createClass(WeMod.PUBLIC,pkg,name);
    }

    /**
     * {@link #createClass(String, String)}
     * @param modifier If you do not know how set this modifier,you'd better check WeMod
     * @param pkg
     * @param name
     * @return
     */
    public WeClass createClass(int modifier,String pkg,String name){
        return mWeClass=new WeClass(modifier,pkg,name);
    }

    public TypeSpec build(){
        return mWeClass.build();
    }
}
