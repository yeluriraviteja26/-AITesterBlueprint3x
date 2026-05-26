package com.restassured.utils;

import com.restassured.builder.RequestSpecBuilder;
import com.restassured.specification.RequestSpecification;
import com.restassured.config.Configuration;

public class RequestSpecificationBuilder {
    public static RequestSpecification buildRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Configuration.BASE_URL)
                .setContentType(Configuration.CONTENT_TYPE)
                .addHeader("Accept", Configuration.ACCEPT)
                .setConnectTimeout(Configuration.CONNECTION_TIMEOUT)
                .setSocketTimeout(Configuration.SOCKET_TIMEOUT)
                .setRelaxedHTTPSValidation()
                .build();
    }

    public static RequestSpecification buildRequestSpecWithAuth(String token) {
        return new RequestSpecBuilder()
                .setBaseUri(Configuration.BASE_URL)
                .setContentType(Configuration.CONTENT_TYPE)
                .addHeader("Accept", Configuration.ACCEPT)
                .addHeader("Authorization", "Bearer " + token)
                .setConnectTimeout(Configuration.CONNECTION_TIMEOUT)
                .setSocketTimeout(Configuration.SOCKET_TIMEOUT)
                .setRelaxedHTTPSValidation()
                .build();
    }

    public static RequestSpecification buildRequestSpecWithTimeout(int timeout) {
        return new RequestSpecBuilder()
                .setBaseUri(Configuration.BASE_URL)
                .setContentType(Configuration.CONTENT_TYPE)
                .addHeader("Accept", Configuration.ACCEPT)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
                .setRelaxedHTTPSValidation()
                .build();
    }
}
