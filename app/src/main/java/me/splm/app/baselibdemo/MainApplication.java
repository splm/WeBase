package me.splm.app.baselibdemo;

import android.app.Application;
import android.util.Log;

import com.jc.android.baselib.manager.ActivityCoreManager;
import com.jc.android.baselib.manager.ConfigFilesManager;
import com.jc.android.baselib.manager.FragmentCoreManager;
import com.jc.android.baselib.manager.GlobalManager;
import com.jc.android.baselib.manager.SharePreferenceConfig;
import com.jc.android.baselib.manager.UILayerManager;

import me.splm.app.inject.annotation.WeInjectInit;

@WeInjectInit
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        testManager();
    }

    private void testManager(){
        UILayerManager uiLayerManager=new UILayerManager();
        uiLayerManager.registManager(new FragmentCoreManager());//TODO Caution:Some problems will appear here.
        uiLayerManager.registManager(new ActivityCoreManager());

        ConfigFilesManager localFilesManager=new ConfigFilesManager();
        SharePreferenceConfig sharePreferenceConfig= new SharePreferenceConfig.Builder()
                .init(this,"cache")
                .join("key_1",1000)
                .join("key_2",2000)
                .build();
        /*SharePreferenceConfig sharePreferenceConfig2= new SharePreferenceConfig.Builder()
                .init(this,"cache_2")
                .join("key_2_1",3000)
                .join("key_2_2",4000)
                .build();*/
        localFilesManager.registConfig(sharePreferenceConfig);

        Log.e("********", "testManager: "+localFilesManager.showAllManagerCount());

        GlobalManager.getInstance().registManager(localFilesManager);
        GlobalManager.getInstance().registManager(uiLayerManager);

        GlobalManager.getInstance().logAllManagerTags();//读取所有配置管理器
        Log.e("******", "testManager: The Initial procedure has been Successful.");
    }
}
