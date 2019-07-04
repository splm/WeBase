package me.splm.app.core.component.manager;


public interface IWorkShop {
    ActivityCoreManager catchManagerOfActivity();

    FragmentCoreManager catchManagerOfFragment();

    RequestRemoteManager catchManagerOfRequestRemote();

    ConfigFilesManager catchManagerOfConfigFile();
}
