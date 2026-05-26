package com.restassured.tests;

import org.testng.annotations.*;
import com.restassured.response.Response;
import com.restassured.clients.HealthClient;
import com.restassured.utils.ResponseValidator;
import com.restassured.utils.LoggerUtil;
import com.restassured.exception.HttpClientInitializerException;

public class HealthAPITest {
    private HealthClient healthClient;

    @BeforeClass
    public void setup() {
        healthClient = new HealthClient();
        LoggerUtil.info("=== Health API Test Suite Started ===");
    }

    @AfterClass
    public void tearDown() {
        LoggerUtil.info("=== Health API Test Suite Completed ===");
    }

    @Test(description = "TC6: Request Timeout - 100ms timeout on ping endpoint")
    public void testRequestTimeoutError() throws Exception {
        LoggerUtil.info("Executing: testRequestTimeoutError");
        
        try {
            Response response = healthClient.pingWithTimeout(100);
            LoggerUtil.error("Expected timeout but received response with status: " + response.getStatusCode());
            throw new AssertionError("Expected timeout exception but request completed");
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("timeout") || errorMessage.contains("Read timed out") 
                    || errorMessage.contains("SocketTimeoutException")) {
                LoggerUtil.info("Test PASSED: Timeout exception occurred as expected - " + errorMessage);
            } else {
                LoggerUtil.warn("Received exception: " + errorMessage);
                throw e;
            }
        }
    }
}
