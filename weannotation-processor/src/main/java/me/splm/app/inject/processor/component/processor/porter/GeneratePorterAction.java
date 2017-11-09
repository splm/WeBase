package me.splm.app.inject.processor.component.processor.porter;

import me.splm.app.inject.annotation.WeInjectPorter;
import me.splm.app.inject.annotation.Wheather;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.TypeElement;

import me.splm.app.inject.processor.code.WeClass;
import me.splm.app.inject.processor.code.WeCodeModel;
import me.splm.app.inject.processor.code.WeMod;
import me.splm.app.inject.processor.component.proxy.ActionTask;
import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.core.Config;
import me.splm.app.inject.processor.code.WeMethod;
import me.splm.app.inject.processor.code.WeVar;
import me.splm.app.inject.processor.component.elder.NamePair;
import me.splm.app.inject.processor.component.proxy.AbsGenerateJavaAction;
import me.splm.app.inject.processor.component.proxy.IWorkersProxy;

import static com.squareup.javapoet.ClassName.get;


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
        NamePair p1st = splitTargetStr2(Config.APP_PACKAGE + ".databinding." + paramTypes[0]);
        NamePair p2nd = splitTargetStr2(paramTypes[1]);
        ClassName clazzName = get(TARGET_PACKAGE_NAME, "We" + cName + writeSuffix());
        ClassName databindingUtil = get("android.databinding", "DataBindingUtil");
        WeInjectPorter porter = mTypeElement.getAnnotation(WeInjectPorter.class);
        int id = porter.layoutId();
        Wheather isFullScreen = porter.fullScreen();
        Wheather isShowTile = porter.noTitle();
        CodeBlock.Builder fullScreenBuilder = CodeBlock.builder();
        if (isFullScreen == Wheather.YES) {
            fullScreenBuilder.add(MACTIVITYNAME + ".getWindow().setFlags(" + 0x00000400 + "," + 0x00000400 + ");\n");
            fullScreenBuilder.add(MACTIVITYNAME + ".getSupportActionBar().hide();\n");
        } else {
            if (isShowTile == Wheather.YES) {
                fullScreenBuilder.add(MACTIVITYNAME + ".getSupportActionBar().hide();\n");
            }
        }
        CodeBlock fullScreen = fullScreenBuilder.build();
        /**********/
        WeVar className = new WeVar(WeMod.PRIVATE+WeMod.STATIC,TARGET_PACKAGE_NAME,"We" + cName + writeSuffix(),"instance");
        WeCodeModel weCodeModel = new WeCodeModel();
        WeClass weClass = weCodeModel.createClass("We" + cName + writeSuffix());
        weClass.declareVar(WeMod.PUBLIC, p1st.getPackageName(), p1st.getSimpleName(), MDATABINGNAME);//mDatabinding
        weClass.declareVar(WeMod.PUBLIC, pName, cName, MACTIVITYNAME);
        weClass.addInterface(IWorkersProxy.class);
        weClass.canBeSingleton(className);
        /*WeVar weVar=new WeVar(WeMod.PRIVIATE,pName, cName,MACTIVITYNAME);
        weClass.declareVar(weVar);*/
        WeVar mViewModel = weClass.declareVar(WeMod.PUBLIC, p2nd.getPackageName(), p2nd.getSimpleName(), MVIEWMODELNAME);
        WeVar object = new WeVar("java.lang", "Object", "object");

        WeMethod initView = weClass.declareMethod(WeMod.PUBLIC, "initView");
        initView.addParameters(object);
        CodeBlock codeOfInitView = CodeBlock.builder()
                .add(fullScreen)
                .add(MACTIVITYNAME + "=("+absName+")"+object.getFieldName()+";\n")
                .addStatement(MDATABINGNAME + "=$T.setContentView(" + MACTIVITYNAME + ",$L)", databindingUtil, id)
                .addStatement(MACTIVITYNAME + ".setBinding(" + MDATABINGNAME + ")")
                .addStatement(MVIEWMODELNAME + "=new $T(" + MACTIVITYNAME + ")", mViewModel.toClassType())
                .addStatement(MDATABINGNAME + ".setViewModel(" + MVIEWMODELNAME + ")")
                .addStatement(MACTIVITYNAME + ".setViewModel(" + MVIEWMODELNAME + ")").build();
        initView.addBody(codeOfInitView);

        WeMethod assist = weClass.declareMethod(WeMod.PUBLIC, "assist");
        assist.addParameters(object);
        assist.addAnnotation(Override.class);
        assist.reference(initView, object);//TODO uncomplete

        mTargetClzz = weCodeModel.build();

        /************/

        return new ActionTask(this);
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
