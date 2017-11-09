package com.jc.android.baselib.manager;


public interface IWorkshop {
    public ActivityCoreManager catchManagerOfActivity();
    public FragmentCoreManager catchManagerOfFragment();
    LoaderObject enrollPermission(ILoader loader);
}
