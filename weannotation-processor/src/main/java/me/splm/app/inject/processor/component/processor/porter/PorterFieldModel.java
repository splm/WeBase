package me.splm.app.inject.processor.component.processor.porter;

import com.squareup.javapoet.ClassName;

import me.splm.app.inject.processor.code.WeVar;


public class PorterFieldModel {
    private String name;
    private Object value;
    private ClassName className;
    public PorterFieldModel(WeVar weVar) {
        this.name=weVar.getFieldName();
        this.value=weVar.getIllusionValue();
        this.className=weVar.toClassType();
    }

    public String getName() {
        return name;
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
}
