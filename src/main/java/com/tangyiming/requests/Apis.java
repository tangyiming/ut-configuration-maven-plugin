package com.tangyiming.requests;

import com.alibaba.fastjson.JSONObject;
import com.tangyiming.data.ApiResponse;
import com.tangyiming.requests.dto.GetRepoInfoOfUTCPolicyReq;
import com.tangyiming.requests.dto.RepoUTCPolicyDetail;
import com.tangyiming.data.Consts;
import com.tangyiming.requests.dto.ReportDataOfUTCPolicyReq;
import com.tangyiming.utils.HttpUtil;

public class Apis {
    public static ApiResponse<RepoUTCPolicyDetail> getRepoInfoOfUTCPolicy(GetRepoInfoOfUTCPolicyReq req) {
        String url = Consts.BASEURL + "/v1/policy/ut-coverage/info-of-repo";
        return HttpUtil.post(url, JSONObject.toJSONString(req));
    }

    public static ApiResponse<String> reportDataOfUTCPolicy(ReportDataOfUTCPolicyReq reportData) {
        String url = Consts.BASEURL + "/v1/policy/ut-coverage/report-data";
        return HttpUtil.post(url, JSONObject.toJSONString(reportData));
    }
}
