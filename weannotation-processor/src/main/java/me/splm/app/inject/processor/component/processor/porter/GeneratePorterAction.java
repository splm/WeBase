package me.splm.app.inject.processor.component.processor.porter;

import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.TypeElement;

import me.splm.app.inject.annotation.WeInjectPorter;
import me.splm.app.inject.annotation.Whether;
import me.splm.app.inject.processor.code.WeClass;
import me.splm.app.inject.processor.code.WeCodeModel;
import me.splm.app.inject.processor.code.WeMethod;
import me.splm.app.inject.processor.code.WeMod;
import me.splm.app.inject.processor.code.WeVar;
import me.splm.app.inject.processor.component.elder.NamePair;
import me.splm.app.inject.processor.component.proxy.AbsGenerateJavaAction;
import me.splm.app.inject.processor.component.proxy.ActionTask;
import me.splm.app.inject.processor.component.proxy.IWorkersProxy;
import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.core.Config;
import me.splm.app.inject.processor.exception.NotDuplicateException;


public class GeneratePorterAction extends AbsGenerateJavaAction {
    private TypeElement mTypeElement;
    private static final String SUFFIX = Config.SUFFIX_PORTER;
    private static final String MDATABINGNAME = "mDatabinding";
    private static final String MVIEWMODELNAME = "mViewModel";
    private static final String MACTIVITYNAME = "mActivity";

    @Override
    protected String writeSuffix() {
        return SUFFIX;
    }

    @Override
    public ActionTask prepareAction(TreeTrunk treeTrunk) {
        mTypeElement = treeTrunk.getTypeElement();
        String cName = mTypeElement.getSimpleName().toString();
        String pName = treeTrunk.getPackageName();
        String absName = treeTrunk.getAbstractName();
        String subName = mTypeElement.getSuperclass().toString();
        String[] paramTypes = splitParaType(subName);
        NamePair p1st = splitTargetStr2(paramTypes[0]);//Config.APP_PACKAGE + ".databinding." +
        NamePair p2nd = splitTargetStr2(paramTypes[1]);
        WeInjectPorter porter = mTypeElement.getAnnotation(WeInjectPorter.class);
        int id = porter.layoutId();
        Whether isFullScreen = porter.fullScreen();
        Whether isShowTile = porter.noTitle();
        /**********/
        try{
            WeCodeModel weCodeModel =renewCodeModel();
//            WeClass weClass = weCodeModel.createClass("We" + cName + writeSuffix());
            WeClass weClass=weCodeModel.createClass(WeMod.PUBLIC,pName,"We" + cName + writeSuffix());
            WeVar mDatabinding=weClass.declareVar(WeMod.PUBLIC, p1st.getPackageName(), p1st.getSimpleName(), MDATABINGNAME);//mDatabinding
            WeVar mActivity=weClass.declareVar(WeMod.PUBLIC, pName, cName, MACTIVITYNAME);
            WeVar mViewModel = weClass.declareVar(WeMod.PUBLIC, p2nd.getPackageName(), p2nd.getSimpleName(), MVIEWMODELNAME);
            WeVar mDataBindingUtils=new WeVar("android.databinding", "DataBindingUtil","");
            weClass.canBeSingleton();
            weClass.addInterface(IWorkersProxy.class);
            WeMethod initView = weClass.declareMethod(WeMod.PUBLIC, "initView");
            WeVar object = new WeVar("java.lang", "Object", "object");
            initView.addParameters(object);
            WeVar layoutIdOfIllusion=new WeVar(id);
            WeVar absNameOfIllusion=new WeVar(absName);
            PorterFieldModelProxy porterFieldModelProxy=new PorterFieldModelProxy();
            porterFieldModelProxy.setFieldOfActivity(new PorterFieldModel(mActivity));
            porterFieldModelProxy.setFieldOfDataBinding(new PorterFieldModel(mDatabinding));
            porterFieldModelProxy.setFieldOfDataBindingUtils(new PorterFieldModel(mDataBindingUtils));
            porterFieldModelProxy.setFieldOfObject(new PorterFieldModel(object));
            porterFieldModelProxy.setFieldOfViewModel(new PorterFieldModel(mViewModel));
            porterFieldModelProxy.setValueOfLayoutIdValueModel(new PorterFieldModel(layoutIdOfIllusion));
            porterFieldModelProxy.setValueOfAbsNameModel(new PorterFieldModel(absNameOfIllusion));
            PorterCodeAssistant assistant=new PorterCodeAssistant(porterFieldModelProxy);
            CodeBlock codeOfInitView=assistant.buildMethodInitViewPortion(isFullScreen,isShowTile);
            initView.addBody(codeOfInitView);

            WeMethod assist = weClass.declareMethod(WeMod.PUBLIC, "assist");
            assist.addParameters(object);
            assist.addAnnotation(Override.class);
            assist.reference(initView, object);//TODO uncomplete
            mTargetClzz = weCodeModel.build();
            return super.prepareAction(treeTrunk);
        }catch(NotDuplicateException e){
            LOGGER.error(e);
        }
        return null;
    }

    private String[] splitParaType(String target) {
        int start = target.indexOf("<");
        int end = target.length();
        String str = target.substring(start + 1, end - 1);
        return str.split(",");
    }

    private NamePair splitTargetStr2(String absName) {
        int index = absName.lastIndexOf(".");
        String p = absName.substring(0, index);
        String s = absName.substring(index + 1);
        return new NamePair(p, s);
    }
}
