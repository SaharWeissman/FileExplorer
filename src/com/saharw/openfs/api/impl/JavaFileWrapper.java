package com.saharw.openfs.api.impl;

import com.saharw.openfs.api.AbstractFSDataItem;
import com.saharw.openfs.api.AbstractType;
import com.saharw.openfs.core.op.util.FilesUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Created by Sahar on 06/21/2017.
 */
public class JavaFileWrapper extends AbstractFSDataItem<JavaFileWrapperOperationResult> {
    public final File file;

    public JavaFileWrapper(File file, AbstractType type, String name, String path, Collection<AbstractFSDataItem> descendants, Set<AbstractFSDataItem> predecessors) {
        super(type, name, path, descendants, predecessors);
        this.file = file;
    }

    public static JavaFileWrapper createFromBaseType(File baseTypeInstance, FilesUtil filesUtil, FileFilter fileFilter, int maxDepthLevel, int currDepthLevel, boolean isFilterRecursive){
        JavaFileWrapper javaFileWrapper = null;
        if(currDepthLevel < maxDepthLevel) {
            if (baseTypeInstance != null && baseTypeInstance.getName() != null && !baseTypeInstance.getName().isEmpty()
                    && baseTypeInstance.getPath() != null && !baseTypeInstance.getPath().isEmpty()) {
                String path = baseTypeInstance.getPath();
                String name = baseTypeInstance.getName();
                AbstractType type = AbstractType.UNKNOWN;
                Collection<AbstractFSDataItem> descendants = null;
                if (baseTypeInstance.isDirectory() && baseTypeInstance.exists() && baseTypeInstance.canRead()) {
                    type = AbstractType.DIRECTORY;

                    // add descendants
                    File[] lstFiles = null;
                    if (fileFilter != null) {
                        lstFiles = baseTypeInstance.listFiles(fileFilter);
                    } else {
                        lstFiles = baseTypeInstance.listFiles();
                    }

                    if (lstFiles != null) {
                        descendants = new ArrayList<>(lstFiles.length);
                        for (int i = 0; i < lstFiles.length; i++) {
                            File currFile = lstFiles[i];
                            JavaFileWrapper wrapper = createFromBaseType(currFile, filesUtil, fileFilter, maxDepthLevel, currDepthLevel++, isFilterRecursive);
                            descendants.add(wrapper);
                        }
                    }
                } else {
                    type = filesUtil.getAbstractType(baseTypeInstance);
                }
                javaFileWrapper = new JavaFileWrapper(baseTypeInstance, type, name, path, descendants, null);
            }
        }
        return javaFileWrapper;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(file != null && file.exists() && file.getPath() != null && file.getName() != null && !file.getName().isEmpty() && !file.getPath().isEmpty()) {
            sb.append(type.txt+"\t");
            if(file.isDirectory()) {
                sb.append(file.getPath() + "\\");
            }
            sb.append(String.format("%-4s \tSize:%-4d Bytes\tlast mod: %-4s", file.getName(), file.length(), new Date(file.lastModified())));
        }

        String isDir = file.isDirectory() ? "D" : "f";
        String canRead = file.canRead() ? " R" : "";
        String canWrite = file.canWrite() ? " W" : "";
        String canExecute = file.canExecute() ? " Exe" : "";
        sb.append(String.format("\tmeta: %-4s", isDir, canRead, canWrite, canExecute));
        return sb.toString();
    }
}
