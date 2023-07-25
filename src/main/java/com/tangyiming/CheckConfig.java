package com.tangyiming;

import com.tangyiming.data.Consts;
import com.tangyiming.requests.dto.ReportDataOfUTCPolicyReq;
import com.tangyiming.utils.FileUtil;
import com.tangyiming.utils.ProjectUtil;
import com.tangyiming.utils.RepoUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;

@Mojo(name = "check-config", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class CheckConfig extends AbstractMojo {
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    public void execute() throws MojoFailureException {
        String path = project.getBasedir().getAbsolutePath() + File.separator + "target";
        File targetDir = new File(path);
        if (!targetDir.exists()) {
            return;
        }

        if (project.getPackaging().equals(Consts.PACKAGING_TYPE_POM)) {
            return;
        }

        ReportDataOfUTCPolicyReq reportData = new ReportDataOfUTCPolicyReq();
        reportData.setRepoFullName(RepoUtil.getRepoFullName());

        if (ProjectUtil.isSingleModuleProject(project)) {
            boolean jacocoDirExist = FileUtil.isTargetDirExist(targetDir, Consts.JACOCO);
            boolean surefireDirExist = FileUtil.isTargetDirExist(targetDir, Consts.SUREFIRE_REPORTS);
            boolean surefireReportExist = FileUtil.isTargetFileExist(targetDir, Consts.SUREFIRE_REPORT_FILE_NAME);
            boolean diffReportDirExist = FileUtil.isTargetDirExist(targetDir, Consts.DIFF_COVERAGE);
            if (!jacocoDirExist || !surefireDirExist || !surefireReportExist || !diffReportDirExist) {
                getLog().warn("check-config: " + project.getName() + " is not configured correctly.");
            }
        } else {
            if (ProjectUtil.isAggregateReport(project)) {
                boolean aggregateReportDirExist = FileUtil.isTargetDirExist(targetDir, Consts.JACOCO_AGGREGATE);
                boolean diffReportDirExist = FileUtil.isTargetDirExist(targetDir, Consts.DIFF_COVERAGE);
                if (!aggregateReportDirExist || !diffReportDirExist) {
                    getLog().warn("check-config: " + project.getName() + " is not configured correctly.");
                }
            } else {
                boolean jacocoDirExist = FileUtil.isTargetDirExist(targetDir, Consts.JACOCO);
                boolean surefireDirExist = FileUtil.isTargetDirExist(targetDir, Consts.SUREFIRE_REPORTS);
                boolean surefireReportExist = FileUtil.isTargetFileExist(targetDir, Consts.SUREFIRE_REPORT_FILE_NAME);
                if (!jacocoDirExist || !surefireDirExist || !surefireReportExist) {
                    getLog().warn("check-config: " + project.getName() + " is not configured correctly.");
                }
            }
        }
    }
}