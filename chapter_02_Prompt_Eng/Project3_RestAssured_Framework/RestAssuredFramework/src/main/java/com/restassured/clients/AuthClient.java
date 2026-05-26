package com.restassured.clients;

import com.restassured.response.Response;
import com.restassured.specification.RequestSpecification;
import com.restassured.models.AuthRequest;
import com.restassured.models.AuthResponse;
import com.restassured.config.Configuration;
import com.restassured.utils.RequestSpecificationBuilder;
import com.restassured.utils.LoggerUtil;
import io.restassured.RestAssured;

public class AuthClient {
    public Response authenticate(AuthRequest authRequest) throws Exception {
        try {
            RequestSpecification spec = RequestSpecificationBuilder.buildRequestSpec();
            LoggerUtil.info("Sending POST request to " + Configuration.AUTH_ENDPOINT);
            
            Response response = RestAssured
                    .given(spec)
                    .body(authRequest)
                    .post(Configuration.AUTH_ENDPOINT);
            
            LoggerUtil.info("Response Status Code: " + response.getStatusCode());
            return response;
        } catch (Exception e) {
            LoggerUtil.error("Error occurred while authenticating: " + e.getMessage(), e);
            throw new Exception("Authentication request failed", e);
        }
    }

    public AuthResponse authenticateAndReturnResponse(AuthRequest authRequest) throws Exception {
        try {
            Response response = authenticate(authRequest);
            if (response.getStatusCode() == 200) {
                return response.as(AuthResponse.class);
            } else {
                throw new Exception("Authentication failed with status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            LoggerUtil.error("Error parsing authentication response: " + e.getMessage(), e);
            throw new Exception("Failed to parse authentication response", e);
        }
    }
}
