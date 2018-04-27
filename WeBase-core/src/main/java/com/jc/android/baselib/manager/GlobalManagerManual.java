package com.jc.android.baselib.manager;

/**
 * You can use this class to get the Manager which you like.
 */
public enum GlobalManagerManual {

    /*****UIlayerManager******/
    findUIlayerManager(){
        @Override
        public UILayerManager searchManager() {
            return (UILayerManager) GlobalManagerManual.getManager().findManagerByTag(UILayerManager.class);
        }
    },
    findFragmentCoreManager(){
        @Override
        public FragmentCoreManager searchManager() {
            return (FragmentCoreManager) GlobalManagerManual.exchangeUIlayer().findManagerByTag(FragmentCoreManager.class);
        }
    },
    findActivityCoreManager(){
        @Override
        public ActivityCoreManager searchManager() {
            return (ActivityCoreManager)GlobalManagerManual.exchangeUIlayer().findManagerByTag(ActivityCoreManager.class);
        }
    },

    /**Get your custom manager*/
    findCustomManager(){
        @Override
        public IManagerMarker searchManager() {
            return null;
        }

        @Override
        public IManagerMarker searchManager(Class<? extends IManagerMarker> clazz) {
            return GlobalManagerManual.exchangeUIlayer().findManagerByTag(clazz);
        }
    },

    /*****LocalFilesManager******/
    findLocalFilesManager(){
        @Override
        public IManagerMarker searchManager() {
            return GlobalManagerManual.getManager().findManagerByTag(LocalFilesManager.class);
        }
    },
    ;
    public abstract IManagerMarker searchManager();
    protected IManagerMarker searchManager(Class<? extends IManagerMarker> clazz){
        return null;
    }
    private static GlobalManager getManager(){
        return GlobalManager.getInstance();
    }
    private static UILayerManager exchangeUIlayer(){
        return ((UILayerManager)(findUIlayerManager.searchManager()));
    }

    private static LocalFilesManager exchangeLocalFiles(){
        return ((LocalFilesManager)(findLocalFilesManager.searchManager()));
    }
}