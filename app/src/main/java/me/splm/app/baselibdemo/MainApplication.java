package me.splm.app.baselibdemo;

import android.app.Application;
import android.util.Log;

import me.splm.app.core.component.manager.ActivityCoreManager;
import me.splm.app.core.component.manager.ConfigFilesManager;
import me.splm.app.core.component.manager.FragmentCoreManager;
import me.splm.app.core.component.manager.GlobalManager;
import me.splm.app.core.component.manager.HttpRequestConfig;
import me.splm.app.core.component.manager.SharePreferenceConfig;
import me.splm.app.core.component.manager.UILayerManager;
import me.splm.app.inject.annotation.WeInjectInit;
import me.splm.app.wenetjudger.processor.NetManager;

@WeInjectInit
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        testManager();
    }

    private void testManager(){

        NetManager.getDefault().init(this);

        UILayerManager uiLayerManager=new UILayerManager();
        uiLayerManager.registManager(new FragmentCoreManager());//TODO Caution:Some problems will appear here.
        uiLayerManager.registManager(new ActivityCoreManager());

        ConfigFilesManager localFilesManager=new ConfigFilesManager();
        SharePreferenceConfig sharePreferenceConfig= new SharePreferenceConfig.Builder()
                .init(this,"cache")
                .join("key_1",1000)
                .join("key_2",2000)
                .build();
        HttpRequestConfig httpRequestConfig=new HttpRequestConfig.Builder()
                .httpPort(80)
                .httpsPort(443)
                .hostUrl("http://www.app.splm.me/api")
                .isFixWhenException(true).build();
        localFilesManager.registConfig(sharePreferenceConfig);
        localFilesManager.registConfig(httpRequestConfig);

        GlobalManager.getInstance().registManager(localFilesManager);
        GlobalManager.getInstance().registManager(uiLayerManager);

        GlobalManager.getInstance().logAllManagerTags();//读取所有配置管理器
        Log.e("******", "testManager: The Initial procedure has been Successful.");
    }
}
