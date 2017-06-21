package com.saharw.openfs.api;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Sahar on 06/21/2017.
 */
public abstract class IAbstractFSDataItem <T extends IDatable> implements Iterable<IAbstractFSDataItem>{
    public String name;
    public String fullPath;
    public T datable;
    public Collection<IAbstractFSDataItem> descendants;
    public Set<IAbstractFSDataItem> predecessors;
}
