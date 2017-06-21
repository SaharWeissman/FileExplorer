package com.saharw.openfs.api.impl;

import com.saharw.openfs.api.AbstractFSDataItem;
import com.saharw.openfs.api.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Sahar on 06/21/2017.
 */
public class JavaFileWrapper extends AbstractFSDataItem {
    public final File file;

    public JavaFileWrapper(File file, Type type, String name, String fullPath, Collection<AbstractFSDataItem> descendants, Set<AbstractFSDataItem> predecessors) {
        super(type, name, fullPath, descendants, predecessors);
        this.file = file;
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
