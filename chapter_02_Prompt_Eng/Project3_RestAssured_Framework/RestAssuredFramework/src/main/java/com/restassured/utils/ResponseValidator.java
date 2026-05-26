package com.restassured.utils;

import com.restassured.response.Response;
import org.testng.Assert;

public class ResponseValidator {
    public static void validateStatusCode(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, 
            "Expected status code: " + expectedStatusCode + ", but got: " + response.getStatusCode());
    }

    public static void validateNotNull(Object value, String fieldName) {
        Assert.assertNotNull(value, fieldName + " should not be null");
    }

    public static void validateResponseTimeIsWithinLimit(Response response, long maxTimeInMs) {
        Assert.assertTrue(response.getTime() <= maxTimeInMs, 
            "Response time exceeded limit. Expected: " + maxTimeInMs + "ms, but was: " + response.getTime() + "ms");
    }

    public static void validateStatusCodeIsSuccessful(Response response) {
        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode >= 200 && statusCode < 300, 
            "Expected successful status code (2xx), but got: " + statusCode);
    }

    public static void validateStatusCodeIsError(Response response) {
        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode >= 400 && statusCode < 600, 
            "Expected error status code (4xx/5xx), but got: " + statusCode);
    }
}
