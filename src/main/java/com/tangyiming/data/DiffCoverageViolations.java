package com.tangyiming.data;

public class DiffCoverageViolations {
    private Boolean failOnViolation;
    private Float minCoverage;
    private Float minLines;
    private Float minBranches;
    private Float minInstructions;

    public DiffCoverageViolations() {
    }

    public DiffCoverageViolations(Boolean failOnViolation, Float minCoverage, Float minLines, Float minBranches, Float minInstructions) {
        this.failOnViolation = failOnViolation;
        this.minCoverage = minCoverage;
        this.minLines = minLines;
        this.minBranches = minBranches;
        this.minInstructions = minInstructions;
    }


    public Boolean getFailOnViolation() {
        return failOnViolation;
    }

    public void setFailOnViolation(Boolean failOnViolation) {
        this.failOnViolation = failOnViolation;
    }

    public Float getMinCoverage() {
        return minCoverage;
    }

    public void setMinCoverage(Float minCoverage) {
        this.minCoverage = minCoverage;
    }

    public Float getMinLines() {
        return minLines;
    }

    public void setMinLines(Float minLines) {
        this.minLines = minLines;
    }

    public Float getMinBranches() {
        return minBranches;
    }

    public void setMinBranches(Float minBranches) {
        this.minBranches = minBranches;
    }

    public Float getMinInstructions() {
        return minInstructions;
    }

    public void setMinInstructions(Float minInstructions) {
        this.minInstructions = minInstructions;
    }

    @Override
    public String toString() {
        return "DiffCoverageViolations{" +
                "failOnViolation=" + failOnViolation +
                ", minCoverage=" + minCoverage +
                ", minLines=" + minLines +
                ", minBranches=" + minBranches +
                ", minInstructions=" + minInstructions +
                '}';
    }
}
