package me.splm.app.inject.processor.component.processor.porter;

import com.squareup.javapoet.CodeBlock;

import java.util.List;

import me.splm.app.inject.annotation.Whether;
import me.splm.app.inject.processor.code.WeVar;
import me.splm.app.inject.processor.component.Utils.TextUtils;
import me.splm.app.inject.processor.component.proxy.FieldModelProxy;
import me.splm.app.inject.processor.core.Config;
import me.splm.app.inject.processor.log.Logger;
import me.splm.app.inject.processor.log.LoggerFactory;


public class PorterCodeAssistant {

    private FieldModelProxy mtProxy;
    private String mActivityName;

    private int mlayoutId;
    private String mAbsName;
    private String mObjName;

    public static final String GLOBAL_ACT = Config.Varial_GLOBAL + "mActivity";
    public static final String GLOBAL_LAYOUT_ID = Config.Varial_GLOBAL + "layoutID";
    public static final String SECTION_OBJECT = Config.Varial_SECTION + "object";

    public StringBuilder mCodeOfOtherView = new StringBuilder();

    protected static final Logger LOGGER = LoggerFactory.getLogger(PorterCodeAssistant.class);

    public PorterCodeAssistant(FieldModelProxy proxy) {
        this.mtProxy = proxy;
        init();
    }

    private void init() {

        PorterFieldModel mActivityModel = getSinglePorterFieldModel(GLOBAL_ACT);
        PorterFieldModel mLayoutIDModel = getSinglePorterFieldModel(GLOBAL_LAYOUT_ID);
        PorterFieldModel mObjModel = getSinglePorterFieldModel(SECTION_OBJECT);
        mActivityName = mActivityModel.getName();
        mAbsName = mActivityModel.getClassName().toString();
        mlayoutId = (int) mLayoutIDModel.getValue();
        mObjName = mObjModel.getName();
    }

    private PorterFieldModel getSinglePorterFieldModel(String tag) {
        List<PorterFieldModel> list = mtProxy.getModels(tag);
        return list.get(0);
    }

    public CodeBlock buildInitComponFraction() {
        CodeBlock.Builder initComponBuilder = CodeBlock.builder();
        List<PorterFieldModel> models = mtProxy.getModels(WeVar.GROUP);
        for (PorterFieldModel model : models) {
            int viewID = (int) (model.getValue());
            String parentView = model.getExtraForString1();
            if (!TextUtils.isEmpty(parentView)) {
                mCodeOfOtherView.append(mActivityName + "." + model.getName() + "=" + mActivityName + "." + parentView + ".findViewById(" + viewID + ");\n");
                continue;
            }
            initComponBuilder.add(mActivityName + "." + model.getName() + "=" + mActivityName + ".findViewById(" + viewID + ");\n");
        }
        return initComponBuilder.build();
    }

    public CodeBlock buildInitOtherViewFraction() {
        CodeBlock.Builder buildInitOtherViewBuilder = CodeBlock.builder();
        buildInitOtherViewBuilder.add(mCodeOfOtherView.toString());
        return buildInitOtherViewBuilder.build();
    }

    private CodeBlock buildFullScreenFraction(Whether isFullScreen, Whether isShowTile) {

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

    public CodeBlock buildMethodInitViewPortion(Whether isFullScreen, Whether isShowTile) {
        CodeBlock codeOfInitView = CodeBlock.builder()
                .add(buildFullScreenFraction(isFullScreen, isShowTile)).add(mActivityName + "=(" + mAbsName + ")" + mObjName + ";\n")
                .addStatement(mActivityName + ".setContentView($L);", mlayoutId)
                .add(buildInitComponFraction())
                .build();
        return codeOfInitView;
    }
}
