package me.splm.app.inject.processor.component.processor.porter;

import com.google.auto.service.AutoService;
import me.splm.app.inject.annotation.WeInjectPorter;
import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.component.processor.loadingbridge.GenerateLoadingBridgeAction;
import me.splm.app.inject.processor.core.ForemanProcessor;
import me.splm.app.inject.processor.component.proxy.ActionFactory;
import me.splm.app.inject.processor.component.proxy.ActionTask;
import me.splm.app.inject.processor.component.proxy.ActionTaskQueue;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;


@AutoService(Processor.class)
public class PorterProcessor extends ForemanProcessor<WeInjectPorter> {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    protected ActionTaskQueue getHowToCreate(TreeTrunk treeTrunk) {
        ActionTask taskA= ActionFactory.createAction(treeTrunk,GeneratePorterAction.class);
        ActionTask taskB= ActionFactory.createAction(treeTrunk,GenerateLoadingBridgeAction.class);
        return ActionTaskQueue.construct(taskA,taskB);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return super.process(annotations,roundEnv);
    }
}
