package com.tangyiming.data;

import java.util.List;

public class JacocoCheckConfiguration {
    private Boolean haltOnFailure;
    private List<JacocoCheckRule> jacocoCheckRules;

    public JacocoCheckConfiguration() {
    }

    public JacocoCheckConfiguration(Boolean haltOnFailure, List<JacocoCheckRule> jacocoCheckRules) {
        this.haltOnFailure = haltOnFailure;
        this.jacocoCheckRules = jacocoCheckRules;
    }

    public Boolean getHaltOnFailure() {
        return haltOnFailure;
    }

    public void setHaltOnFailure(Boolean haltOnFailure) {
        this.haltOnFailure = haltOnFailure;
    }

    public List<JacocoCheckRule> getJacocoCheckRules() {
        return jacocoCheckRules;
    }

    public void setJacocoCheckRules(List<JacocoCheckRule> jacocoCheckRules) {
        this.jacocoCheckRules = jacocoCheckRules;
    }

    @Override
    public String toString() {
        return "JacocoCheckConfiguration{" +
                "haltOnFailure=" + haltOnFailure +
                ", jacocoCheckRules=" + jacocoCheckRules +
                '}';
    }
}
