package com.saharw.openfs.api;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Sahar on 06/21/2017.
 */
public abstract class AbstractFSDataItem {
    public final Type type;
    public String name;
    public String fullPath;
    public Collection<AbstractFSDataItem> descendants;
    public Set<AbstractFSDataItem> predecessors;

    public AbstractFSDataItem(Type type, String name, String fullPath, Collection<AbstractFSDataItem> descendants, Set<AbstractFSDataItem> predecessors){
        this.type = type;
        this.name = name;
        this.fullPath = fullPath;
        this.descendants = descendants;
        this.predecessors = predecessors;
    }
}
