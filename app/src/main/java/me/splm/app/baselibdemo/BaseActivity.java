package me.splm.app.baselibdemo;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.android.baselib.manager.ActivityCoreManager;
import com.jc.android.baselib.manager.GlobalManagerManual;
import me.splm.app.inject.processor.component.proxy.WeWorkersProxy;


public abstract class BaseActivity<B extends ViewDataBinding,V> extends AppCompatActivity {
    private B mBinding;
    private V mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeWorkersProxy.bind(this);
        ActivityCoreManager activityCoreManager=(ActivityCoreManager) GlobalManagerManual.findActivityCoreManager.searchManager();
        activityCoreManager.addActivity(this);
    }

    public V getViewModel() {
        return mViewModel;
    }

    public void setViewModel(V mViewModel) {
        this.mViewModel = mViewModel;
    }

    public B getBinding() {
        return mBinding;
    }

    public void setBinding(B mBinding) {
        this.mBinding = mBinding;
    }
}
