package com.saharw.openfs.core.op.util;

import com.saharw.openfs.api.AbstractType;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * Created by Sahar on 06/21/2017.
 */
public class FilesUtil {

    public AbstractType getAbstractType(File file){
        AbstractType type = AbstractType.UNKNOWN;
        if(file != null && file.exists() && file.getName() != null && !file.getName().isEmpty()){
            String fileExt = FilenameUtils.getExtension(file.getName());
            switch (fileExt){

                // text files
                case "txt":{
                    type = AbstractType.FILE_TYPE_TXT;
                    break;
                }

                // image files
                case "jpg":{
                    type = AbstractType.FILE_TYPE_IMAGE;
                    break;
                }

                // video files
                case "avi":{
                    type = AbstractType.FILE_TYPE_VIDEO;
                    break;
                }

                // audio files
                case "mp3":{
                    type = AbstractType.FILE_TYPE_AUDIO;
                    break;
                }

                // TODO: other types... can be extended by user
                case "ini":
                case "cfg":{
                    type = AbstractType.CONFIG;
                    break;
                }
                case "pub":{
                    type = AbstractType.FILE_TYPE_SECURITY;
                    break;
                }
                case "settings":{
                    type = AbstractType.SETTINGS;
                    break;
                }
                case "exe":
                case "bat":{
                    type = AbstractType.EXECUTABLE;
                    break;
                }

            }

        }
        return type;
    }
}
