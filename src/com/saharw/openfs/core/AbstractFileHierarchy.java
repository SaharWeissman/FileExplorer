package com.saharw.openfs.core;

import com.saharw.openfs.api.IAbstractFSDataItem;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Sahar on 06/21/2017.
 */
public class AbstractFileHierarchy implements Iterable<Collection<IAbstractFSDataItem>>{

    private final IAbstractFSDataItem mHead;
    private final Iterator<Collection<IAbstractFSDataItem>> mIterator;
    private final int mMaxLevelDown;
    private final AtomicInteger mCurrLevelDown;

    public AbstractFileHierarchy(IAbstractFSDataItem head, int maxLevelsDown){
        this.mHead = head;
        this.mCurrLevelDown = new AtomicInteger(0);
        this.mIterator = new Iterator<Collection<IAbstractFSDataItem>>() {
            @Override
            public boolean hasNext() {
                if(mCurrLevelDown.get() >= maxLevelsDown){
                    return false;
                }else {
                    mCurrLevelDown.incrementAndGet();
                    return mHead.descendants != null;
                }
            }

            @Override
            public Collection<IAbstractFSDataItem> next() {
                return mHead.descendants;
            }
        };
        this.mMaxLevelDown = maxLevelsDown;
    }

    @NotNull
    @Override
    public Iterator<Collection<IAbstractFSDataItem>> iterator() {
        return this.mIterator;
    }
}
