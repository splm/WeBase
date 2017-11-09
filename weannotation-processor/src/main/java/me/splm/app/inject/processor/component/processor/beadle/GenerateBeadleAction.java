package me.splm.app.inject.processor.component.processor.beadle;


import me.splm.app.inject.annotation.WeInjectBeadle;
import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.core.Config;
import me.splm.app.inject.processor.component.TargetClassComponent;
import me.splm.app.inject.processor.component.proxy.AbsGenerateJavaAction;
import me.splm.app.inject.processor.component.proxy.ActionTask;
import me.splm.app.inject.processor.component.proxy.TreeBranchesMethods;
import me.splm.app.inject.processor.exception.NotExtendException;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;


public class GenerateBeadleAction extends AbsGenerateJavaAction {

    private static final String PKG_BACKGROUNDEXECTOR="BackgroundExecutor";

    @Override
    protected String writeSuffix() {
        return Config.SUFFIX_BEADLE;
    }

    @Override
    public ActionTask prepareAction(TreeTrunk treeTrunk) throws NullPointerException {
        String cName=treeTrunk.getName();
        String absName=treeTrunk.getAbstractName();
        String pName=treeTrunk.getPackageName();
        ClassName subClazzName=ClassName.get(pName,cName);
        String clazzSimpleName="We" + cName + writeSuffix();
        LOGGER.info(clazzSimpleName+"99999999999999999999999");
        ClassName clazzName = ClassName.get(TARGET_PACKAGE_NAME, clazzSimpleName);
        Set<TreeBranchesMethods> treeBranchesMethodsSet=treeTrunk.fetcheMemberOfMethods();

        List<MethodSpec> methodSpecs=new ArrayList<>();
        for(TreeBranchesMethods treeBranchesMethods:treeBranchesMethodsSet){
            List<ParameterSpec> parameterSpecs=new ArrayList<>();
            StringBuilder paramBuilder=new StringBuilder();
            String methodName=treeBranchesMethods.getName();
            TypeMirror returnTypeMirror=treeBranchesMethods.getReturnType();
            TypeName typeNameReturn=ClassName.get(returnTypeMirror);
            int paramSize=treeBranchesMethods.getParameters().size();
            int index=0;
            while(index<paramSize){
                TypeName t=ClassName.get(treeBranchesMethods.getParameters().get(index).asType());
                String paramName=treeBranchesMethods.getParameters().get(index).toString();
                ParameterSpec spec=ParameterSpec.builder(t,paramName).addModifiers(Modifier.FINAL).build();
                paramBuilder.append(paramName);
                if(index!=paramSize-1){
                    paramBuilder.append(",");
                }
                parameterSpecs.add(spec);
                index++;
            }
            WeInjectBeadle beadle=treeBranchesMethods.getExecutableElement().getAnnotation(WeInjectBeadle.class);
            String taskID=beadle.taskID();
            String serial=beadle.serial();
            long delay=beadle.delay();
            CodeBlock codeBlock=CodeBlock.builder().add(PKG_BACKGROUNDEXECTOR+".execute(new "+PKG_BACKGROUNDEXECTOR+".Task(\""+taskID+"\",\""+serial+"\","+delay+"){\n" +
                    "@Override\n"+
                    "public void execute() {\n" +
                    "   "+clazzSimpleName+".super."+methodName+"("+paramBuilder.toString()+");\n" +
                    "   }\n" +
                    "});\n").build();
            MethodSpec methodSpec=MethodSpec.methodBuilder(methodName)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameters(parameterSpecs)
                    .addCode(codeBlock)
                    .returns(typeNameReturn)
                    .build();
            methodSpecs.add(methodSpec);
        }
        TargetClassComponent component=new TargetClassComponent.TargetClassBuilder()
                .addWriteName("We" + cName + writeSuffix())
                .addClassNameOfSingleton(clazzName)
                .addAbsNameOfTarget(absName)
                .build();
        try{
            mTargetClzz=getDefTargetClzzSpec(component).toBuilder()
                    .superclass(subClazzName)
                    .addMethods(methodSpecs)
                    .addModifiers(Modifier.PUBLIC)
                    .build();
        }catch(NotExtendException e){
            LOGGER.error(e);
        }
        return new ActionTask(this);
    }
}
