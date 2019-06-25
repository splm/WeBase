package me.splm.app.core.component.manager;

public class InformationDesk implements IWorkshop {
    @Override
    public ConfigFilesManager catchManagerOfConfigFile() {
        return GlobalManagerManual.findConfigFilesManager.searchManager();
    }

    @Override
    public ActivityCoreManager catchManagerOfActivity() {
        return GlobalManagerManual.findActivityCoreManager.searchManager();
    }

    public UILayerManager catchManagerOfUI() {
        return GlobalManagerManual.findUIlayerManager.searchManager();
    }

    @Override
    public RequestRemoteManager catchManagerOfRequestRemote() {
        return GlobalManagerManual.findRequestRemoteManager.searchManager();
    }

    public IManagerMarker catchManagerOfCustom(Class<? extends IManagerMarker> clazz) {
        return GlobalManagerManual.findCustomManager.searchManager(clazz);
    }

    @Override
    public FragmentCoreManager catchManagerOfFragment() {
        return GlobalManagerManual.findFragmentCoreManager.searchManager();
    }
}
