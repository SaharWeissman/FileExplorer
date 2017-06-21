package com.saharw.openfs.api;

/**
 * Created by Sahar on 06/21/2017.
 */
public abstract class AbstractFSOperationListener {
    public abstract void onSuccess(long totalTime);
    public abstract void onProgress(byte[] writtenBytes, long totalBytes);
    public abstract void onError(Throwable t);
}
