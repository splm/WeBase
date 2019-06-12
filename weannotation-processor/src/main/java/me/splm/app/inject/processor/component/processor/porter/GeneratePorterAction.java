package me.splm.app.inject.processor.component.processor.porter;

import com.squareup.javapoet.CodeBlock;

import java.util.Iterator;
import java.util.Set;

import javax.lang.model.element.TypeElement;

import me.splm.app.inject.annotation.WeInjectPorter;
import me.splm.app.inject.annotation.Whether;
import me.splm.app.inject.processor.code.WeClass;
import me.splm.app.inject.processor.code.WeCodeModel;
import me.splm.app.inject.processor.code.WeMethod;
import me.splm.app.inject.processor.code.WeMod;
import me.splm.app.inject.processor.code.WeVar;
import me.splm.app.inject.processor.component.proxy.AbsGenerateJavaAction;
import me.splm.app.inject.processor.component.proxy.ActionTask;
import me.splm.app.inject.processor.component.proxy.FieldModelProxy;
import me.splm.app.inject.processor.component.proxy.IWorkersProxy;
import me.splm.app.inject.processor.component.proxy.TreeLeavesFields;
import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.core.Config;
import me.splm.app.inject.processor.exception.NotDuplicateException;


public class GeneratePorterAction extends AbsGenerateJavaAction {
    private TypeElement mTypeElement;
    private static final String SUFFIX = Config.SUFFIX_PORTER;
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
        WeInjectPorter porter = mTypeElement.getAnnotation(WeInjectPorter.class);
        int id = porter.value();//布局文件id
        Whether isFullScreen = porter.fullScreen();
        Whether isShowTile = porter.noTitle();
        try {
            WeCodeModel weCodeModel = renewCodeModel();
            WeClass weClass = weCodeModel.createClass(WeMod.PUBLIC, pName, "We" + cName + writeSuffix());
            WeVar mActivity = weClass.declareVar(WeMod.PUBLIC, pName, cName, MACTIVITYNAME);
            /**********/
            FieldModelProxy proxy = FieldModelProxy.getInstance();
            proxy.put(PorterCodeAssistant.GLOBAL_ACT, new PorterFieldModel(mActivity));
            Set<TreeLeavesFields> fields = treeTrunk.fetchMemberOffileds();
            Iterator<TreeLeavesFields> iterable = fields.iterator();
            while (iterable.hasNext()) {
                TreeLeavesFields treeLeavesFields = iterable.next();
                int viewID = treeLeavesFields.getVariableElement().getAnnotation(WeInjectPorter.class).viewID();
                String parentView=treeLeavesFields.getVariableElement().getAnnotation(WeInjectPorter.class).parentView();
                WeVar mView = new WeVar(WeMod.PRIVATE, treeLeavesFields.getPackageName(), treeLeavesFields.getSimpleName(), treeLeavesFields.getName());
                mView.mExtraForString1=parentView;
                mView.initPortableValue(viewID);
                proxy.put(WeVar.GROUP, new PorterFieldModel(mView));
            }
            /**********/
            weClass.canBeSingleton();
            weClass.addInterface(IWorkersProxy.class);
            WeMethod initView = weClass.declareMethod(WeMod.PUBLIC, "initView");
            WeVar object = new WeVar("java.lang", "Object", "object");
            proxy.put(PorterCodeAssistant.SECTION_OBJECT, new PorterFieldModel(object));
            initView.addParameters(object);
            //定义变量
            WeVar layoutIdOfIllusion = new WeVar(id);
            WeVar absNameOfIllusion = new WeVar(absName);
            proxy.put(PorterCodeAssistant.GLOBAL_LAYOUT_ID, new PorterFieldModel(layoutIdOfIllusion));
            proxy.put(PorterCodeAssistant.GLOBAL_ACT, new PorterFieldModel(absNameOfIllusion));
            //为变量赋值

            PorterCodeAssistant assistant = new PorterCodeAssistant(proxy);
            CodeBlock codeOfInitView = assistant.buildMethodInitViewPortion(isFullScreen, isShowTile);
            initView.addBody(codeOfInitView);

            WeMethod assist = weClass.declareMethod(WeMod.PUBLIC, "assist");
            assist.addParameters(object);
            assist.addAnnotation(Override.class);
            assist.reference(initView, object);//TODO uncomplete

            WeMethod initOtherView=weClass.declareMethod(WeMod.PUBLIC, "initOtherView");
            CodeBlock codeOfInitOtherView = assistant.buildInitOtherViewFraction();
            initOtherView.addBody(codeOfInitOtherView);

            mTargetClzz = weCodeModel.build();
            return super.prepareAction(treeTrunk);
        } catch (NotDuplicateException e) {
            LOGGER.error(e);
        }
        return null;
    }
}
