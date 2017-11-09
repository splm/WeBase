package me.splm.app.inject.processor.component;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;



public class TargetClassComponent {
    private String writeName;//A class file name which you want to generate.
    private ClassName classNameOfSingleton;//The type of the singleton
    private String absNameOfTarget;//Abstract name of the target class
    private String actNameOfField;//The name of the current activity field.
    private CodeBlock codeOfInitView;//Additional codes.
    private Class<?> subInterce;//Subclass or interface.
    public TargetClassComponent(TargetClassBuilder builder) {
        this.writeName=builder.writeName;
        this.absNameOfTarget=builder.absNameOfTarget;
        this.classNameOfSingleton=builder.clazzNameOfSingleton;
        this.actNameOfField=builder.actNameOfField;
        this.codeOfInitView=builder.codeOfInitView;
        this.subInterce=builder.subInterface;
    }

    public String getWriteName() {
        return writeName;
    }

    public ClassName getClassNameOfSingleton() {
        return classNameOfSingleton;
    }

    public String getAbsNameOfTarget() {
        return absNameOfTarget;
    }

    public String getActNameOfField() {
        return actNameOfField;
    }

    public CodeBlock getCodeOfInitView() {
        return codeOfInitView;
    }

    public Class<?> getSubInterce(){
        return this.subInterce;
    }

    public static class TargetClassBuilder {
        private String writeName;
        private ClassName clazzNameOfSingleton;
        private String absNameOfTarget;
        private String actNameOfField;
        private CodeBlock codeOfInitView;
        private Class<?> subInterface;
        public TargetClassBuilder addWriteName(String writeName) {
            this.writeName = writeName;
            return this;
        }
        public TargetClassBuilder addClassNameOfSingleton(ClassName clazzName){
            this.clazzNameOfSingleton=clazzName;
            return this;
        }

        public TargetClassBuilder addAbsNameOfTarget(String absName){
            this.absNameOfTarget=absName;
            return this;
        }

        public TargetClassBuilder addActNameofField(String fieldName){
            this.actNameOfField=fieldName;
            return this;
        }

        public TargetClassBuilder addCodeOfInitView(CodeBlock codeBlock){
            this.codeOfInitView=codeBlock;
            return this;
        }

        public TargetClassBuilder addSubInterface(Class<?> clazz){
            this.subInterface=clazz;
            return this;
        }

        public TargetClassComponent build() {
            return new TargetClassComponent(this);
        }
    }
}
