package me.splm.app.inject.processor.component.proxy;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import me.splm.app.inject.processor.core.Config;
import me.splm.app.inject.processor.core.FileGauge;

public class WeWorkersProxy {
    public static void bind(Object object) {
        accessAction(object);
    }

    private static void accessAction(Object object) {
        try {
            Class targetClz = object.getClass();
            String targetSimpleName=targetClz.getSimpleName();
            Class<?> valueClz = Class.forName(Config.GEN_fOLDER+".We"+ targetSimpleName+ Config.SUFFIX_RECORD);//Eg:com.jc.android.auto.WeMainActivity_Legend
            Field field=valueClz.getDeclaredField("VALUES");
            if(field!=null){
                Object o=field.get(valueClz);
                String[] values=(String[])o;
                for(String s:values){
                    String key= FileGauge.find(s);
                    if(key==null){
                        continue;
                    }
                    Class<?> proxyClazz=targetClz.getClassLoader().loadClass(Config.GEN_fOLDER+".We" + targetSimpleName + key);//Eg:com.jc.android.auto.WeMainActivity$$Porter
                    Method getInstance = proxyClazz.getDeclaredMethod("getInstance");
                    IWorkersProxy proxy = (IWorkersProxy) getInstance.invoke(null);
                    proxy.assist(object);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
