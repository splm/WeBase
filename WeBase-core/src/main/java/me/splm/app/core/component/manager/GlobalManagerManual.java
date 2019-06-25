package me.splm.app.core.component.manager;

import android.util.Log;

/**
 * You can use this class to get the Manager which you like.
 */
public enum GlobalManagerManual {

    /*****UIlayerManager******/
    findUIlayerManager() {
        @Override
        public UILayerManager searchManager() {
            return (UILayerManager) GlobalManagerManual.getManager().findManagerByTag(UILayerManager.class);
        }
    },
    findFragmentCoreManager() {
        @Override
        public FragmentCoreManager searchManager() {
            return (FragmentCoreManager) ((UILayerManager) (findUIlayerManager.searchManager())).findManagerByTag(FragmentCoreManager.class);
        }
    },
    findActivityCoreManager() {
        @Override
        public ActivityCoreManager searchManager() {
            Log.e("***********", "searchManager: findUIlayerManager.searchManager()"+findUIlayerManager.searchManager());
            return (ActivityCoreManager) ((UILayerManager) (findUIlayerManager.searchManager())).findManagerByTag(ActivityCoreManager.class);
        }
    },

    /**
     * Get your custom manager
     */
    findCustomManager() {
        @Override
        public <T extends IManagerMarker> T searchManager() {
            return null;
        }

        public <T extends IManagerMarker> T searchManager(Class<? extends IManagerMarker> clazz) {
            return (T) GlobalManagerManual.getManager().findManagerByTag(clazz);
        }
    },

    /*****ConfigFilesManager******/
    findConfigFilesManager() {
        @Override
        public ConfigFilesManager searchManager() {
            return (ConfigFilesManager) GlobalManagerManual.getManager().findManagerByTag(ConfigFilesManager.class);
        }
    },

    /***FindRequestManager***/
    findRequestRemoteManager() {
        @Override
        public RequestRemoteManager searchManager() {
            return (RequestRemoteManager) GlobalManagerManual.getManager().findManagerByTag(RequestRemoteManager.class);
        }
    };

    public abstract <T extends IManagerMarker> T searchManager();

    protected <T extends IManagerMarker> T searchManager(Class<? extends IManagerMarker> clazz) {
        return null;
    }

    private static GlobalManager getManager() {
        return GlobalManager.getInstance();
    }

    private static UILayerManager exchangeUIlayer() {
        return findUIlayerManager.searchManager();
    }

    private static ConfigFilesManager exchangeLocalFiles() {
        return findConfigFilesManager.searchManager();
    }

    private static RequestRemoteManager exchangeRequestRemote() {
        return findRequestRemoteManager.searchManager();
    }
}