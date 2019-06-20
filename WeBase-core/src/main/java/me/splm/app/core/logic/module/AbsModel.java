package me.splm.app.core.logic.module;


import android.support.annotation.NonNull;

import com.jc.android.baselib.manager.ConfigFilesManager;
import com.jc.android.baselib.manager.IWorkshop;
import com.jc.android.baselib.manager.InformationDesk;
import com.jc.android.baselib.manager.SharePreferenceConfig;

import me.splm.app.core.component.tool.UtilsFactory;

public abstract class AbsModel implements IModel {

    private SharePreferenceConfig mSharePreferenceConfig;

    public AbsModel() {
        IWorkshop workshop = new InformationDesk();
        ConfigFilesManager configFilesManager = workshop.catchManagerOfConfigFile();
        mSharePreferenceConfig = configFilesManager.getSharePreferenceConfig();
    }

    @Override
    public UtilsFactory getUtilsFactory() {
        return UtilsFactory.getInstance();
    }

    @Override
    public <E> void writeValueToSP(String key, E value) {
        mSharePreferenceConfig.putValue(key, value);
    }

    @Override
    public <E> E getValueFromSP(String key, @NonNull E defaultValue) {
        return mSharePreferenceConfig.getValue(key, defaultValue);
    }
}
