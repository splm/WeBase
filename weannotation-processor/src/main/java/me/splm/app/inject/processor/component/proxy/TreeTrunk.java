package me.splm.app.inject.processor.component.proxy;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;


public class TreeTrunk extends TreeRoot {
    private Set<TreeLeavesFields> mFields=new LinkedHashSet<>();
    private Set<TreeBranchesMethods> mMethods=new LinkedHashSet<>();
    private TypeElement mTypeElement;

    public static TreeTrunk getRootClass(Map<String,TreeTrunk> mTypeMapper, Element e){
        String name=e.getSimpleName().toString();
        TreeTrunk treeTrunk =mTypeMapper.get(name);
        if(treeTrunk ==null){
            treeTrunk =new TreeTrunk((TypeElement) e);
            mTypeMapper.put(name, treeTrunk);
        }
        return treeTrunk;
    }

    private TreeTrunk(TypeElement element){
        super(element);
        this.mTypeElement=element;
    }

    public TypeElement getTypeElement(){
        return this.mTypeElement;
    }

    public void bindMemberOfFields(TreeLeavesFields field){
        mFields.add(field);
    }

    public void bindMemberOfMethods(TreeBranchesMethods method){
        mMethods.add(method);
    }

    public Set<TreeLeavesFields> fetchMemberOffileds(){
        return this.mFields;
    }

    public Set<TreeBranchesMethods> fetcheMemberOfMethods(){
        return this.mMethods;
    }

    public void executeTask(ActionTaskQueue queue, ProcessingEnvironment processingEnvironment){
        if(queue.isEmpty()){
            throw new IllegalArgumentException("ActionTaskQueue's size is 0");
        }
        for(ActionTask task:queue.values()){
            task.performAction(processingEnvironment);
        }
    }

}
