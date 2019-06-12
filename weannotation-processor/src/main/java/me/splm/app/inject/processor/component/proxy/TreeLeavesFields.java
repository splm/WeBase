package me.splm.app.inject.processor.component.proxy;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;


public class TreeLeavesFields extends TreeRoot {
    private String name;
    private TypeMirror typeMirror;
    private TypeMirror subClass;
    private VariableElement variableElement;

    public TreeLeavesFields(VariableElement element){
        super(element);
        this.variableElement=element;
        this.name=element.getSimpleName().toString();
        this.typeMirror=element.asType();
        this.subClass=element.getEnclosingElement().asType();
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String getSubName() {
        return getOwnMirror().toString();
    }

    @Override
    public String getSimpleName() {
        return splitName(getAbstractName(), false);
    }

    @Override
    public String getPackageName() {
        String name=getSubName();
        return splitName(name);
    }

    @Override
    public String getAbstractName() {
        return getSubName();
    }

    public VariableElement getVariableElement(){
        return this.variableElement;
    }

    public TypeMirror getOwnMirror(){
        return this.typeMirror;
    }

    public TypeMirror getSubMirror(){
        return this.subClass;
    }
}
