package com.jc.android.baselib.manager;

/**
 * You can use this class to get the Manager which you like.
 */
public enum GlobalManagerManual {

    /*****UIlayerManager******/
    findUIlayerManager(){
        @Override
        public IManagerMarker searchManager() {
            return GlobalManagerManual.getManager().findManagerByTag(UILayerManager.class);
        }
    },
    findFragmentCoreManager(){
        @Override
        public IManagerMarker searchManager() {
            return GlobalManagerManual.exchangeUIlayer().findManagerByTag(FragmentCoreManager.class);
        }
    },
    findActivityCoreManager(){
        @Override
        public IManagerMarker searchManager() {
            return GlobalManagerManual.exchangeUIlayer().findManagerByTag(ActivityCoreManager.class);
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