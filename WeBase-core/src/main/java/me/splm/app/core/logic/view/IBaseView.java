package me.splm.app.core.logic.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public interface IBaseView extends View.OnClickListener {
    Context getCurrentContext();
    void onUIError(String message);
    void onUISuccess(String message);
    void onUIReflect(String message);
    void onNormalTip(String message);
    Intent getTransferData();
    String getStringFromIntent(String key);
    Bundle makeShareEle(View view, String key);
    void jump(Class<? extends BaseActivity> clzz, Bundle bundle);
    void jump(Class<? extends BaseActivity> clzz, Bundle data, Bundle options);
    void jump(Class<? extends BaseActivity> clzz, Bundle bundle,int requestCode);
    void jump(Class<? extends BaseActivity> clzz, Bundle bundle,int requestCode,Bundle options);
}
