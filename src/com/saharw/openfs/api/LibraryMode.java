package com.saharw.openfs.api;

/**
 * Created by Sahar on 06/21/2017.
 */
public enum LibraryMode {
    READ_ONLY(0),
    WRITABLE(1),
    ENCRYPT(2),
    DECRYPT(3),
    COMPRESS(4),
    EXECUTE(5),
    DEFAULT(0),
    ;

    public final int value;
    LibraryMode(int value){
        this.value = value;
    }
}
