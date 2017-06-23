import com.saharw.openfs.api.AbstractFSDataItem;
import com.saharw.openfs.api.AbstractType;
import com.saharw.openfs.api.impl.JavaFileWrapper;
import com.saharw.openfs.app.CustomShell;
import com.saharw.openfs.core.op.AbstractFSTree;
import com.saharw.openfs.core.op.OperationType;
import com.saharw.openfs.core.op.util.FilesUtil;
import com.saharw.openfs.core.verbose.VerboseLevel;
import regex.CmdRegex;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sahar on 06/22/2017.
 */
public class Main {
    private static final String WELCOME_MSG = "Welcome to OpenFS - v";
    private static final String verName = "1.00.00";
    private static final int verCode = 1;
    private static final String INVALID_CMD = "Error - Invalid cmd: ";
    private static final String EMPTY_DIR = "-empty-";
    private static final int STATUS_CODE_OK = 0;
    private static final String GIT_URL = "https://github.com/ssdd1886/FileExplorer";
    private static final String DEVELOPER_INFO = " - Developed by Sahar Weissman (" + GIT_URL + ")";
    private static final String GOODBYE_MSG = "Thank you for using OpenFS v" +verName + "(" + verCode + ") for Java console! code is available at: " + GIT_URL + "\nCome Back soon!:)";
    private static final java.lang.String PARAMS_DELIMITER = "\\s+";
    private static final String DIR_SEPERATOR = "\\";
    private static final int PARAMS_LENGTH_CD = 1;
    static Scanner sScanner;
    static CustomShell customShell;
    static FilesUtil sFilesUtil = new FilesUtil();
    static int sMaxDepthLevel = 5;
    static int sCurrDepthLevel = 0;
    private static String sDir = System.getProperty("user.home");
    private static boolean sIsRunning = false;
    private static final String CURR_DIR_SYMBOL = "\\.";
    private static VerboseLevel sVerboseLevel = VerboseLevel.MEDIUM;
    private static boolean sChangedDir = false;

    public static void main(String[] args){
        System.out.println(WELCOME_MSG + verName + "(" + verCode + ")" + DEVELOPER_INFO);
        runMainShellLoop();
    }

    private static void runMainShellLoop() {
        sIsRunning = true;
        CmdRegex.getInstance().load();
        while(sIsRunning) {
            System.out.print(sDir.replace("\\\\","\\") + ">");
            sScanner = new Scanner(System.in);
            String cmd;
            while ((cmd = sScanner.nextLine()).trim() == null || cmd.isEmpty()) {
            }
            OperationType type = getOperationAndParams(cmd);
            if (type != null) {
                switch (type) {
                    case LIST_FILES: {
                        String dirPathEscaped = sDir.replace("\\", "\\\\");
                        File dir = new File(dirPathEscaped);
                        if (dir.exists() && dir.canRead() && dir.isDirectory()) {
                            File[] files = dir.listFiles();
                            Collection<AbstractFSDataItem> descendants = new ArrayList<>(files.length);
                            for (int i = 0; i < files.length; i++) {
                                descendants.add(JavaFileWrapper.createFromBaseType(files[i], sFilesUtil, null, sMaxDepthLevel, sCurrDepthLevel, true));
                            }
                            JavaFileWrapper root = new JavaFileWrapper(dir, AbstractType.DIRECTORY, dir.getName(), dir.getPath(), descendants, new HashSet<>());
                            customShell = new CustomShell(new AbstractFSTree(root), sMaxDepthLevel, sCurrDepthLevel, null);


                            System.out.println(customShell.listFiles(sVerboseLevel));
                        } else {
                            System.out.println(EMPTY_DIR);
                        }
                        break;
                    }
                    case CHANGE_DIR:{
                        File dir = new File(sDir);
                        if (dir.exists() && dir.canRead() && dir.isDirectory()) {
                            File[] files = dir.listFiles();
                            Collection<AbstractFSDataItem> descendants = new ArrayList<>(files.length);
                            for (int i = 0; i < files.length; i++) {
                                descendants.add(JavaFileWrapper.createFromBaseType(files[i], sFilesUtil, null, sMaxDepthLevel, sCurrDepthLevel, true));
                            }
                            JavaFileWrapper root = new JavaFileWrapper(dir, AbstractType.DIRECTORY, dir.getName(), dir.getPath(), descendants, new HashSet<>());
                            customShell = new CustomShell(new AbstractFSTree(root), sMaxDepthLevel, sCurrDepthLevel, null);
                            System.out.println(customShell.listFiles(sVerboseLevel));
                        }else {
                            System.out.println(EMPTY_DIR);
                        }
                        break;
                    }
                    default: {
                        printErrMsg(INVALID_CMD + "\t" + cmd + "\n");
                    }
                }
            } else {
                printErrMsg(INVALID_CMD + "\t" + cmd + "\n");
            }
        }
    }

    static OperationType getOperationAndParams(String line){
        OperationType type = null;

        // replace with regex recognition
        if(line != null && !line.trim().isEmpty()){
            // ls
            if(line.startsWith("ls")){
                Pattern[] patterns = CmdRegex.getInstance().regexes.get(OperationType.LIST_FILES);
                if(patterns != null && patterns.length > 0){
                    for(int i = 0; i < patterns.length; i++){
                        Pattern curr = patterns[i];
                        Matcher matcher = curr.matcher(line);
                        if(matcher.find()){
                            if(matcher.groupCount() > 1){
                                handleListFilesParams(matcher.group(0));
                                type = OperationType.LIST_FILES;
                            }else if(matcher.groupCount() == 1){ // no extra params
                                type = OperationType.LIST_FILES;
                            }
                        }
                    }
                }
            }

            // cd
            else if(line.startsWith("cd")){
                Pattern[] patterns = CmdRegex.getInstance().regexes.get(OperationType.CHANGE_DIR);
                if(patterns != null && patterns.length > 0){
                    for(int i = 0; i < patterns.length; i++){
                        Pattern curr = patterns[i];
                        Matcher matcher = curr.matcher(line);
                        if(matcher.find()){
                            if(matcher.groupCount() == 1){
                                handleChangeDirParams(matcher.group(0));
                                sChangedDir = true;
                                type = OperationType.CHANGE_DIR;
                            }
                        }
                    }
                }
            }

            //exit
            else if(line.startsWith("exit")){
                Pattern[] patterns = CmdRegex.getInstance().regexes.get(OperationType.EXIT);
                if(patterns != null && patterns.length > 0){
                    for(int i = 0; i < patterns.length; i++){
                        Pattern curr = patterns[i];
                        Matcher matcher = curr.matcher(line);
                        if(matcher.find()){
                            type = OperationType.EXIT;
                        }
                    }
                }
            }
        }

        return type;
    }

    private static void handleChangeDirParams(String line) {
        if(line != null && !line.isEmpty()){
            String[] split = line.split("\\s+");
            if(split.length > 1){
                String param = split[1];
                // 1 parameter
                if(param.startsWith(".")){
                    String currFolder = sDir;
                    if(line.contains("\\")) {
                        sDir = currFolder + "\\" + line.substring(line.indexOf("\\")).replace("\\\\", "\\");
                    }else{
                        sDir = currFolder;
                    }
                }else{
                    sDir = line.replace("cd", "").trim().replace("\\\\", "\\");
                }
            }
        }
    }

    private static void handleListFilesParams(String line) {
        if(line != null && !line.isEmpty()){
            String[] split = line.split("\\s+");
            if(split.length == 2){
                String param= split[1];

                // 1 parameter
                if(param.equals("-s")){
                    sVerboseLevel = VerboseLevel.SHORT;
                }else if(param.equals("-m")){
                    sVerboseLevel = VerboseLevel.MEDIUM;
                }else if (param.equals("-l")){
                    sVerboseLevel = VerboseLevel.LONG;
                }
            }
        }
    }

    private static boolean checkParamsValid(OperationType type, int paramsLength) {
        boolean isParamsLengthValid = false;
        switch (type){
            case CHANGE_DIR:{
                isParamsLengthValid = (OperationType.CHANGE_DIR.numParams.contains(paramsLength));
                break;
            }
            default:{
                break;
            }
        }
        return isParamsLengthValid;
    }

    private static void printMsg(String msg) {
        System.out.println(msg);
    }

    static void printErrMsg(String msg){
        System.err.println(msg);
    }
}
