package me.splm.app.inject.processor.component.processor.plumber;

import com.google.auto.service.AutoService;
import me.splm.app.inject.annotation.WeInjectPlumber;
import me.splm.app.inject.processor.component.proxy.ActionTask;
import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.core.ForemanProcessor;
import me.splm.app.inject.processor.component.proxy.ActionFactory;
import me.splm.app.inject.processor.component.proxy.ActionTaskQueue;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class PlumberProcessor2 extends ForemanProcessor<WeInjectPlumber> {
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return super.process(annotations, roundEnv);
    }

    @Override
    public ActionTaskQueue getHowToCreate(TreeTrunk treeTrunk) {
        LOGGER.info("Hello,new shit nerd!");
        ActionTask taskA= ActionFactory.createAction(treeTrunk,GeneratePlumberAction.class);
        return ActionTaskQueue.construct(taskA);
    }
}
