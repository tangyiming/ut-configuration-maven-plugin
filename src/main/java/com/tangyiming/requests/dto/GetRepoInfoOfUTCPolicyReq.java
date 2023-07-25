package com.tangyiming.requests.dto;

public class GetRepoInfoOfUTCPolicyReq {
    private String repoFullName;

    public GetRepoInfoOfUTCPolicyReq(String repoFullName) {
        this.repoFullName = repoFullName;
    }

    public GetRepoInfoOfUTCPolicyReq() {
    }

    public String getRepoFullName() {
        return repoFullName;
    }

    public void setRepoFullName(String repoFullName) {
        this.repoFullName = repoFullName;
    }
}
