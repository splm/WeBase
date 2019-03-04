package me.splm.app.inject.processor.component.processor.plumber;

import com.google.auto.service.AutoService;

import javax.annotation.processing.Processor;

import me.splm.app.inject.annotation.WeInjectPlumber;
import me.splm.app.inject.processor.component.proxy.ActionFactory;
import me.splm.app.inject.processor.component.proxy.ActionTask;
import me.splm.app.inject.processor.component.proxy.ActionTaskQueue;
import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.core.ForemanProcessor;

@AutoService(Processor.class)
public class PlumberProcessor2 extends ForemanProcessor<WeInjectPlumber> {

    @Override
    public ActionTaskQueue getHowToCreate(TreeTrunk treeTrunk) {
        LOGGER.info("Hello,new shit nerd!");
        ActionTask taskA= ActionFactory.createAction(treeTrunk,GeneratePlumberAction.class);
        return ActionTaskQueue.construct(taskA);
    }
}
