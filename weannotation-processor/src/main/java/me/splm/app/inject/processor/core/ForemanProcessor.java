package me.splm.app.inject.processor.core;

import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.component.proxy.ActionTaskQueue;
import me.splm.app.inject.processor.component.proxy.TreeBranchesMethods;
import me.splm.app.inject.processor.component.proxy.TreeLeavesFields;
import me.splm.app.inject.processor.log.Logger;
import me.splm.app.inject.processor.log.LoggerContext;
import me.splm.app.inject.processor.log.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;


public abstract class ForemanProcessor<T extends Annotation> extends AbstractProcessor {
    private TreeTrunk mTreeTrunk;
    protected Map<String, TreeTrunk> mTypeMapper = new HashMap<>();
    private static final Set<String> mSupportedAnnotationTypes = new HashSet<>();
    protected ProcessingEnvironment mProcessingEnv;
    protected static final Logger LOGGER= LoggerFactory.getLogger(ForemanProcessor.class);

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mSupportedAnnotationTypes.add(getAnnotationClass().getCanonicalName());
        this.mProcessingEnv = processingEnv;
        LoggerContext.getInstance().setProcessingEnvironment(mProcessingEnv);//Init log engine
        LOGGER.info(mSupportedAnnotationTypes.size()+"***8888888888");
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return mSupportedAnnotationTypes;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(getAnnotationClass())) {
            List<? extends AnnotationMirror> list;
            if (element.getKind() == ElementKind.CLASS) {
                list = element.getAnnotationMirrors();
                mTreeTrunk = TreeTrunk.getRootClass(mTypeMapper, element);
                mTreeTrunk.bindMemberOfAnnotation(list);
                mTreeTrunk.setEnviroment(mProcessingEnv);
            }
            if (element.getKind() == ElementKind.FIELD) {
                VariableElement variableElement = (VariableElement) element;
                list = variableElement.getAnnotationMirrors();
                TreeLeavesFields classMember = new TreeLeavesFields(variableElement);
                mTreeTrunk=TreeTrunk.getRootClass(mTypeMapper,variableElement.getEnclosingElement());
                mTreeTrunk.bindMemberOfFields(classMember);
                classMember.bindMemberOfAnnotation(list);
                mTreeTrunk.setEnviroment(mProcessingEnv);
            }
            if (element.getKind() == ElementKind.METHOD) {
                ExecutableElement executableElement = (ExecutableElement) element;
                LOGGER.info("999999999999999999"+executableElement.getEnclosingElement());
                list = executableElement.getAnnotationMirrors();
                TreeBranchesMethods methodMember = new TreeBranchesMethods(executableElement);
                mTreeTrunk=TreeTrunk.getRootClass(mTypeMapper,executableElement.getEnclosingElement());
                LOGGER.info(mTreeTrunk+"");
                mTreeTrunk.bindMemberOfMethods(methodMember);
                methodMember.bindMemberOfAnnotation(list);
                mTreeTrunk.setEnviroment(mProcessingEnv);
            }
        }
        for (TreeTrunk pc : mTypeMapper.values()) {
            ActionTaskQueue queue = getHowToCreate(pc);
            pc.executeTask(queue, processingEnv);
        }
        return false;
    }

    private Class<T> getAnnotationClass() {
        Class<T> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            entityClass = (Class<T>) p[0];
        }
        return entityClass;
    }

    protected abstract ActionTaskQueue getHowToCreate(TreeTrunk treeTrunk);

}
