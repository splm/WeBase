package me.splm.app.core.component.manager;


public interface IWorkshop {
    ActivityCoreManager catchManagerOfActivity();

    FragmentCoreManager catchManagerOfFragment();

    RequestRemoteManager catchManagerOfRequestRemote();

    ConfigFilesManager catchManagerOfConfigFile();
}
