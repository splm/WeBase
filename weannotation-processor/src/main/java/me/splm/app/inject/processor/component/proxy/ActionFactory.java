package me.splm.app.inject.processor.component.proxy;


public class ActionFactory {

    public static ActionTask createAction(TreeTrunk treeTrunk,Class<? extends IArborAction> actionClz){
        IArborAction action=createInstance(actionClz);
        return action.prepareAction(treeTrunk);
    }

    private static IArborAction createInstance(Class<? extends IArborAction> actionClz){
        try{
            return (IArborAction)actionClz.newInstance();
        }catch(InstantiationException ie){
            ie.printStackTrace();
        }catch (IllegalAccessException ill){
            ill.printStackTrace();
        }
        return null;
    }
}
