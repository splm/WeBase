package me.splm.app.inject.processor.component.proxy;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;


public class TreeBranchesMethods extends TreeRoot {
    private ExecutableElement element;

    public TreeBranchesMethods(ExecutableElement element){
        super(element);
        this.element=element;
    }

    public ExecutableElement getExecutableElement(){
        return this.element;
    }

    public TypeMirror getReturnType(){
        return element.getReturnType();
    }

    public List<? extends VariableElement> getParameters(){
        return this.element.getParameters();
    }

}
