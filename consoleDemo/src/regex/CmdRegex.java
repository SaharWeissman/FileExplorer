package regex;

import com.saharw.openfs.core.op.OperationType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Sahar on 06/23/2017.
 */
public class CmdRegex {
    private static volatile CmdRegex sInstance = null;
    public Map<OperationType, Pattern[]> regexes = null;

    public static CmdRegex getInstance(){
        if(sInstance == null){
            synchronized (CmdRegex.class){
                if(sInstance == null){
                    sInstance = new CmdRegex();
                }
            }
        }
        return sInstance;
    }

    public void load(){
        if(regexes == null){
            regexes = new HashMap<>();
            regexes.put(OperationType.EXIT, new Pattern[]{Pattern.compile("(exit)\\s+")});
            regexes.put(OperationType.LIST_FILES, new Pattern[]{Pattern.compile("(ls)(\\s+|(-(s|m|l))|[0-9])*")});
            regexes.put(OperationType.CHANGE_DIR, new Pattern[]{Pattern.compile("(cd)\\s+.*\\s?")});
        }
    }
}
