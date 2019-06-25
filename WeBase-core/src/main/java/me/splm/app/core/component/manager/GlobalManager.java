package me.splm.app.core.component.manager;

public class GlobalManager extends AbsManager {
    private static GlobalManager mInstance;
    private GlobalManager(){}
    public static GlobalManager getInstance(){
        if(mInstance==null){
            synchronized (GlobalManager.class){
                if(mInstance==null){
                    mInstance=new GlobalManager();
                }
            }
        }
        return mInstance;
    }
}
