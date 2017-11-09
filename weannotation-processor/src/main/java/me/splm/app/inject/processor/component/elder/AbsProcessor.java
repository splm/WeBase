package me.splm.app.inject.processor.component.elder;


import me.splm.app.inject.annotation.WeInjectPlumber;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@Deprecated
public abstract class AbsProcessor extends AbstractProcessor {

    protected Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        initProcessor(processingEnv);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager= processingEnv.getMessager();
        for (Element element : roundEnv.getElementsAnnotatedWith(WeInjectPlumber.class)) {
            if (element.getKind() == ElementKind.METHOD) {
                memberOfMethod(element);
            }
            if (element.getKind() == ElementKind.FIELD) {
                 memberOfField(element);
            }
            if (element.getKind() == ElementKind.CLASS) {
                 memberOfClass(element);
            }
        }
        action(annotations,roundEnv);
        return false;
    }

    protected void loge(String log){
        messager.printMessage(Diagnostic.Kind.NOTE, log);
    }

    protected abstract void initProcessor(ProcessingEnvironment processingEnv);
    protected abstract void memberOfMethod(Element e);
    protected abstract void memberOfField(Element e);
    protected abstract void memberOfClass(Element e);
    protected abstract void action(Set<? extends TypeElement> annotations, RoundEnvironment renv);
}
