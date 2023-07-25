package com.tangyiming.requests.dto;

import com.tangyiming.data.DiffCoverageData;

import java.util.List;

public class ReportDataOfUTCPolicyReq {
    private String repoFullName;
    private Boolean jacocoDirExist;
    private Boolean surefireDirExist;
    private Boolean surefireReportExist;
    private Boolean aggregateReportDirExist;
    private Boolean diffReportDirExist;
    private List<DiffCoverageData> diffCoverageData;

    public ReportDataOfUTCPolicyReq(String repoFullName, Boolean jacocoDirExist, Boolean surefireDirExist, Boolean surefireReportExist, Boolean aggregateReportDirExist, Boolean diffReportDirExist, List<DiffCoverageData> diffCoverageData) {
        this.repoFullName = repoFullName;
        this.jacocoDirExist = jacocoDirExist;
        this.surefireDirExist = surefireDirExist;
        this.surefireReportExist = surefireReportExist;
        this.aggregateReportDirExist = aggregateReportDirExist;
        this.diffReportDirExist = diffReportDirExist;
        this.diffCoverageData = diffCoverageData;
    }

    public ReportDataOfUTCPolicyReq() {
    }

    public String getRepoFullName() {
        return repoFullName;
    }

    public void setRepoFullName(String repoFullName) {
        this.repoFullName = repoFullName;
    }

    public Boolean getJacocoDirExist() {
        return jacocoDirExist;
    }

    public void setJacocoDirExist(Boolean jacocoDirExist) {
        this.jacocoDirExist = jacocoDirExist;
    }

    public Boolean getSurefireDirExist() {
        return surefireDirExist;
    }

    public void setSurefireDirExist(Boolean surefireDirExist) {
        this.surefireDirExist = surefireDirExist;
    }

    public Boolean getSurefireReportExist() {
        return surefireReportExist;
    }

    public void setSurefireReportExist(Boolean surefireReportExist) {
        this.surefireReportExist = surefireReportExist;
    }

    public Boolean getAggregateReportDirExist() {
        return aggregateReportDirExist;
    }

    public void setAggregateReportDirExist(Boolean aggregateReportDirExist) {
        this.aggregateReportDirExist = aggregateReportDirExist;
    }

    public Boolean getDiffReportDirExist() {
        return diffReportDirExist;
    }

    public void setDiffReportDirExist(Boolean diffReportDirExist) {
        this.diffReportDirExist = diffReportDirExist;
    }

    public List<DiffCoverageData> getDiffCoverageData() {
        return diffCoverageData;
    }

    public void setDiffCoverageData(List<DiffCoverageData> diffCoverageData) {
        this.diffCoverageData = diffCoverageData;
    }
}
