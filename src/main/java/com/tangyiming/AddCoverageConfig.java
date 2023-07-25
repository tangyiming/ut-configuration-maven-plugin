package com.tangyiming;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.alibaba.fastjson.JSONObject;
import com.tangyiming.data.*;
import com.tangyiming.requests.Apis;
import com.tangyiming.requests.dto.GetRepoInfoOfUTCPolicyReq;
import com.tangyiming.requests.dto.RepoUTCPolicyDetail;
import com.tangyiming.utils.PomFileOperatorUtil;
import com.tangyiming.utils.ProjectUtil;
import com.tangyiming.utils.RepoUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Mojo(name = "add-coverage-config", defaultPhase = LifecyclePhase.COMPILE)
public class AddCoverageConfig extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(defaultValue = "3.1.0", readonly = true)
    String surefireVersion;

    @Parameter(defaultValue = "3.1.0", readonly = true)
    String surefireReportVersion;

    @Parameter(defaultValue = "0.8.10", readonly = true)
    String jacocoVersion;

    @Parameter(defaultValue = "0.3.2", readonly = true)
    String diffCoverageVersion;

    public void execute() {
        // todo config the rules
        RepoUTCPolicyDetail policyInfo = new RepoUTCPolicyDetail();
        JacocoCheckConfiguration jacocoCheckConfiguration = new JacocoCheckConfiguration();
        jacocoCheckConfiguration.setHaltOnFailure(true);
        List<JacocoCheckRule> jacocoCheckRules = new ArrayList<>();
        JacocoCheckRule jacocoCheckRule = new JacocoCheckRule();
        jacocoCheckRule.setElement("CLASS");
        jacocoCheckRule.setCounter("INSTRUCTION");
        jacocoCheckRule.setValue("COVEREDRATIO");
        jacocoCheckRule.setMinimum((float)0.8);
        jacocoCheckRules.add(jacocoCheckRule);
        jacocoCheckConfiguration.setJacocoCheckRules(jacocoCheckRules);
        policyInfo.setJacocoCheckConfiguration(jacocoCheckConfiguration);
        DiffCoverageViolations diffCoverageViolations = new DiffCoverageViolations();
        diffCoverageViolations.setFailOnViolation(true);
        diffCoverageViolations.setMinCoverage((float)0.8);
        diffCoverageViolations.setMinLines((float)0.10);
        diffCoverageViolations.setMinBranches((float)0.10);
        diffCoverageViolations.setMinInstructions((float)0.10);
        policyInfo.setDiffCoverageViolations(diffCoverageViolations);

        // todo
        // you can also get the policy info from the server side if you want to make it a common configuration for some target projects
        // RepoUTCPolicyDetail policyInfo = getPolicy();

        String packaging = project.getPackaging();
        if (packaging.equals(Consts.PACKAGING_TYPE_JAR) && !project.getName().equals(Consts.AGGREGATE_REPORT)) {
            checkThenAddNodeToPom("/m:project/m:build", "/m:project", "build");
            checkThenAddNodeToPom("/m:project/m:build/m:plugins", "/m:project/m:build", "plugins");

            List<String> buildPlugins = PomFileOperatorUtil.getBuildPlugins(project.getFile());
            boolean needAddSurefire = true;
            boolean needAddSurefireReport = true;
            boolean needAddJacoco = true;
            boolean needAddDiffCoverage = true;
            boolean needAddDiffEmptyTxt = true;
            for (String plugin : buildPlugins) {
                if (plugin.trim().equals(Consts.SUREFIRE_PLUGIN_NAME)) {
                    needAddSurefire = false;
                }
                if (plugin.trim().equals(Consts.SUREFIRE_REPORT_PLUGIN_NAME)) {
                    needAddSurefireReport = false;
                }
                if (plugin.trim().equals(Consts.JACOCO_PLUGIN_NAME)) {
                    needAddJacoco = false;
                }
                if (plugin.trim().equals(Consts.DIFF_COVERAGE_PLUGIN_NAME)) {
                    needAddDiffCoverage = false;
                    File diffFile = new File(Consts.DIFF_FILE_NAME);
                    if (diffFile.exists()) {
                        needAddDiffEmptyTxt = false;
                    }
                }
            }
            if (needAddSurefire) {
                addBuildPlugin(XmlTemplates.SUREFIRE_PLUGIN_TEMPLATE, surefireVersion);
            }
            if (needAddSurefireReport) {
                addBuildPlugin(XmlTemplates.SUREFIRE_REPORT_PLUGIN_TEMPLATE, surefireReportVersion);
            }
            if (needAddJacoco) {
                addBuildPlugin(XmlTemplates.JACOCO_PLUGIN_TEMPLATE, jacocoVersion);
            }
            if (ProjectUtil.isSingleModuleProject(project) && needAddDiffCoverage) {
                addBuildPlugin(XmlTemplates.DIFF_COVERAGE_PLUGIN_TEMPLATE, diffCoverageVersion);
            }
            if (ProjectUtil.isSingleModuleProject(project) && needAddDiffEmptyTxt) {
                File file = new File(Consts.DIFF_FILE_NAME);
                createDiffFile(file);
            }
            addOrOverrideJacocoCoverageRules(project.getFile(), policyInfo.getJacocoCheckConfiguration());
            if (ProjectUtil.isSingleModuleProject(project)) {
                addOrOverrideDiffCoverageViolationRules(project.getFile(), policyInfo.getDiffCoverageViolations());
            }
        }

        if (packaging.equals(Consts.PACKAGING_TYPE_POM) && ProjectUtil.isRootPom(project)) {
            File file = null;
            if (!ProjectUtil.isAggregateReportModuleExits(project)) {
                String groupId = project.getGroupId();
                String artifactId = project.getArtifactId();
                String version = project.getVersion();
                String filePath = project.getBasedir() + File.separator + Consts.AGGREGATE_REPORT;
                String content = String.format(XmlTemplates.AGGREGATE_REPORT_POM_TEMPLATE, groupId, artifactId, version, jacocoVersion, diffCoverageVersion);
                file = PomFileOperatorUtil.createPomFile(filePath, content);
            } else {
                file = new File(Consts.AGGREGATE_REPORT + File.separator + Consts.POM_FILE_NAME);
            }
            boolean checkReportModuleExist = PomFileOperatorUtil.checkNodeExits(project.getFile(), "/m:project/m:modules[m:module='aggregate-report']");
            if (!checkReportModuleExist) {
                PomFileOperatorUtil.addContentToPomNode(project.getFile().getPath(), "/m:project/m:modules", "module", Consts.AGGREGATE_REPORT);
            }
            checkAndAddDepsToReportModulePom(file);
            addOrOverrideJacocoCoverageRules(file, policyInfo.getJacocoCheckConfiguration());
            addOrOverrideDiffCoverageViolationRules(file, policyInfo.getDiffCoverageViolations());
            File diffFile = new File(Consts.AGGREGATE_REPORT + File.separator + Consts.DIFF_FILE_NAME);
            createDiffFile(diffFile);
        }
    }

    private void checkAndAddDepsToReportModulePom(File file) {
        List<MavenProject> collectedProjects = project.getCollectedProjects();
        for (MavenProject collectedProject : collectedProjects) {
            if (collectedProject.getPackaging().equals(Consts.PACKAGING_TYPE_JAR) && !collectedProject.getName().equals(Consts.AGGREGATE_REPORT)) {
                String dep = String.format(XmlTemplates.POM_DEPENDENCY_TEMPLATE, collectedProject.getGroupId(), collectedProject.getArtifactId(), collectedProject.getVersion());
                boolean checkDepExist = PomFileOperatorUtil.checkNodeExits(file, "/m:project/m:dependencies/m:dependency[m:artifactId='" + collectedProject.getArtifactId() + "']");
                if (!checkDepExist) {
                    PomFileOperatorUtil.addContentToPomNode(file.getPath(), "/m:project/m:dependencies", "dependency", dep);
                }
            }
        }
    }

    private void createDiffFile(File file) {
        if (!file.exists()) {
            try {
                boolean res = file.createNewFile();
                if (!res) {
                    getLog().error("create diff.txt error");
                }
            } catch (IOException e) {
                getLog().error("create diff.txt error:" + e.getMessage());
            }
        }
    }

    private void addBuildPlugin(String content, String version) {
        content = String.format(content, version);
        PomFileOperatorUtil.addContentToPomNode(project.getFile().getPath(), "/m:project/m:build/m:plugins", "plugin", content);
    }

    private void checkThenAddNodeToPom(String checkXpath, String targetXpath, String newNodeName) {
        boolean exits = PomFileOperatorUtil.checkNodeExits(project.getFile(), checkXpath);
        if (!exits) {
            PomFileOperatorUtil.addContentToPomNode(project.getFile().getPath(), targetXpath, newNodeName, "");
        }
    }

    private RepoUTCPolicyDetail getPolicy() {
        String repo = RepoUtil.getRepoFullName();
        if (repo.equals("")) {
            return null;
        }
        String moduleName = project.getName();
        // String appPath = getAppPath(moduleName);
        if (!moduleName.equals(Consts.AGGREGATE_REPORT)) {
            GetRepoInfoOfUTCPolicyReq req = new GetRepoInfoOfUTCPolicyReq();
            req.setRepoFullName(repo);
            ApiResponse<RepoUTCPolicyDetail> apiResponse = Apis.getRepoInfoOfUTCPolicy(req);
            if (null != apiResponse && apiResponse.getCode() == 0) {
                return JSONObject.parseObject(JSONObject.toJSONString(apiResponse.getData()), RepoUTCPolicyDetail.class);
            }
        }
        return null;
    }

    private String getAppPath(String moduleName) {
        StringBuilder appPath = new StringBuilder(moduleName);
        if (ProjectUtil.isRootPom(project)) {
            return "";
        } else {
            // if multi-module project, the appPath maybe like: module1/module2/module3
            MavenProject parent = project;
            for (int i = 0; i < 100; i++) { // any count is ok, just make sure we can iterate all the levels
                parent = parent.getParent();
                // should get the parent file, otherwise we may add the project name to the appPath
                if (parent.getParentFile() != null) {
                    appPath.insert(0, parent.getName() + "/");
                } else {
                    break;
                }
            }
        }
        return appPath.toString();
    }

    private void addOrOverrideJacocoCoverageRules(File file, JacocoCheckConfiguration jacocoCheckConfiguration) {
        if (jacocoCheckConfiguration != null && jacocoCheckConfiguration.getJacocoCheckRules() != null && jacocoCheckConfiguration.getJacocoCheckRules().size() > 0) {
            boolean coverageCheckExist = PomFileOperatorUtil.checkNodeExits(file, Consts.JACOCO_PLUGIN_EXECUTIONS_XPATH + "/m:execution[m:id='coverage-check']");
            if (coverageCheckExist) {
                PomFileOperatorUtil.updateContentToPomNode(file.getPath(), Consts.JACOCO_PLUGIN_EXECUTIONS_XPATH + "/m:execution[m:id='coverage-check']/m:configuration/m:haltOnFailure", jacocoCheckConfiguration.getHaltOnFailure().toString());
                for (JacocoCheckRule jacocoCheckRule : jacocoCheckConfiguration.getJacocoCheckRules()) {
                    boolean ruleExist = PomFileOperatorUtil.checkNodeExits(file, Consts.JACOCO_PLUGIN_EXECUTIONS_XPATH + "/m:execution[m:id='coverage-check']/m:configuration/m:rules/m:rule[m:element='" + jacocoCheckRule.getElement() + "']");
                    if (ruleExist) {
                        PomFileOperatorUtil.updateContentToPomNode(file.getPath(), Consts.JACOCO_PLUGIN_EXECUTIONS_XPATH + "/m:execution[m:id='coverage-check']/m:configuration/m:rules/m:rule[m:element='" + jacocoCheckRule.getElement() + "']/m:limits/m:limit/m:counter", jacocoCheckRule.getCounter());
                        PomFileOperatorUtil.updateContentToPomNode(file.getPath(), Consts.JACOCO_PLUGIN_EXECUTIONS_XPATH + "/m:execution[m:id='coverage-check']/m:configuration/m:rules/m:rule[m:element='" + jacocoCheckRule.getElement() + "']/m:limits/m:limit/m:value", jacocoCheckRule.getValue());
                        PomFileOperatorUtil.updateContentToPomNode(file.getPath(), Consts.JACOCO_PLUGIN_EXECUTIONS_XPATH + "/m:execution[m:id='coverage-check']/m:configuration/m:rules/m:rule[m:element='" + jacocoCheckRule.getElement() + "']/m:limits/m:limit/m:minimum", jacocoCheckRule.getMinimum().toString());
                    } else {
                        String ruleContent = String.format(XmlTemplates.JACOCO_RULE_TEMPLATE, jacocoCheckRule.getElement(), jacocoCheckRule.getCounter(), jacocoCheckRule.getValue(), jacocoCheckRule.getMinimum());
                        PomFileOperatorUtil.addContentToPomNode(file.getPath(), Consts.JACOCO_PLUGIN_EXECUTIONS_XPATH + "/m:execution[m:id='coverage-check']/m:configuration/m:rules", "rule", ruleContent);
                    }
                }
            } else {
                String checkGoal = String.format(XmlTemplates.JACOCO_COVERAGE_CHECK_TEMPLATE, jacocoCheckConfiguration.getHaltOnFailure());
                PomFileOperatorUtil.addContentToPomNode(file.getPath(), Consts.JACOCO_PLUGIN_EXECUTIONS_XPATH, "execution", checkGoal);
                for (JacocoCheckRule jacocoCheckRule : jacocoCheckConfiguration.getJacocoCheckRules()) {
                    String rule = String.format(XmlTemplates.JACOCO_RULE_TEMPLATE, jacocoCheckRule.getElement(), jacocoCheckRule.getCounter(), jacocoCheckRule.getValue(), jacocoCheckRule.getMinimum());
                    PomFileOperatorUtil.addContentToPomNode(file.getPath(), Consts.JACOCO_PLUGIN_EXECUTIONS_XPATH + "/m:execution[m:id='coverage-check']/m:configuration/m:rules", "rule", rule);
                }
            }
        }
    }

    private void addOrOverrideDiffCoverageViolationRules(File file, DiffCoverageViolations diffCoverageViolations) {
        if (diffCoverageViolations != null) {
            boolean violationExist = PomFileOperatorUtil.checkNodeExits(file, Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations");
            if (violationExist) {
                boolean failOnViolationExist = PomFileOperatorUtil.checkNodeExits(file, Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations/m:failOnViolation");
                if (failOnViolationExist) {
                    PomFileOperatorUtil.updateContentToPomNode(file.getPath(), Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations/m:failOnViolation", diffCoverageViolations.getFailOnViolation().toString());
                } else {
                    PomFileOperatorUtil.addContentToPomNode(file.getPath(), Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations", "failOnViolation", diffCoverageViolations.getFailOnViolation().toString());
                }

                boolean minCoverageExist = PomFileOperatorUtil.checkNodeExits(file, Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations/m:minCoverage");
                if (minCoverageExist) {
                    PomFileOperatorUtil.updateContentToPomNode(file.getPath(), Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations/m:minCoverage", diffCoverageViolations.getMinCoverage().toString());
                } else {
                    PomFileOperatorUtil.addContentToPomNode(file.getPath(), Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations", "minCoverage", diffCoverageViolations.getMinCoverage().toString());
                }

                boolean minLinesExist = PomFileOperatorUtil.checkNodeExits(file, Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations/m:minLines");
                if (minLinesExist) {
                    PomFileOperatorUtil.updateContentToPomNode(file.getPath(), Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations/m:minLines", diffCoverageViolations.getMinLines().toString());
                } else {
                    PomFileOperatorUtil.addContentToPomNode(file.getPath(), Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations", "minLines", diffCoverageViolations.getMinLines().toString());
                }

                boolean minBranchesExist = PomFileOperatorUtil.checkNodeExits(file, Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations/m:minBranches");
                if (minBranchesExist) {
                    PomFileOperatorUtil.updateContentToPomNode(file.getPath(), Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations/m:minBranches", diffCoverageViolations.getMinBranches().toString());
                } else {
                    PomFileOperatorUtil.addContentToPomNode(file.getPath(), Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations", "minBranches", diffCoverageViolations.getMinBranches().toString());
                }

                boolean minInstructionsExist = PomFileOperatorUtil.checkNodeExits(file, Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations/m:minInstructions");
                if (minInstructionsExist) {
                    PomFileOperatorUtil.updateContentToPomNode(file.getPath(), Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations/m:minInstructions", diffCoverageViolations.getMinInstructions().toString());
                } else {
                    PomFileOperatorUtil.addContentToPomNode(file.getPath(), Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH + "/m:violations", "minInstructions", diffCoverageViolations.getMinInstructions().toString());
                }
            } else {
                String content = String.format(XmlTemplates.DIFF_COVERAGE_VIOLATIONS_TEMPLATE, diffCoverageViolations.getFailOnViolation(), diffCoverageViolations.getMinCoverage(), diffCoverageViolations.getMinLines(), diffCoverageViolations.getMinBranches(), diffCoverageViolations.getMinInstructions());
                PomFileOperatorUtil.addContentToPomNode(file.getPath(), Consts.DIFF_COVERAGE_PLUGIN_CONFIG_XPATH, "violations", content);
            }
        }
    }
}
