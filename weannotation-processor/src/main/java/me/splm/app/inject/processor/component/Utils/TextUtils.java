package me.splm.app.inject.processor.component.Utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static boolean isEquals(CharSequence var1,CharSequence var2){
        if(var1==var2){
            return true;
        }
        int length;
        if(var1!=null&&var2!=null&&(length=var1.length())==var2.length()){
            if(var1 instanceof String && var2 instanceof String){
                return var1.equals(var2);
            }else{
                for(int i=0;i<length;i++){
                    if(var1.charAt(i)!=var2.charAt(i)){
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static String md5(String content) {
        if(TextUtils.isEmpty(content)){
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(content.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
