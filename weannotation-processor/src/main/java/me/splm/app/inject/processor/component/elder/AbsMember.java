package me.splm.app.inject.processor.component.elder;

import java.util.Collection;
@Deprecated
public abstract class AbsMember {
    protected void add(Collection c,Object o){
        c.add(o);
    }
    protected boolean remove(Collection c,Object o){
        return c.remove(o);
    }

}
