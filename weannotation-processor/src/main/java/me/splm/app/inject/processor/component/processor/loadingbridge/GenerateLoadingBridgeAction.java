package me.splm.app.inject.processor.component.processor.loadingbridge;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;

import me.splm.app.inject.processor.code.WeClass;
import me.splm.app.inject.processor.code.WeCodeModel;
import me.splm.app.inject.processor.code.WeMod;
import me.splm.app.inject.processor.code.WeVar;
import me.splm.app.inject.processor.component.proxy.AbsGenerateJavaAction;
import me.splm.app.inject.processor.component.proxy.ActionTask;
import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.core.Config;

public class GenerateLoadingBridgeAction extends AbsGenerateJavaAction {

    @Override
    protected String writeSuffix() {
        return Config.SUFFIX_RECORD;
    }

    @Override
    public ActionTask prepareAction(TreeTrunk treeTrunk) {
        List<? extends AnnotationMirror> set= treeTrunk.fetchMemberOfAnnotations();
        String[] array=new String[set.size()];
        int i=0;
        for(AnnotationMirror t:set){
            array[i]=t.getAnnotationType().toString();
            i++;
        }
        String pkg=treeTrunk.getPackageName();
        WeCodeModel weCodeModel =renewCodeModel();
        WeClass weClass=weCodeModel.createClass(pkg,"We"+treeTrunk.getName()+writeSuffix());
        WeVar weVar=LoadingBridgeAssistant.declareArray(WeMod.PUBLIC+WeMod.FINAL+WeMod.STATIC,String.class,"VALUES",array);
        weClass.declareVar(weVar);
        mTargetClzz = weCodeModel.build();
        return super.prepareAction(treeTrunk);
    }
}
