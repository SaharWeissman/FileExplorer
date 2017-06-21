package com.saharw.openfs.api;

/**
 * Created by Sahar on 06/21/2017.
 */
public abstract class IAbstractWriteListener {
    abstract void onSuccess(long totalTime);
    abstract void onProgress(byte[] writtenBytes, long totalBytes);
    abstract void onError(Throwable t);
}
