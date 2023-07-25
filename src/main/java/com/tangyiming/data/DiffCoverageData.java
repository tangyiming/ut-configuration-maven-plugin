package com.tangyiming.data;

public class DiffCoverageData {
    private String group;
    private String packageName;
    private String className;
    private int instructionMissed;
    private int instructionCovered;
    private int branchMissed;
    private int branchCovered;
    private int lineMissed;
    private int lineCovered;
    private int complexityMissed;
    private int complexityCovered;
    private int methodMissed;
    private int methodCovered;

    public DiffCoverageData() {
    }

    public DiffCoverageData(String group, String packageName, String className, int instructionMissed, int instructionCovered, int branchMissed, int branchCovered, int lineMissed, int lineCovered, int complexityMissed, int complexityCovered, int methodMissed, int methodCovered) {
        this.group = group;
        this.packageName = packageName;
        this.className = className;
        this.instructionMissed = instructionMissed;
        this.instructionCovered = instructionCovered;
        this.branchMissed = branchMissed;
        this.branchCovered = branchCovered;
        this.lineMissed = lineMissed;
        this.lineCovered = lineCovered;
        this.complexityMissed = complexityMissed;
        this.complexityCovered = complexityCovered;
        this.methodMissed = methodMissed;
        this.methodCovered = methodCovered;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getInstructionMissed() {
        return instructionMissed;
    }

    public void setInstructionMissed(int instructionMissed) {
        this.instructionMissed = instructionMissed;
    }

    public int getInstructionCovered() {
        return instructionCovered;
    }

    public void setInstructionCovered(int instructionCovered) {
        this.instructionCovered = instructionCovered;
    }

    public int getBranchMissed() {
        return branchMissed;
    }

    public void setBranchMissed(int branchMissed) {
        this.branchMissed = branchMissed;
    }

    public int getBranchCovered() {
        return branchCovered;
    }

    public void setBranchCovered(int branchCovered) {
        this.branchCovered = branchCovered;
    }

    public int getLineMissed() {
        return lineMissed;
    }

    public void setLineMissed(int lineMissed) {
        this.lineMissed = lineMissed;
    }

    public int getLineCovered() {
        return lineCovered;
    }

    public void setLineCovered(int lineCovered) {
        this.lineCovered = lineCovered;
    }

    public int getComplexityMissed() {
        return complexityMissed;
    }

    public void setComplexityMissed(int complexityMissed) {
        this.complexityMissed = complexityMissed;
    }

    public int getComplexityCovered() {
        return complexityCovered;
    }

    public void setComplexityCovered(int complexityCovered) {
        this.complexityCovered = complexityCovered;
    }

    public int getMethodMissed() {
        return methodMissed;
    }

    public void setMethodMissed(int methodMissed) {
        this.methodMissed = methodMissed;
    }

    public int getMethodCovered() {
        return methodCovered;
    }

    public void setMethodCovered(int methodCovered) {
        this.methodCovered = methodCovered;
    }
}
