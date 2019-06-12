package me.splm.app.inject.processor.component.processor.porter;

import com.squareup.javapoet.ClassName;

import me.splm.app.inject.processor.code.WeVar;


public class PorterFieldModel {
    private String name;
    private Object value;
    private ClassName className;

    private String mExtraForString1;
    private String mExtraForString2;

    private int mExtraForInt1;
    private int mExtraForInt2;

    private Object mExtraForAll;
    public PorterFieldModel(WeVar weVar) {
        this.name=weVar.getFieldName();
        this.value=weVar.getIllusionValue();
        this.className=weVar.toClassType();
        this.mExtraForString1=weVar.mExtraForString1;
        this.mExtraForString2=weVar.mExtraForString2;
        this.mExtraForInt1=weVar.mExtraForInt1;
        this.mExtraForInt2=weVar.mExtraForInt2;
        this.mExtraForAll=weVar.mExtraForAll;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public ClassName getClassName() {
        return className;
    }

    public void setClassName(ClassName className) {
        this.className = className;
    }

    public String getExtraForString1() {
        return mExtraForString1;
    }

    public String getExtraForString2() {
        return mExtraForString2;
    }

    public int getExtraForInt1() {
        return mExtraForInt1;
    }

    public int getExtraForInt2() {
        return mExtraForInt2;
    }

    public Object getExtraForAll() {
        return mExtraForAll;
    }
}
