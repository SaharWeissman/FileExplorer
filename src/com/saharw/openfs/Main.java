package com.saharw.openfs;

import com.saharw.openfs.api.AbstractFSDataItem;
import com.saharw.openfs.api.AbstractType;
import com.saharw.openfs.api.impl.JavaFileWrapper;
import com.saharw.openfs.app.CustomShell;
import com.saharw.openfs.core.op.AbstractFSTree;
import com.saharw.openfs.core.op.OperationType;
import com.saharw.openfs.core.op.util.FilesUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

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
    static Scanner sScanner;
    static CustomShell customShell;
    static FilesUtil sFilesUtil = new FilesUtil();
    static int sMaxDepthLevel = 5;
    static int sCurrDepthLevel = 0;
    private static String sRootDir = System.getProperty("user.home");
    private static boolean sIsRunning = false;

    public static void main(String[] args){
        System.out.println(WELCOME_MSG + verName + "(" + verCode + ")" + DEVELOPER_INFO);
        runMainShellLoop();
    }

    private static void runMainShellLoop() {
        sIsRunning = true;
        while(sIsRunning) {
            System.out.print(sRootDir + ">");
            sScanner = new Scanner(System.in);
            String cmd;
            while ((cmd = sScanner.nextLine()).trim() == null || cmd.isEmpty()) {
            }
            OperationType type = getOperation(cmd);
            if (type != null) {
                switch (type) {
                    case LIST_FILES: {
                        File dir = new File(System.getProperty("user.home"));
                        if (dir.exists() && dir.canRead() && dir.isDirectory()) {
                            File[] files = dir.listFiles();
                            Collection<AbstractFSDataItem> descendants = new ArrayList<>(files.length);
                            for (int i = 0; i < files.length; i++) {
                                descendants.add(JavaFileWrapper.createFromBaseType(files[i], sFilesUtil, null, sMaxDepthLevel, sCurrDepthLevel, true));
                            }
                            JavaFileWrapper root = new JavaFileWrapper(dir, AbstractType.DIRECTORY, dir.getName(), dir.getPath(), descendants, new HashSet<>());
                            customShell = new CustomShell(new AbstractFSTree(root), sMaxDepthLevel, sCurrDepthLevel, null);
                            System.out.println(customShell.listFiles());
                        } else {
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

    static OperationType getOperation(String line){
        OperationType type = null;
        switch (line){
            case "ls":{
                type = OperationType.LIST_FILES;
                break;
            }
            case "exit":{
                type = OperationType.EXIT;
                printMsg(GOODBYE_MSG);
                System.exit(STATUS_CODE_OK);
            }
            default:{
                break;
            }
        }
        return type;
    }

    private static void printMsg(String msg) {
        System.out.println(msg);
    }

    static void printErrMsg(String msg){
        System.err.println(msg);
    }
}
