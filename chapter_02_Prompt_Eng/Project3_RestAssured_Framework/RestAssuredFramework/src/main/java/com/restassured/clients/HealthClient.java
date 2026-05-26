package com.restassured.clients;

import com.restassured.response.Response;
import com.restassured.specification.RequestSpecification;
import com.restassured.models.HealthResponse;
import com.restassured.config.Configuration;
import com.restassured.utils.RequestSpecificationBuilder;
import com.restassured.utils.LoggerUtil;
import io.restassured.RestAssured;

public class HealthClient {
    public Response ping() throws Exception {
        try {
            RequestSpecification spec = RequestSpecificationBuilder.buildRequestSpec();
            LoggerUtil.info("Sending GET request to " + Configuration.PING_ENDPOINT);
            
            Response response = RestAssured
                    .given(spec)
                    .get(Configuration.PING_ENDPOINT);
            
            LoggerUtil.info("Response Status Code: " + response.getStatusCode());
            return response;
        } catch (Exception e) {
            LoggerUtil.error("Error occurred during health check: " + e.getMessage(), e);
            throw new Exception("Health check request failed", e);
        }
    }

    public Response pingWithTimeout(int timeout) throws Exception {
        try {
            RequestSpecification spec = RequestSpecificationBuilder.buildRequestSpecWithTimeout(timeout);
            LoggerUtil.info("Sending GET request to " + Configuration.PING_ENDPOINT + " with timeout: " + timeout + "ms");
            
            Response response = RestAssured
                    .given(spec)
                    .get(Configuration.PING_ENDPOINT);
            
            LoggerUtil.info("Response Status Code: " + response.getStatusCode());
            return response;
        } catch (Exception e) {
            LoggerUtil.error("Error occurred during health check with timeout: " + e.getMessage(), e);
            throw new Exception("Health check request with timeout failed", e);
        }
    }
}
