package com.jc.android.baselib.manager;

public class InformationDesk implements IWorkshop {
    @Override
    public LocalFilesManager catchManagerOfLocalFile() {
        return null;
    }

    public ActivityCoreManager catchManagerOfActivity(){
        return (ActivityCoreManager) GlobalManagerManual.findActivityCoreManager.searchManager();
    }

    public UILayerManager catchManagerOfUI(){
        return (UILayerManager) GlobalManagerManual.findUIlayerManager.searchManager();
    }

    public IManagerMarker catchManagerOfCustom(Class<? extends IManagerMarker> clazz){
        return GlobalManagerManual.findCustomManager.searchManager(clazz);
    }

    @Override
    public FragmentCoreManager catchManagerOfFragment() {
        return null;
    }
}
