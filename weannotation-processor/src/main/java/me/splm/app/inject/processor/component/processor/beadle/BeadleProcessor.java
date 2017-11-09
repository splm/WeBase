package me.splm.app.inject.processor.component.processor.beadle;


import com.google.auto.service.AutoService;
import me.splm.app.inject.annotation.WeInjectBeadle;
import me.splm.app.inject.processor.component.proxy.ActionFactory;
import me.splm.app.inject.processor.component.proxy.ActionTask;
import me.splm.app.inject.processor.component.proxy.ActionTaskQueue;
import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.core.ForemanProcessor;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class BeadleProcessor extends ForemanProcessor<WeInjectBeadle> {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return super.process(annotations, roundEnv);
    }

    @Override
    protected ActionTaskQueue getHowToCreate(TreeTrunk treeTrunk) {
        ActionTask taskA= ActionFactory.createAction(treeTrunk,GenerateBeadleAction.class);
        return ActionTaskQueue.construct(taskA);
    }
}
