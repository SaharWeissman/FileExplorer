package com.saharw.openfs.core.op.base;

import com.saharw.openfs.api.AbstractFSDataItem;
import com.saharw.openfs.api.AbstractFSOperationListener;
import com.saharw.openfs.core.op.OperationType;
import org.jetbrains.annotations.Nullable;

import java.io.FileFilter;
import java.util.Collection;

/**
 * Created by Sahar on 06/21/2017.
 */
public abstract class AbstractFSOperation<T extends AbstractFSDataItem> {
    public long timePassedMillis;
    public final OperationType type;
    public final T abstractItem;

    public AbstractFSOperation(T item, long timePassedMillis, OperationType type){
        this.timePassedMillis = timePassedMillis;
        this.type = type;
        this.abstractItem = item;
    }

    public abstract void operate(Collection<AbstractFSOperationListener> listeners, @Nullable FileFilter fileFilter, boolean isFilterRecursive);
}
