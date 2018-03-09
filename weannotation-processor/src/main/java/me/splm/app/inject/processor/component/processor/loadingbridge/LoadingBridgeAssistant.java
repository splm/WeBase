package me.splm.app.inject.processor.component.processor.loadingbridge;


import java.util.List;

import javax.lang.model.element.AnnotationMirror;

public class LoadingBridgeAssistant {

    //ArrayTypeName objArrayTy = ArrayTypeName.of(String.class);

    private String[] announceArray(List<? extends AnnotationMirror> set){
        String[] array=new String[set.size()];
        int i=0;
        for(AnnotationMirror t:set){
            array[i]=t.getAnnotationType().toString();
            i++;
        }
        return array;
    }

    public void declareArray(Class<?> arrayType){
        /*ArrayTypeName objArrayTy = ArrayTypeName.of(arrayType);
        String literal = "\"" + String.join("\",\"",array) + "\"";
        CodeBlock block = CodeBlock.builder().add("{"+literal+"}").build();
        FieldSpec mObjectArray = FieldSpec.builder(objArrayTy, "VALUES", Modifier.PUBLIC,Modifier.STATIC)
                .initializer(block)
                .build();*/
    }

}
