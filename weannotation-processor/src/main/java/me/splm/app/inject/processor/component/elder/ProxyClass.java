package me.splm.app.inject.processor.component.elder;

import me.splm.app.inject.annotation.WeInjectPorter;
import me.splm.app.inject.processor.component.proxy.TreeLeavesFields;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;


@Deprecated
public class ProxyClass {
    public static final String TARGET_PACKAGE_NAME = "com.jc.android.auto";
    private TypeElement mTypeElement;
    private Set<TreeLeavesFields> mFields=new LinkedHashSet<>();
    private static final String SUFFIX="$$Proxy";
    public ProxyClass(TypeElement element){
        this.mTypeElement=element;
    }

    public void bindMember(TreeLeavesFields member){
        mFields.add(member);
    }

    public Set<TreeLeavesFields> fetchMembers(){
        return this.mFields;
    }

    public JavaFile generateFile(){
        String cName=mTypeElement.getSimpleName().toString();
        ClassName clazzName = ClassName.get(TARGET_PACKAGE_NAME, "We" + cName+SUFFIX);
        WeInjectPorter porter=mTypeElement.getAnnotation(WeInjectPorter.class);
        /*Class<?> bindingClazz=porter.binding();
        int id=porter.id();
        Class<?> viewModel=porter.viewModel();*/
        TypeName classOfBridgeData = ClassName.get(TARGET_PACKAGE_NAME, "We" + cName);
        TypeSpec targetClazz = TypeSpec.classBuilder("We"+ cName+SUFFIX)
                .addModifiers(Modifier.PUBLIC)
                .build();
        JavaFile javaFile = JavaFile.builder(TARGET_PACKAGE_NAME, targetClazz).build();
        return javaFile;
    }
}
