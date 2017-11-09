package com.jc.android.baselib.manager;

public class InformationDesk implements IWorkshop {
    public ActivityCoreManager catchManagerOfActivity(){
        ActivityCoreManager activityCoreManager=(ActivityCoreManager) GlobalManagerManual.findActivityCoreManager.searchManager();
        return activityCoreManager;
    }

    @Override
    public LoaderObject enrollPermission(ILoader loader) {
        return loader.read(this);
    }

    @Override
    public FragmentCoreManager catchManagerOfFragment() {
        return null;
    }
}
