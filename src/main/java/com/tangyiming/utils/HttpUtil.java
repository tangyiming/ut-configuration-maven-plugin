package com.tangyiming.utils;

import com.alibaba.fastjson.JSONObject;
import com.tangyiming.data.ApiResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpUtil {
    private static final Log log = new SystemStreamLog();
    public static ApiResponse get(String url){
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                log.error("ut-configuration-maven-plugin request failed: " + response);
                return null;
            }
            HttpEntity entity = response.getEntity();
            String res = EntityUtils.toString(entity, "UTF-8");
            ApiResponse apiResponse = JSONObject.parseObject(res, ApiResponse.class);
            EntityUtils.consume(entity);
            return apiResponse;
        } catch (IOException e) {
            log.error("ut-configuration-maven-plugin request failed: " + e.getCause());
            return null;
        }
    }

    public static ApiResponse post(String url, String json){
        try {
            CloseableHttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            String js = JSONObject.parseObject(json).toJSONString();
            StringEntity entity = new StringEntity(js, StandardCharsets.UTF_8);
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
            HttpResponse res = httpclient.execute(post);
            int statusCode = res.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                log.error("ut-configuration-maven-plugin request failed: " + res);
                return null;
            }
            String result = EntityUtils.toString(res.getEntity());
            ApiResponse apiResponse = JSONObject.parseObject(result, ApiResponse.class);
            EntityUtils.consume(entity);
            return apiResponse;
        } catch (Exception e) {
            log.error("ut-configuration-maven-plugin request failed: " + e.getCause());
            return null;
        }
    }
}
