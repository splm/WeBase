package com.jc.android.baselib.manager;



public class UILayerManager extends AbsManager implements IManagerMarker {
    @Override
    public void registManager(IManagerMarker manager) {
        if(manager instanceof FragmentCoreManager){
            super.put(manager);
        }else{
            super.registManager(manager);
        }
    }
}
