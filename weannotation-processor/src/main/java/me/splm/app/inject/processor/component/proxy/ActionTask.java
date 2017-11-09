package me.splm.app.inject.processor.component.proxy;


import java.io.IOException;

import javax.annotation.processing.ProcessingEnvironment;

public class ActionTask implements IArborAction {

    protected IArborAction mAction;

    public ActionTask(IArborAction action) {
        this.mAction = action;
    }

    @Override
    public ActionTask prepareAction(TreeTrunk treeTrunk) {
        return mAction.prepareAction(treeTrunk);
    }


    @Override
    public void performAction(ProcessingEnvironment processingEnvironment) {
        try {
            mAction.performAction(processingEnvironment);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
