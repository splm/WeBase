package com.jc.android.baselib.manager;


public interface IWorkshop {
    ActivityCoreManager catchManagerOfActivity();
    FragmentCoreManager catchManagerOfFragment();

    LocalFilesManager catchManagerOfLocalFile();
}
