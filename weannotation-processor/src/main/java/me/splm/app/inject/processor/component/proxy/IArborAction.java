package me.splm.app.inject.processor.component.proxy;


import java.io.IOException;

import javax.annotation.processing.ProcessingEnvironment;

public interface IArborAction {
    ActionTask prepareAction(TreeTrunk treeTrunk) throws NullPointerException;
    void performAction(ProcessingEnvironment processingEnvironment) throws IOException;
}
