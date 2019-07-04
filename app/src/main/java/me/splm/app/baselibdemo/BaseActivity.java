package me.splm.app.baselibdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.splm.app.core.component.manager.ActivityCoreManager;
import me.splm.app.core.component.manager.IWorkShop;
import me.splm.app.core.component.manager.InformationDesk;
import me.splm.app.inject.processor.component.proxy.WeWorkersProxy;


public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeWorkersProxy.bind(this);
        IWorkShop workshop=new InformationDesk();
        ActivityCoreManager activityCoreManager=workshop.catchManagerOfActivity();
        activityCoreManager.addActivity(this);
    }
}
