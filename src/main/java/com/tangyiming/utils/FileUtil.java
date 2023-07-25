package com.tangyiming.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<File> findDirByName(File baseDir, String dirName, List<File> res) {
        File[] files = baseDir.listFiles();
        if (files == null) {
            return res;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                if (file.getName().equals(dirName)) {
                    res.add(file);
                } else {
                    findDirByName(file, dirName, res);
                }
            }
        }
        return res;
    }

    public static List<File> findFileByName(File baseDir, String fileName, List<File> res) {
        File[] files = baseDir.listFiles();
        if (files == null) {
            return res;
        }
        for (File file : files) {
            if (file.isFile()) {
                if (file.getName().equals(fileName)) {
                    res.add(file);
                }
            } else {
                findFileByName(file, fileName, res);
            }
        }
        return res;
    }

    public static Boolean isTargetDirExist(File targetDir, String targetName) {
        boolean targetExist = false;
        List<File> files = new ArrayList<>();
        FileUtil.findDirByName(targetDir, targetName, files);
        if (files.size() > 0) {
            targetExist = true;
        }
        return targetExist;
    }

    public static Boolean isTargetFileExist(File targetDir, String targetName) {
        boolean targetExist = false;
        List<File> files = new ArrayList<>();
        FileUtil.findFileByName(targetDir, targetName, files);
        if (files.size() > 0) {
            targetExist = true;
        }
        return targetExist;
    }
}
