package me.splm.app.inject.processor.component.processor.loadingbridge;

import me.splm.app.inject.processor.component.proxy.AbsGenerateJavaAction;
import me.splm.app.inject.processor.component.proxy.ActionTask;
import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.core.Config;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Modifier;

public class GenerateLoadingBridgeAction extends AbsGenerateJavaAction {

    @Override
    protected String writeSuffix() {
        return Config.SUFFIX_RECORD;
    }

    @Override
    public ActionTask prepareAction(TreeTrunk treeTrunk) {
        ArrayTypeName objArrayTy = ArrayTypeName.of(String.class);
        List<? extends AnnotationMirror> set= treeTrunk.fetchMemberOfAnnotations();
        String[] array=new String[set.size()];
        int i=0;
        for(AnnotationMirror t:set){
            array[i]=t.getAnnotationType().toString();
            i++;
        }
        String literal = "\"" + String.join("\",\"",array) + "\"";
        CodeBlock block = CodeBlock.builder().add("{"+literal+"}").build();
        FieldSpec mObjectArray = FieldSpec.builder(objArrayTy, "VALUES", Modifier.PUBLIC,Modifier.STATIC)
                .initializer(block)
                .build();
        mTargetClzz=getTargetClzzSpec("We"+treeTrunk.getName()+writeSuffix()).toBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addField(mObjectArray)
                .build();
        return new ActionTask(this);
    }

}
