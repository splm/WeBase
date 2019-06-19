package me.splm.app.core.logic.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.splm.app.core.component.ui.ToastUtils;
import me.splm.app.core.logic.presenter.IBasePresenter;
import me.splm.app.inject.processor.component.proxy.WeWorkersProxy;
import me.splm.app.wepermission.WePermissionCallback;
import me.splm.app.wepermission.WePermissions;


public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity implements IBaseView, WePermissionCallback {

    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        WeWorkersProxy.bind(this);
    }

    public void setPresenter(P presenter){
        this.mPresenter=presenter;
    }

    @Override
    public Context getCurrentContext() {
        return this.getApplicationContext();
    }

    @Override
    public void onUIReflect(String message) {
        //ToastUtils.showSN(this, message);
    }

    @Override
    public void onUISuccess(String message) {
        onNormalTip(message);
    }

    @Override
    public void onUIError(String message) {
        onNormalTip(message);
    }

    @Override
    public void onNormalTip(String message) {
        ToastUtils.show(this, message);
    }

    @Override
    public Intent getTransferData() {
        return this.getIntent();
    }

    @Override
    public String getStringFromIntent(String key) {
        return getTransferData().getStringExtra(key);
    }

    @Override
    public Bundle makeShareEle(View view, String key) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, new Pair<>(view, key));
        return optionsCompat.toBundle();
    }

    @Override
    public void jump(Class<? extends BaseActivity> clzz,Bundle bundle) {
        jump(clzz,bundle,null );
    }

    @Override
    public void jump(Class<? extends BaseActivity> clzz,Bundle data, Bundle options) {
        Intent intent = new Intent(this, clzz);
        intent.putExtra("data", data);
        ActivityCompat.startActivity(this,intent,options);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WePermissions.onRequestPermissionResult(this, requestCode, grantResults,permissions);
    }

    @Override
    public void onRequestPermissionGranted(int requestCode, @NonNull String[] permissions,boolean isAllGranted) {

    }

    @Override
    public void onRequestPermissionDennied(int requestCode, @NonNull String[] permissions) {

    }
}
