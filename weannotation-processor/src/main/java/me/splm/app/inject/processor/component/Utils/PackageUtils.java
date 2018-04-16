package me.splm.app.inject.processor.component.Utils;


public class PackageUtils {
    public static String[] split(String full){
        if(TextUtils.isEmpty(full)){
            return null;
        }
        int index=full.lastIndexOf(".");
        String p=full.substring(0,index);
        String s=full.substring(index+1);
        return new String[]{p,s};
    }
}
