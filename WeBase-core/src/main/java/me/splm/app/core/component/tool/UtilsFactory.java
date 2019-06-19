package me.splm.app.core.component.tool;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class UtilsFactory {

    public enum UtilsMarker {
        COMMON, TXT, WS,SharePreference
    }

    private static UtilsFactory instance;

    private UtilsFactory() {
    }

    public static UtilsFactory getInstance() {
        if (instance == null) {
            synchronized (UtilsFactory.class) {
                if (instance == null) {
                    instance = new UtilsFactory();
                }
            }
        }
        return instance;
    }

    public <T extends IUtilsMarker> T getCommUtils(UtilsMarker marker) {
        if (marker == UtilsMarker.COMMON) {
            return createNewInstance(CommUtils.class);
        } else if (marker == UtilsMarker.TXT) {

        } else if (marker == UtilsMarker.WS) {
        }else if(marker == UtilsMarker.SharePreference){
            return createNewInstance(SPUtils.class);
        }
        return null;
    }

    private <T extends IUtilsMarker> T createNewInstance(Class<? extends IUtilsMarker> utils) {
        try {
            Constructor constructor = utils.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (T) constructor.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
