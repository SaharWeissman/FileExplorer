package com.saharw.openfs.api;

/**
 * Created by Sahar on 06/21/2017.
 */
public enum AbstractType {
    DIRECTORY("*D*"),
    DIRECTORY_SYSTEM("*DS*"),
    FILE_TYPE_TXT("*Txt*"),
    FILE_TYPE_IMAGE("*Img*"),
    FILE_TYPE_VIDEO("*Vid*"),
    FILE_TYPE_AUDIO("*Aud*"),
    FILE_TYPE_BINARY("*bin*"),
    FILE_TYPE_SECURITY("*sec*"),
    FILE_TYPE_LOG("*log*"),
    FILE_TYPE_SYSTEM("*S*"),
    FILE_TYPE_HIDDEN("*h*"),
    UNKNOWN("*Ukn*"),
    INVALID("*e*"),
    CONFIG("*cfg*"),
    SETTINGS("*set*"),
    XML("*xml*"),
    EXECUTABLE("*exe*")
    ;

    public final String txt;

    AbstractType(String txt){
        this.txt = txt;
    }
}
