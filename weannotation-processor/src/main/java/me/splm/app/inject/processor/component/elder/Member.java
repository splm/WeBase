package me.splm.app.inject.processor.component.elder;

import java.util.LinkedHashSet;
import java.util.Set;

@Deprecated
public class Member extends AbsMember {
    private Set<String> fields=new LinkedHashSet<>();
    private Set<String> methods=new LinkedHashSet<>();

    public void addFields(String fieldName){
        add(fields,fieldName);
    }

    public boolean removeField(String fieldName){
        return remove(fields,fieldName);
    }

    public void addMethod(String methodName){
        add(methods,methodName);
    }

    public boolean removeMethod(String methodName){
        return remove(methods,methodName);
    }

    public Set<String> getFields(){
        return fields;
    }
    public Set<String> getMethods(){
        return methods;
    }
}
