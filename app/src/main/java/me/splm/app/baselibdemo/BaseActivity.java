package me.splm.app.baselibdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.android.baselib.manager.ActivityCoreManager;
import com.jc.android.baselib.manager.GlobalManagerManual;

import me.splm.app.inject.processor.component.proxy.WeWorkersProxy;


public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeWorkersProxy.bind(this);
        ActivityCoreManager activityCoreManager=GlobalManagerManual.findActivityCoreManager.searchManager();
        activityCoreManager.addActivity(this);
    }

}
