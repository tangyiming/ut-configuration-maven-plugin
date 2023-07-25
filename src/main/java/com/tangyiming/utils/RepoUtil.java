package com.tangyiming.utils;

import com.tangyiming.data.Consts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepoUtil {
    public static String getRepoFullName() {
        String origin = ExecShellUtil.execShell("git remote -v");
        if (origin.contains("origin")) {
            Pattern regex = Pattern.compile(Consts.ORG_REPO_REGEX);
            Matcher matcher = regex.matcher(origin.split("\n")[0]);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return "";
    }

    public static String getRepoBranch() {
        return ExecShellUtil.execShell("git rev-parse --abbrev-ref HEAD").trim();
    }

}
