package com.saharw.openfs.core.op.impl;

import com.saharw.openfs.api.AbstractFSOperationListener;
import com.saharw.openfs.api.impl.JavaFileWrapper;
import com.saharw.openfs.core.op.OperationType;
import com.saharw.openfs.core.op.base.AbstractFSOperation;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Sahar on 06/21/2017.
 */
public class AbstractFSListAbstractItems extends AbstractFSOperation<JavaFileWrapper> {

    private int mMaxLevelsDeep;

    public AbstractFSListAbstractItems(JavaFileWrapper item, long timePassedMillis, int maxLevelsDeep) {
        super(item, timePassedMillis, OperationType.LIST_FILES);
        this.mMaxLevelsDeep = maxLevelsDeep;
    }


    @Override
    public void operate(@Nullable Collection<AbstractFSOperationListener> listeners, @Nullable FileFilter fileFilter) {
        JavaFileWrapper fileWrapper = abstractItem;
        if(fileWrapper != null){

            // perform actual list files of abstract fs item
            operateListItemsInternal(fileWrapper, listeners, fileFilter, mMaxLevelsDeep);
        }else{
            if(listeners != null) {
                Iterator<AbstractFSOperationListener> iterator = listeners.iterator();
                while(iterator.hasNext()){
                    iterator.next().onError(new NullPointerException("AbstractFSListAbstractItems: abstract item is null!"));
                }
            }
        }
    }

    private void operateListItemsInternal(JavaFileWrapper fileWrapper, Collection<AbstractFSOperationListener> listeners, FileFilter fileFilter, int mMaxLevelsDeep) {
        File[] lstFiles;
        if(fileFilter == null){
            lstFiles = fileWrapper.file.listFiles();
        }else{
            lstFiles = fileWrapper.file.listFiles(fileFilter);
        }
        if(lstFiles != null){

        }else{
            if(listeners != null){
                Iterator<AbstractFSOperationListener> iterator = listeners.iterator();
                while(iterator.hasNext()){
                    iterator.next().onError(new NullPointerException(""));
                }
            }
        }
    }
}
