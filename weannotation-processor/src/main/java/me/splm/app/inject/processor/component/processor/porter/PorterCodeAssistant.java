package me.splm.app.inject.processor.component.processor.porter;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import me.splm.app.inject.annotation.Whether;


public class PorterCodeAssistant {

    private PorterFieldModelProxy mProxy;
    private PorterFieldModel mActivityModel;
    private PorterFieldModel mDataBindingModel;
    private PorterFieldModel mDataBindingUtilsModel;
    private PorterFieldModel mObjectModel;
    private PorterFieldModel mViewModel;
    private PorterFieldModel mValueOfLayoutIdModel;
    private PorterFieldModel mValueOfAbsNameModel;

    private String mActivityName;
    private String mDataBindingName;
    private ClassName mDataBindingUtilsClzName;
    private ClassName mViewModelClzName;
    private String mViewModelName;
    private String mObjectName;

    private int mlayoutId;
    private String mAbsName;


    public PorterCodeAssistant(PorterFieldModelProxy proxy) {
        this.mProxy=proxy;
        init();
    }

    private void init(){
        mActivityModel=mProxy.getFieldOfActivity();
        mDataBindingModel=mProxy.getFieldOfDataBinding();
        mDataBindingUtilsModel=mProxy.getFieldOfDataBindingUtils();
        mObjectModel=mProxy.getFieldOfObject();
        mViewModel=mProxy.getFieldOfViewModel();
        mValueOfLayoutIdModel=mProxy.getValueOfLayoutIdValueModel();
        mValueOfAbsNameModel=mProxy.getValueOfAbsNameModel();

        mActivityName= mActivityModel.getName();
        mDataBindingName=mDataBindingModel.getName();
        mDataBindingUtilsClzName=mDataBindingUtilsModel.getClassName();
        mObjectName=mObjectModel.getName();
        mViewModelClzName=mViewModel.getClassName();
        mViewModelName=mViewModel.getName();

        mlayoutId=(Integer) mValueOfLayoutIdModel.getValue();
        mAbsName=(String) mValueOfAbsNameModel.getValue();
    }

    private CodeBlock  buildFullScreenFraction(Whether isFullScreen, Whether isShowTile){

        CodeBlock.Builder fullScreenBuilder = CodeBlock.builder();
        if (isFullScreen == Whether.YES) {
            fullScreenBuilder.add(mActivityName + ".getWindow().setFlags(" + 0x00000400 + "," + 0x00000400 + ");\n");
            fullScreenBuilder.add(mActivityName + ".getSupportActionBar().hide();\n");
        } else {
            if (isShowTile == Whether.YES) {
                fullScreenBuilder.add(mActivityName + ".getSupportActionBar().hide();\n");
            }
        }
        return fullScreenBuilder.build();
    }

    public CodeBlock buildMethodInitViewPortion(Whether isFullScreen, Whether isShowTile){
        CodeBlock codeOfInitView = CodeBlock.builder()
                .add(buildFullScreenFraction(isFullScreen,isShowTile))
                .add(mActivityName + "=("+mAbsName+")"+mObjectName+";\n")
                .addStatement(mDataBindingName + "=$T.setContentView(" + mActivityName + ",$L)", mDataBindingUtilsClzName, mlayoutId)
                .addStatement(mActivityName + ".setBinding(" + mDataBindingName + ")")
                .addStatement(mViewModelName + "=new $T(" + mActivityName + ")", mViewModelClzName)
                .addStatement(mDataBindingName + ".setViewModel(" + mViewModelName + ")")
                .addStatement(mActivityName + ".setViewModel(" + mViewModelName + ")").build();
        return codeOfInitView;
    }
}
