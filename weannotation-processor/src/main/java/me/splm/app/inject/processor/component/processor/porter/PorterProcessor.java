package me.splm.app.inject.processor.component.processor.porter;

import com.google.auto.service.AutoService;

import javax.annotation.processing.Processor;

import me.splm.app.inject.annotation.WeInjectPorter;
import me.splm.app.inject.processor.component.processor.loadingbridge.GenerateLoadingBridgeAction;
import me.splm.app.inject.processor.component.proxy.ActionFactory;
import me.splm.app.inject.processor.component.proxy.ActionTask;
import me.splm.app.inject.processor.component.proxy.ActionTaskQueue;
import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.core.ForemanProcessor;


@AutoService(Processor.class)
public class PorterProcessor extends ForemanProcessor<WeInjectPorter> {

    @Override
    protected ActionTaskQueue getHowToCreate(TreeTrunk treeTrunk) {
        ActionTask taskA= ActionFactory.createAction(treeTrunk,GeneratePorterAction.class);
        ActionTask taskB= ActionFactory.createAction(treeTrunk,GenerateLoadingBridgeAction.class);
        return ActionTaskQueue.construct(taskA,taskB);
    }

    /*@Override
    protected Class<WeInjectPorter> getAnnotationClass() {
        return WeInjectPorter.class;
    }*/
}
