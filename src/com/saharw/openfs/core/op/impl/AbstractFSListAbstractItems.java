package com.saharw.openfs.core.op.impl;

import com.saharw.openfs.api.AbstractFSOperationListener;
import com.saharw.openfs.api.impl.JavaFileWrapper;
import com.saharw.openfs.core.op.AbstractFSTree;
import com.saharw.openfs.core.op.OperationType;
import com.saharw.openfs.core.op.base.AbstractFSOperation;
import com.saharw.openfs.core.op.util.FilesUtil;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Sahar on 06/21/2017.
 */
public class AbstractFSListAbstractItems extends AbstractFSOperation<JavaFileWrapper> {

    private final FilesUtil mFileUtils;
    private int mMaxLevelsDeep;

    public AbstractFSListAbstractItems(JavaFileWrapper item, FilesUtil utils, long timePassedMillis, int maxLevelsDeep) {
        super(item, timePassedMillis, OperationType.LIST_FILES);
        this.mMaxLevelsDeep = maxLevelsDeep;
        this.mFileUtils = utils;
    }


    @Override
    public void operate(@Nullable Collection<AbstractFSOperationListener> listeners, @Nullable FileFilter fileFilter, boolean isFilterRecursive) {
        JavaFileWrapper fileWrapper = abstractItem;
        if(fileWrapper != null){

            // perform actual list files of abstract fs item
            try {
                operateListItemsInternal(fileWrapper, listeners, fileFilter, mMaxLevelsDeep, 0, isFilterRecursive);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            if(listeners != null) {
                Iterator<AbstractFSOperationListener> iterator = listeners.iterator();
                while(iterator.hasNext()){
                    iterator.next().onError(new NullPointerException("AbstractFSListAbstractItems: abstract item is null!"));
                }
            }
        }
    }

    private void operateListItemsInternal(JavaFileWrapper fileWrapper, Collection<AbstractFSOperationListener> listeners, FileFilter fileFilter, int mMaxLevelsDeep, int currLevelsDeep, boolean isFilterRecursive) throws IOException {
        File[] lstFiles;
        if(fileFilter == null){
            lstFiles = fileWrapper.file.listFiles();
        }else{
            lstFiles = fileWrapper.file.listFiles(fileFilter);
        }
        if(lstFiles != null && lstFiles.length > 0){
            AbstractFSTree absTree = new AbstractFSTree(fileWrapper);
            for(int i = 0; i < lstFiles.length; i++){
                File file = lstFiles[i];
                if(file != null && file.getName() != null && !file.getName().isEmpty() && file.getPath() != null && !file.getPath().isEmpty()) {
                    absTree.root.addDescendant(JavaFileWrapper.createFromBaseType(file, mFileUtils, fileFilter, mMaxLevelsDeep, currLevelsDeep, isFilterRecursive));
                }
            }
            if(listeners != null && !listeners.isEmpty()){
                Iterator<AbstractFSOperationListener> iterator = listeners.iterator();
                while(iterator.hasNext()){
                    iterator.next().onSuccess(absTree);
                }
            }

        }else{
            if(listeners != null && !listeners.isEmpty()){
                Iterator<AbstractFSOperationListener> iterator = listeners.iterator();
                while(iterator.hasNext()){
                    iterator.next().onError(new NullPointerException(""));
                }
            }
        }
    }
}
