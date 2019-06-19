package me.splm.app.core.logic.presenter;

import me.splm.app.core.component.tool.CommUtils;
import me.splm.app.core.component.tool.UtilsFactory;
import me.splm.app.core.logic.module.IModel;
import me.splm.app.core.logic.view.IBaseView;

public abstract class AbsBasePresenter<V extends IBaseView,M extends IModel> {
    public V mView;
    public M mModel;

    public AbsBasePresenter(V baseView) {
        this.mView=baseView;
    }
    public void setModel(M model){
        this.mModel=model;
    }
    public CommUtils getUtils(UtilsFactory.UtilsMarker marker){
        return mModel.getUtilsFactory().getCommUtils(marker);
    }
}
