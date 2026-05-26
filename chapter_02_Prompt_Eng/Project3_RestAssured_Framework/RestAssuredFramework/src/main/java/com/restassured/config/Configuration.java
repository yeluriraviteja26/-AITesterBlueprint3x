package com.restassured.config;

public class Configuration {
    public static final String BASE_URL = ConfigReader.getBaseUrl();
    public static final int REQUEST_TIMEOUT = ConfigReader.getRequestTimeout();
    public static final int CONNECTION_TIMEOUT = ConfigReader.getConnectionTimeout();
    public static final int SOCKET_TIMEOUT = ConfigReader.getSocketTimeout();

    public static final String AUTH_ENDPOINT = "/auth";
    public static final String PING_ENDPOINT = "/ping";

    public static final String CONTENT_TYPE = "application/json";
    public static final String ACCEPT = "application/json";

    public static final String VALID_USERNAME = "admin";
    public static final String VALID_PASSWORD = "password123";
}
