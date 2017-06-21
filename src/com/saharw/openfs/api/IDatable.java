package com.saharw.openfs.api;

/**
 * Created by Sahar on 06/21/2017.
 */
public abstract class IDatable {
    abstract byte[] getData();
    abstract void writeData(byte[] data, IAbstractWriteListener listener);
    long sizeBytes;
    long lastModified;
}
