package com.tangyiming.data;

public class XmlTemplates {
    public static final String SUREFIRE_PLUGIN_TEMPLATE = "\t<groupId>org.apache.maven.plugins</groupId>\n" +
            "\t<artifactId>maven-surefire-plugin</artifactId>\n" +
            "\t<version>%s</version>\n" +
            "\t<configuration>\n" +
            "\t\t<skip>false</skip>\n" +
            "\t\t<testFailureIgnore>true</testFailureIgnore>\n" +
            "\t</configuration>\n";

    public static final String SUREFIRE_REPORT_PLUGIN_TEMPLATE = "\t<groupId>org.apache.maven.plugins</groupId>\n" +
            "\t<artifactId>maven-surefire-report-plugin</artifactId>\n" +
            "\t<version>%s</version>\n" +
            "\t<executions>\n" +
            "\t\t<execution>\n" +
            "\t\t\t<id>generate-surefire-report</id>\n" +
            "\t\t\t<phase>test</phase>\n" +
            "\t\t\t<goals>\n" +
            "\t\t\t\t<goal>report</goal>\n" +
            "\t\t\t</goals>\n" +
            "\t\t</execution>\n" +
            "\t</executions>";

    public static final String JACOCO_PLUGIN_TEMPLATE = "\t<groupId>org.jacoco</groupId>\n" +
            "\t<artifactId>jacoco-maven-plugin</artifactId>\n" +
            "\t<version>%s</version>\n" +
            "\t<executions>\n" +
            "\t\t<execution>\n" +
            "\t\t\t<id>prepare-agent</id>\n" +
            "\t\t\t<goals>\n" +
            "\t\t\t\t<goal>prepare-agent</goal>\n" +
            "\t\t\t</goals>\n" +
            "\t\t</execution>\n" +
            "\t\t<execution>\n" +
            "\t\t\t<id>default-report</id>\n" +
            "\t\t\t<phase>prepare-package</phase>\n" +
            "\t\t\t<goals>\n" +
            "\t\t\t\t<goal>report</goal>\n" +
            "\t\t\t</goals>\n" +
            "\t\t\t<configuration>\n" +
            "\t\t\t\t<outputDirectory>target/site/jacoco</outputDirectory>\n" +
            "\t\t\t</configuration>\n" +
            "\t\t</execution>\n" +
            "\t</executions>";

    public static final String DIFF_COVERAGE_PLUGIN_TEMPLATE = "\t<groupId>com.github.surpsg</groupId>\n" +
            "\t<artifactId>diff-coverage-maven-plugin</artifactId>\n" +
            "\t<version>%s</version>\n" +
            "\t<configuration>\n" +
            "\t\t<diffSource>\n" +
            "\t\t\t<file>diff.txt</file>\n" +
            "\t\t</diffSource>\n" +
            "\t\t<dataFileIncludes>target/*.exec</dataFileIncludes>\n" +
            "\t</configuration>\n" +
            "\t<executions>\n" +
            "\t\t<execution>\n" +
            "\t\t\t<phase>prepare-package</phase>\n" +
            "\t\t\t<goals>\n" +
            "\t\t\t\t<goal>diffCoverage</goal>\n" +
            "\t\t\t</goals>\n" +
            "\t\t</execution>\n" +
            "\t</executions>";

    public static final String AGGREGATE_REPORT_POM_TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
            "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
            "    <modelVersion>4.0.0</modelVersion>\n" +
            "\t<parent>\n" +
            "\t\t<groupId>%s</groupId>\n" +
            "\t\t<artifactId>%s</artifactId>\n" +
            "\t\t<version>%s</version>\n" +
            "\t</parent>\n" +
            "\t<artifactId>aggregate-report</artifactId>\n" +
            "\t<version>0.0.1-SNAPSHOT</version>\n" +
            "\t<dependencies></dependencies>\n" +
            "\t<build>\n" +
            "\t\t<plugins>\n" +
            "\t\t\t<plugin>\n" +
            "\t\t\t\t<groupId>org.jacoco</groupId>\n" +
            "\t\t\t\t<artifactId>jacoco-maven-plugin</artifactId>\n" +
            "\t\t\t\t<version>%s</version>\n" +
            "\t\t\t\t<configuration>\n" +
            "\t\t\t\t\t<destFile>target/coverage-reports/custom-jacoco.exec</destFile>\n" +
            "\t\t\t\t\t<dataFile>target/coverage-reports/custom-jacoco.exec</dataFile>\n" +
            "\t\t\t\t</configuration>\n" +
            "\t\t\t\t<executions>\n" +
            "\t\t\t\t\t<execution>\n" +
            "\t\t\t\t\t\t<id>prepare</id>\n" +
            "\t\t\t\t\t\t<goals>\n" +
            "\t\t\t\t\t\t\t<goal>prepare-agent</goal>\n" +
            "\t\t\t\t\t\t</goals>\n" +
            "\t\t\t\t\t</execution>\n" +
            "\t\t\t\t\t<execution>\n" +
            "\t\t\t\t\t\t<id>report-aggregate</id>\n" +
            "\t\t\t\t\t\t<phase>prepare-package</phase>\n" +
            "\t\t\t\t\t\t<goals>\n" +
            "\t\t\t\t\t\t\t<goal>report-aggregate</goal>\n" +
            "\t\t\t\t\t\t</goals>\n" +
            "\t\t\t\t\t\t<configuration>\n" +
            "\t\t\t\t\t\t\t<dataFileIncludes>target/coverage-reports/custom-jacoco.exec</dataFileIncludes>\n" +
            "\t\t\t\t\t\t</configuration>\n" +
            "\t\t\t\t\t</execution>\n" +
            "\t\t\t\t</executions>\n" +
            "\t\t\t</plugin>\n" +
            "\t\t\t<plugin>\n" +
            "\t\t\t\t<groupId>com.github.surpsg</groupId>\n" +
            "\t\t\t\t<artifactId>diff-coverage-maven-plugin</artifactId>\n" +
            "\t\t\t\t<version>%s</version>\n" +
            "\t\t\t\t<configuration>\n" +
            "\t\t\t\t\t<diffSource>\n" +
            "\t\t\t\t\t\t<file>diff.txt</file>\n" +
            "\t\t\t\t\t</diffSource>\n" +
            "\t\t\t\t\t<dataFileIncludes>**/coverage-reports/custom-jacoco.exec</dataFileIncludes>\n" +
            "\t\t\t\t</configuration>\n" +
            "\t\t\t\t<executions>\n" +
            "\t\t\t\t\t<execution>\n" +
            "\t\t\t\t\t\t<phase>prepare-package</phase>\n" +
            "\t\t\t\t\t\t<goals>\n" +
            "\t\t\t\t\t\t\t<goal>diffCoverage</goal>\n" +
            "\t\t\t\t\t\t</goals>\n" +
            "\t\t\t\t\t</execution>\n" +
            "\t\t\t\t</executions>\n" +
            "\t\t\t</plugin>\n" +
            "\t\t</plugins>\n" +
            "\t</build>" +
            "</project>";

    public static final String JACOCO_RULE_TEMPLATE = "\t\t\t\t\t\t<element>%s</element>\n" +
            "\t\t\t\t\t\t<limits>\n" +
            "\t\t\t\t\t\t\t<limit>\n" +
            "\t\t\t\t\t\t\t\t<counter>%s</counter>\n" +
            "\t\t\t\t\t\t\t\t<value>%s</value>\n" +
            "\t\t\t\t\t\t\t\t<minimum>%s</minimum>\n" +
            "\t\t\t\t\t\t\t</limit>\n" +
            "\t\t\t\t\t\t</limits>\n";

    public static final String JACOCO_COVERAGE_CHECK_TEMPLATE = "\t\t\t<id>coverage-check</id>\n" +
            "\t\t\t<goals>\n" +
            "\t\t\t\t<goal>check</goal>\n" +
            "\t\t\t</goals>\n" +
            "\t\t\t<configuration>\n" +
            "\t\t\t\t<haltOnFailure>%s</haltOnFailure>\n" +
            "\t\t\t\t<rules>\n" +
            "\t\t\t\t</rules>\n" +
            "\t\t\t</configuration>\n";

    public static final String POM_DEPENDENCY_TEMPLATE = "<groupId>%s</groupId>\n" +
            "\t<artifactId>%s</artifactId>\n" +
            "\t<version>%s</version>\n";

    public static final String DIFF_COVERAGE_VIOLATIONS_TEMPLATE = "\t\t\t\t\t\t<failOnViolation>%b</failOnViolation>\n" +
            "\t\t\t\t\t\t<minCoverage>%f</minCoverage>\n" +
            "\t\t\t\t\t\t<minLines>%f</minLines>\n" +
            "\t\t\t\t\t\t<minBranches>%f</minBranches>\n" +
            "\t\t\t\t\t\t<minInstructions>%f</minInstructions>\n";
}
