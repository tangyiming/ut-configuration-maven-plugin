package com.tangyiming.requests.dto;

import com.tangyiming.data.JacocoCheckConfiguration;
import com.tangyiming.data.DiffCoverageViolations;

public class RepoUTCPolicyDetail {
    private JacocoCheckConfiguration jacocoCheckConfiguration;
    private DiffCoverageViolations diffCoverageViolations;

    public RepoUTCPolicyDetail() {
    }

    public RepoUTCPolicyDetail(JacocoCheckConfiguration jacocoCheckConfiguration, DiffCoverageViolations diffCoverageViolations) {
        this.jacocoCheckConfiguration = jacocoCheckConfiguration;
        this.diffCoverageViolations = diffCoverageViolations;
    }

    public JacocoCheckConfiguration getJacocoCheckConfiguration() {
        return jacocoCheckConfiguration;
    }

    public void setJacocoCheckConfiguration(JacocoCheckConfiguration jacocoCheckConfiguration) {
        this.jacocoCheckConfiguration = jacocoCheckConfiguration;
    }

    public DiffCoverageViolations getDiffCoverageViolations() {
        return diffCoverageViolations;
    }

    public void setDiffCoverageViolations(DiffCoverageViolations diffCoverageViolations) {
        this.diffCoverageViolations = diffCoverageViolations;
    }
}
