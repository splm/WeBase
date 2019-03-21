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

    /*@Override
    protected Class<WeInjectBeadle> getAnnotationClass() {
        return WeInjectBeadle.class;
    }*/

    /*@Override
    public void executeTask(Map<String, TreeTrunk> mapper) {
        super.executeTask(mapper);
        for (TreeTrunk pc : mTypeMapper.values()) {
            boolean isEquals=pc.getTypeElement().getAnnotation(WeInjectBeadle.class).equals(WeInjectBeadle.class);
            if(isEquals){
                ActionTaskQueue queue = getHowToCreate(pc);
                pc.executeTask(queue, processingEnv);
            }
        }
    }*/
}
