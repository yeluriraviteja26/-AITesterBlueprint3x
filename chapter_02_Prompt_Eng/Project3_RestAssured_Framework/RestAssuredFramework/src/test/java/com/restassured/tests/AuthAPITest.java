package com.restassured.tests;

import org.testng.annotations.*;
import com.restassured.response.Response;
import com.restassured.clients.AuthClient;
import com.restassured.models.AuthRequest;
import com.restassured.models.AuthResponse;
import com.restassured.utils.ResponseValidator;
import com.restassured.utils.LoggerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthAPITest {
    private AuthClient authClient;

    @BeforeClass
    public void setup() {
        authClient = new AuthClient();
        LoggerUtil.info("=== Auth API Test Suite Started ===");
    }

    @AfterClass
    public void tearDown() {
        LoggerUtil.info("=== Auth API Test Suite Completed ===");
    }

    @Test(description = "TC1: Valid Authentication with correct credentials")
    public void testValidAuthCredentials() throws Exception {
        LoggerUtil.info("Executing: testValidAuthCredentials");
        
        AuthRequest authRequest = new AuthRequest("admin", "password123");
        Response response = authClient.authenticate(authRequest);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateStatusCodeIsSuccessful(response);
        
        AuthResponse authResponse = response.as(AuthResponse.class);
        ResponseValidator.validateNotNull(authResponse.getToken(), "Token");
        
        LoggerUtil.info("Test PASSED: Token received = " + authResponse.getToken());
    }

    @Test(description = "TC2: Invalid Username - should return 401")
    public void testInvalidUsername() throws Exception {
        LoggerUtil.info("Executing: testInvalidUsername");
        
        AuthRequest authRequest = new AuthRequest("invaliduser", "password123");
        Response response = authClient.authenticate(authRequest);
        
        ResponseValidator.validateStatusCode(response, 401);
        ResponseValidator.validateStatusCodeIsError(response);
        
        LoggerUtil.info("Test PASSED: Received 401 for invalid username");
    }

    @Test(description = "TC3: Invalid Password - should return 401")
    public void testInvalidPassword() throws Exception {
        LoggerUtil.info("Executing: testInvalidPassword");
        
        AuthRequest authRequest = new AuthRequest("admin", "wrongpassword");
        Response response = authClient.authenticate(authRequest);
        
        ResponseValidator.validateStatusCode(response, 401);
        ResponseValidator.validateStatusCodeIsError(response);
        
        LoggerUtil.info("Test PASSED: Received 401 for invalid password");
    }

    @Test(description = "TC4: Missing Username field - should return 400")
    public void testMissingUsername() throws Exception {
        LoggerUtil.info("Executing: testMissingUsername");
        
        AuthRequest authRequest = new AuthRequest(null, "password123");
        Response response = authClient.authenticate(authRequest);
        
        int statusCode = response.getStatusCode();
        ResponseValidator.validateStatusCode(response, 400);
        
        LoggerUtil.info("Test PASSED: Received " + statusCode + " for missing username");
    }

    @Test(description = "TC5: Missing Password field - should return 400")
    public void testMissingPassword() throws Exception {
        LoggerUtil.info("Executing: testMissingPassword");
        
        AuthRequest authRequest = new AuthRequest("admin", null);
        Response response = authClient.authenticate(authRequest);
        
        int statusCode = response.getStatusCode();
        ResponseValidator.validateStatusCode(response, 400);
        
        LoggerUtil.info("Test PASSED: Received " + statusCode + " for missing password");
    }
}
