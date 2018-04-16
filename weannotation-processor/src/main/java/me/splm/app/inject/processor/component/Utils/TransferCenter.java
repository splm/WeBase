package me.splm.app.inject.processor.component.Utils;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import me.splm.app.inject.processor.log.Logger;
import me.splm.app.inject.processor.log.LoggerFactory;

public class TransferCenter {
    protected static final Logger LOGGER= LoggerFactory.getLogger(TransferCenter.class);
    /**
     * TypeName to className
     * @param typeName
     * @return
     */
    public static ClassName typeName2ClassName(TypeName typeName){
        if(typeName==null){
            return null;
        }
        String fullName=typeName.toString();
        String[] result=PackageUtils.split(fullName);
        String p=result[0];
        String c=result[1];
        return ClassName.get(p,c);
    }

    /**
     * Classname to typeName
     * @param className
     * @return
     */
    public static TypeName className2TypeName(ClassName className){
        return className.withoutAnnotations();
    }

    /**
     * typeName to Class
     * @param typeName
     * @return
     */
    public static Class typeName2Class(TypeName typeName){
        try{
            return Class.forName(typeName.toString());
        }catch (ClassNotFoundException e){
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Class to typeName
     * @param clazz
     * @return
     */
    public static TypeName class2TypeName(Class clazz){
        ClassName className=ClassName.get(clazz);
        return className2TypeName(className);
    }

    public static Class ClassName2Class(ClassName className){
        TypeName typeName=className2TypeName(className);
        return typeName2Class(typeName);
    }

    public static ClassName Class2ClassName(Class clazz){
        return ClassName.get(clazz);
    }

}
