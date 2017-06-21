package com.saharw.openfs.api;

import com.saharw.openfs.core.op.AbstractFSTree;

/**
 * Created by Sahar on 06/21/2017.
 */
public abstract class AbstractFSOperationListener {
    public abstract void onSuccess(AbstractFSTree tree);
    public abstract void onProgress(AbstractFSTree partialTree);
    public abstract void onError(Throwable t);
}
