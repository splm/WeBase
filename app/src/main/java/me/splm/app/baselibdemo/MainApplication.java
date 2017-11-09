package me.splm.app.baselibdemo;

import android.app.Application;
import android.util.Log;

import me.splm.app.inject.annotation.WeInjectInit;
import com.jc.android.baselib.manager.ActivityCoreManager;
import com.jc.android.baselib.manager.FragmentCoreManager;
import com.jc.android.baselib.manager.GlobalManager;
import com.jc.android.baselib.manager.UILayerManager;

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

        GlobalManager.getInstance().registManager(uiLayerManager);
        GlobalManager.getInstance().logAllManagerTags();
        Log.e("******", "testManager: The Initial procedure has been Successful.");
    }
}
