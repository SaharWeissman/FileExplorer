package com.saharw.openfs.app;

import com.saharw.openfs.api.AbstractFSDataItem;
import com.saharw.openfs.api.AbstractType;
import com.saharw.openfs.api.IStringable;
import com.saharw.openfs.api.impl.JavaFileWrapper;
import com.saharw.openfs.core.op.AbstractFSTree;
import com.saharw.openfs.core.verbose.VerboseLevel;

import java.io.FileFilter;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Sahar on 06/22/2017.
 */
public class CustomShell {
    private final AbstractFSTree<JavaFileWrapper> mTree;
    private final int mMaxDepthLevel;
    private final FileFilter fileFilter;
    private final int mCurrDepthLevel;

    public CustomShell(AbstractFSTree tree, int maxDepthLevel, int currDepthLevel, FileFilter fileFilter){
        this.mTree = tree;
        this.mMaxDepthLevel = maxDepthLevel;
        this.fileFilter = fileFilter;
        this.mCurrDepthLevel = currDepthLevel;
    }

    public StringBuilder listFiles(VerboseLevel level) {
        StringBuilder sb = new StringBuilder();
        JavaFileWrapper root = mTree.root;
        if(mCurrDepthLevel == 0){
            if(root instanceof IStringable){
                sb.append(((IStringable)root).stringify(level)+"\n");
            }
        }
        Collection<AbstractFSDataItem> descendants = root.descendants;
        if(descendants != null) {
            Iterator<AbstractFSDataItem> iterator = descendants.iterator();
            int currStep = mCurrDepthLevel;
            while (currStep < mMaxDepthLevel) {
                currStep++;
                while (iterator.hasNext() && currStep < mMaxDepthLevel) {
                    AbstractFSDataItem child = iterator.next();
                    if(child != null) {
                        for (int i = 0; i <= mCurrDepthLevel; i++) {
                            sb.append("\t");
                        }

                        if(child instanceof IStringable){
                            sb.append(((IStringable)child).stringify(level));
                            sb.append(((IStringable)child).stringify(level));

                        }
                        CustomShell customShell = new CustomShell(new AbstractFSTree(child), mMaxDepthLevel, currStep, fileFilter);
                        sb.append("\n" + customShell.listFiles(level));
                    }
                }
            }
        }
        return sb;
    }
}
