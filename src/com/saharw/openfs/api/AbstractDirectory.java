package com.saharw.openfs.api;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Sahar on 06/21/2017.
 */
public class AbstractDirectory {
    public AbstractFSDataItem root;
    AbstractDataConsumer view;
    LibraryMode[] modes;

    public AbstractDirectory(AbstractFSDataItem root, AbstractDataConsumer view, LibraryMode...modes) throws NullPointerException{
        if(root == null){
            throw new NullPointerException("root of abstract directory cannot be null!");
        }else{
            this.root = root;
        }

        if(view == null){
            throw new NullPointerException("view of abstract directory cannot be null!");
        }else{
            this.view = view;
        }

        if(modes == null){
            this.modes = new LibraryMode[]{LibraryMode.DEFAULT};
        }else {
            this.modes = modes;
        }
    }

    public void getAbstractContent(@Nullable FileFilter fileFilter) throws NullPointerException, IllegalArgumentException{
        try {
            File rootFile = new File(root.fullPath);
            if(rootFile == null || !rootFile.exists()){
                throw new IllegalArgumentException("root file is invalid (null or does not exist!)");
            }
            if(!validModes(rootFile)){
                throw new IllegalArgumentException("modes defined for directory cannot be applied!");
            }
            if(fileFilter != null){

            }

        }catch (NullPointerException e){
            throw new NullPointerException("root abstract data item path is null!");
        }catch (SecurityException e){
            throw new NullPointerException("unable to list files in root dir of abstract library!");
        }
    }

    private boolean validModes(File rootFile) throws NullPointerException{
        boolean modesValid = false;
        if(rootFile == null){
            throw new NullPointerException("root file cannot be null!");
        }

        boolean shouldBeReadable = false;
        boolean shouldBeWritable = false;
        boolean shouldBeExecutable = false;
        for(LibraryMode mode : this.modes){
            switch (mode){
                case DEFAULT:{
                    shouldBeReadable = true;
                    shouldBeWritable = true;
                    shouldBeExecutable = false;
                    break;
                }
                case READ_ONLY:{
                    shouldBeReadable = true;
                    shouldBeWritable = false;
                    shouldBeExecutable = false;
                    break;
                }
                case EXECUTE:
                case WRITABLE:
                case ENCRYPT:
                case DECRYPT:
                case COMPRESS:{
                    shouldBeReadable = true;
                    shouldBeWritable = true;
                    shouldBeExecutable = true;
                    break;
                }
                default:{
                    shouldBeReadable = true;
                    shouldBeWritable = false;
                    shouldBeExecutable = false;
                }
            }
        }

        if(shouldBeReadable){
            if(rootFile.exists() && rootFile.canRead()){
                modesValid = true;
            }
        }
        if(modesValid && shouldBeWritable){
            if(rootFile.exists() && rootFile.canWrite()){
                modesValid = true;
            }
        }
        if(modesValid && shouldBeExecutable){
            if(rootFile.exists() && rootFile.canExecute()){
                modesValid = true;
            }
        }
        return modesValid;
    }
}
