package me.splm.app.inject.processor.component.Utils;


public final class TextUtils {

    public static boolean isEmpty(String target){
        if(target==null){
            return true;
        }else{
            if("".equals(target)){
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(String...targets){
        for(String s : targets){
            boolean isTrue=isEmpty(s);
            if(isTrue){
                return true;
            }
        }
        return false;
    }
}
