package com.jc.android.baselib.manager;

import android.util.Log;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class SharePreferenceConfig implements IManagerMarker {
    public SharePreferenceConfig(String...names) {
        for(String name : names){
            Log.e("*********", "SharePreferenceConfig: "+name);
        }
    }
}
