package me.splm.app.inject.processor.component.processor.beadle;


import com.google.auto.service.AutoService;

import javax.annotation.processing.Processor;

import me.splm.app.inject.annotation.WeInjectBeadle;
import me.splm.app.inject.processor.component.proxy.ActionFactory;
import me.splm.app.inject.processor.component.proxy.ActionTask;
import me.splm.app.inject.processor.component.proxy.ActionTaskQueue;
import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.core.ForemanProcessor;

@AutoService(Processor.class)
public class BeadleProcessor extends ForemanProcessor<WeInjectBeadle> {

    @Override
    protected ActionTaskQueue getHowToCreate(TreeTrunk treeTrunk) {
        ActionTask taskA= ActionFactory.createAction(treeTrunk,GenerateBeadleAction.class);
        return ActionTaskQueue.construct(taskA);
    }
}
