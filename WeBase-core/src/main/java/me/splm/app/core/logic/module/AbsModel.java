package me.splm.app.core.logic.module;


import me.splm.app.core.component.tool.UtilsFactory;

public abstract class AbsModel implements IModel {

    @Override
    public UtilsFactory getUtilsFactory() {
        return UtilsFactory.getInstance();
    }
}
