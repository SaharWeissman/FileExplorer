package com.saharw.openfs.core.op;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sahar on 06/21/2017.
 */
public enum OperationType {
    COPY(Arrays.asList(2)),
    DELETE(Arrays.asList(1)),
    LIST_FILES(Arrays.asList(0, 1)),
    CHANGE_DIR(Arrays.asList(0,1)),
    ENCRYPT(Arrays.asList(1)),
    EXIT(Arrays.asList(0)),
    DECRYPT(Arrays.asList(1)),
    ;

    public final List<Integer> numParams;

    OperationType(List<Integer> numParams){
        this.numParams = numParams;
    }
}
