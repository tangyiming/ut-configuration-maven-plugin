package com.tangyiming.utils;

import com.tangyiming.data.Consts;
import org.apache.maven.project.MavenProject;

import java.util.List;

public class ProjectUtil {
    public static Boolean isSingleModuleProject(MavenProject project) {
        List<String> modules = project.getModules();
        return isRootPom(project) && modules.size() == 0;
    }

    public static Boolean isAggregateReport(MavenProject project) {
        return project.getName().equals(Consts.AGGREGATE_REPORT);
    }

    public static Boolean isRootPom(MavenProject project) {
        return project.getParentFile() == null;
    }

    public static Boolean isAggregateReportModuleExits(MavenProject project) {
        List<String> modules = project.getModules();
        for (String module : modules) {
            if (module.trim().equals(Consts.AGGREGATE_REPORT)) {
                return true;
            }
        }
        return false;
    }
}
