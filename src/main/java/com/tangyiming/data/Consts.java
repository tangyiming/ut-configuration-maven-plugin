package com.tangyiming.data;

public class Consts {
    public final static String ORG_REPO_REGEX = "([\\w,\\-,\\_]+\\/[\\w,\\-,\\_]+)\\.git";
    public final static String POM_FILE_NAME = "pom.xml";
    public final static String PACKAGING_TYPE_POM = "pom";
    public final static String PACKAGING_TYPE_JAR = "jar";
    public final static String AGGREGATE_REPORT = "aggregate-report";
    public final static String DIFF_COVERAGE = "diffCoverage";
    public final static String JACOCO = "jacoco";
    public final static String JACOCO_AGGREGATE = "jacoco-aggregate";
    public final static String SUREFIRE_REPORTS = "surefire-reports";
    public final static String SUREFIRE_REPORT_FILE_NAME = "surefire-report.html";
    public final static String HOTFIX = "hotfix";
    public final static String SUREFIRE_PLUGIN_NAME = "org.apache.maven.plugins:maven-surefire-plugin";
    public final static String SUREFIRE_REPORT_PLUGIN_NAME = "org.apache.maven.plugins:maven-surefire-report-plugin";
    public final static String JACOCO_PLUGIN_NAME = "org.jacoco:jacoco-maven-plugin";
    public final static String DIFF_COVERAGE_PLUGIN_NAME = "com.github.surpsg:diff-coverage-maven-plugin";
    public final static String DIFF_FILE_NAME = "diff.txt";
    public final static String DIFF_COVERAGE_PLUGIN_CONFIG_XPATH = "/m:project/m:build/m:plugins/m:plugin[m:artifactId='diff-coverage-maven-plugin']/m:configuration";
    public final static String JACOCO_PLUGIN_EXECUTIONS_XPATH = "/m:project/m:build/m:plugins/m:plugin[m:artifactId='jacoco-maven-plugin']/m:executions";
    public final static String DIFF_COVERAGE_CSV_FILE_PATH = "/target/site/diffCoverage/diff-coverage.csv";

    // todo: replace with your own api url
    public final static String BASEURL = "http://xxx.com/api";
    public final static String EFFECT_DENY = "deny";
    public final static String EFFECT_WARN = "warn";
}
