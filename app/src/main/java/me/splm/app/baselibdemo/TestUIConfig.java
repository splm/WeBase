package me.splm.app.baselibdemo;

import android.util.Log;

import com.jc.android.baselib.manager.ActivityCoreManager;
import com.jc.android.baselib.manager.Config;
import com.jc.android.baselib.manager.ILoader;
import com.jc.android.baselib.manager.IWorkshop;
import com.jc.android.baselib.manager.LoaderObject;


public class TestUIConfig implements ILoader {
    @Override
    public LoaderObject read(IWorkshop workshop) {
        Log.e("*************", "read: 1111111111111111111");
        //TODO Using workshop to get Mannager instance,then re-build a Iconfig Object.
        ActivityCoreManager manager=workshop.catchManagerOfActivity();
        LoaderObject lo=new LoaderObject(manager,new Config());
        return lo;
    }
}
