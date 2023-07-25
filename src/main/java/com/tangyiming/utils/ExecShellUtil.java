package com.tangyiming.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecShellUtil {
    public static String execShell(String shell) {
        try {
            Process process = Runtime.getRuntime().exec(shell);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = stdInput.readLine()) != null) {
                output.append(line).append("\n");
            }
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
