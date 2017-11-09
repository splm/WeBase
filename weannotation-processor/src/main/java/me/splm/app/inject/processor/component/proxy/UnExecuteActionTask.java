package me.splm.app.inject.processor.component.proxy;


public class UnExecuteActionTask extends ActionTask {
    public UnExecuteActionTask(IArborAction action) {
        super(action);
    }
    public ActionTask prepareAction(TreeTrunk treeTrunk, ActionTask unExecuteActionTask) {
        return super.prepareAction(treeTrunk);
    }
}
