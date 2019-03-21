package me.splm.app.inject.processor.component.proxy;


public class ActionFactory {

    public static ActionTask createAction(TreeTrunk treeTrunk,Class<? extends IArborAction> actionClz){
        IArborAction action=createInstance(actionClz);
        if(action!=null){
            return action.prepareAction(treeTrunk);
        }
        return null;
    }

    private static IArborAction createInstance(Class<? extends IArborAction> actionClz){
        try{
            return actionClz.newInstance();
        }catch(InstantiationException ie){
            ie.printStackTrace();
        }catch (IllegalAccessException ill){
            ill.printStackTrace();
        }
        return null;
    }
}
