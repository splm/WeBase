package me.splm.app.inject.processor.component.proxy;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public abstract class TreeRoot {
    private Element mElement;
    private List<? extends AnnotationMirror> mAnnotations = new ArrayList<>();
    private ProcessingEnvironment processingEnvironment;

    public List<? extends AnnotationMirror> fetchMemberOfAnnotations() {
        return this.mAnnotations;
    }

    public TreeRoot(Element element) {
        this.mElement = element;
    }

    public void bindMemberOfAnnotation(List<? extends AnnotationMirror> annotations) {
        this.mAnnotations = annotations;
    }

    public String getName(){
        return mElement.getSimpleName().toString();
    }

    public void setEnviroment(ProcessingEnvironment processingEnvironment){
        this.processingEnvironment=processingEnvironment;
    }

    public ProcessingEnvironment getEnviroment(){
        return this.processingEnvironment;
    }

    /**
     * Where the element locate in.
     * @return it's subclass
     */
    public String getSubName(){
        if(isClass(mElement)){
            return getName();
        }else{
            return mElement.getEnclosingElement().getSimpleName().toString();
        }
    }

    public String getSubAbsName(){
        if(isClass(mElement)){
            return getName();
        }else{
            return mElement.getEnclosingElement().toString();
        }
    }

    protected String splitName(String name){
        return splitName(name, true);
    }

    protected String splitName(String name,boolean first){
        int lastIndex = name.lastIndexOf(".");
        if(first){
            return name.subSequence(0, lastIndex).toString();
        }else{
            return name.substring(lastIndex+1);
        }
    }

    public String getSimpleName(){
        return getName();
    }

    public String getPackageName(){
        String absName=getAbstractName();
        return splitName(absName);
    }

    public String getAbstractName(){
        if(isClass(mElement)){
            return mElement.toString();
        }else{
            return mElement.getEnclosingElement().toString();
        }
    }

    private boolean isClass(Element element){
        if(element instanceof TypeElement){
            return true;
        }
        return false;
    }

    public Set<Modifier> getModifier() {
        return this.mElement.getModifiers();
    }
}
