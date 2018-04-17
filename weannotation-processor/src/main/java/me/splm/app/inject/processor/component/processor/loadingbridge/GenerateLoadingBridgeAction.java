package me.splm.app.inject.processor.component.processor.loadingbridge;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;

import me.splm.app.inject.processor.code.WeBlurVar;
import me.splm.app.inject.processor.code.WeClass;
import me.splm.app.inject.processor.code.WeCodeModel;
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
        /****/
        String pkg=treeTrunk.getPackageName();
        WeCodeModel weCodeModel =renewCodeModel();
        WeClass weClass=weCodeModel.createClass(pkg,"We"+treeTrunk.getName()+writeSuffix());
        WeVar weVar=LoadingBridgeAssistant.declareArray(String.class,"VALUES",array);
        WeVar weStringVar=LoadingBridgeAssistant.declareString("string_name","text");
        WeVar weIntVar=LoadingBridgeAssistant.declareNumber(WeBlurVar.INT,"int_name","256");
        WeVar weFloatVar=LoadingBridgeAssistant.declareNumber(WeBlurVar.FLOAT,"float_name","16.0f");
        WeVar weShortVar=LoadingBridgeAssistant.declareNumber(WeBlurVar.SHORT,"short_name","1");
        WeVar weLongVar=LoadingBridgeAssistant.declareNumber(WeBlurVar.LONG,"long_name","1024L");
        WeVar weDoubleVar=LoadingBridgeAssistant.declareNumber(WeBlurVar.DOUBLE,"double_name","1e16d");
        WeVar weBooleanVar=LoadingBridgeAssistant.declareNumber(WeBlurVar.BOOLEAN,"boolean_name","true");
        weClass.declareVar(weBooleanVar);
        weClass.declareVar(weDoubleVar);
        weClass.declareVar(weLongVar);
        weClass.declareVar(weShortVar);
        weClass.declareVar(weFloatVar);
        weClass.declareVar(weIntVar);
        weClass.declareVar(weStringVar);
        weClass.declareVar(weVar);
        mTargetClzz = weCodeModel.build();
        /****/
        return new ActionTask(this);
    }
}
