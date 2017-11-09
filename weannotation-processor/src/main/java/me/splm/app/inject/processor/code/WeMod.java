package me.splm.app.inject.processor.code;


import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

public class WeMod {

    public static final int NONE = 0x000;
    public static final int PUBLIC = 0x001;
    public static final int PROTECTED = 0x002;
    public static final int PRIVATE = 0x004;
    public static final int FINAL = 0x008;
    public static final int STATIC = 0x010;
    public static final int ABSTRACT = 0x020;
    public static final int NATIVE = 0x040;
    public static final int SYNCHRONIZED = 0x080;
    public static final int TRANSIENT = 0x100;
    public static final int VOLATILE = 0x200;
    /** Java8 default method indicator */
    public static final int DEFAULT = 0x400;
    public static final int STRICTFP = 0x800;

    private static final int FIELD = PUBLIC | PRIVATE | PROTECTED | STATIC | FINAL | TRANSIENT | VOLATILE;
    private static final int METHOD = PUBLIC | PRIVATE | PROTECTED | FINAL | ABSTRACT | STATIC | NATIVE | SYNCHRONIZED| DEFAULT;
    
    public List<Modifier> resolve(int mods){
        List<Modifier> list=new ArrayList<>();
        if ((mods & PUBLIC) != 0)
            list.add(Modifier.PUBLIC);
        if ((mods & PROTECTED) != 0)
            list.add(Modifier.PROTECTED);
        if ((mods & PRIVATE) != 0)
            list.add(Modifier.PRIVATE);
        if ((mods & FINAL) != 0)
            list.add(Modifier.FINAL);
        if ((mods & STATIC) != 0)
            list.add(Modifier.STATIC);
        if ((mods & ABSTRACT) != 0)
            list.add(Modifier.ABSTRACT);
        if ((mods & NATIVE) != 0)
            list.add(Modifier.NATIVE);
        if ((mods & SYNCHRONIZED) != 0)
            list.add(Modifier.SYNCHRONIZED);
        if ((mods & TRANSIENT) != 0)
            list.add(Modifier.TRANSIENT);
        if ((mods & VOLATILE) != 0)
            list.add(Modifier.VOLATILE);
        if ((mods & DEFAULT) != 0)
            list.add(Modifier.DEFAULT);
        if ((mods & STRICTFP) != 0)
            list.add(Modifier.STRICTFP);
        return list;
    }
}
