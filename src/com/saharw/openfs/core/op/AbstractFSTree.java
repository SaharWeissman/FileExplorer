package com.saharw.openfs.core.op;

import com.saharw.openfs.api.AbstractFSDataItem;

/**
 * Created by Sahar on 06/21/2017.
 */
public class AbstractFSTree<T extends AbstractFSDataItem> {
    public T root;

    public AbstractFSTree(T root){
        this.root = root;
    }
}
