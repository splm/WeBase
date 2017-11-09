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

    public TypeMirror getOwnMirror(){
        return this.typeMirror;
    }

    public TypeMirror getSubMirror(){
        return this.subClass;
    }
}
