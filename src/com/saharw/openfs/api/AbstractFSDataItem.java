package com.saharw.openfs.api;

import com.saharw.openfs.core.op.result.AbstractFSOperationResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Sahar on 06/21/2017.
 */
public abstract class AbstractFSDataItem<T extends AbstractFSOperationResult>{
    public final AbstractType type;
    public String name;
    public String fullPath;
    public Collection<AbstractFSDataItem> descendants;
    private Set<AbstractFSDataItem> predecessors;

    public AbstractFSDataItem(AbstractType type, String name, String fullPath, Collection<AbstractFSDataItem> descendants, Set<AbstractFSDataItem> predecessors){
        this.type = type;
        this.name = name;
        this.fullPath = fullPath;
        this.descendants = descendants;
        this.predecessors = predecessors;
    }


    public void addDescendant(AbstractFSDataItem descendant){
        if(this.descendants == null){
            this.descendants = new ArrayList<>(1);
        }
        this.descendants.add(descendant);
    }

    public boolean removeDescendant(AbstractFSDataItem descendant){
        boolean removeSuccessful = false;
        if(this.descendants != null){
            removeSuccessful = this.descendants.remove(descendant);
        }
        return removeSuccessful;
    }
}
