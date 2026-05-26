package com.restassured.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        String environment = System.getProperty("environment", "dev");
        String propertiesFile = "src/main/resources/application-" + environment + ".properties";
        
        if ("dev".equals(environment)) {
            propertiesFile = "src/main/resources/application.properties";
        }

        try (FileInputStream fis = new FileInputStream(propertiesFile)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties from: " + propertiesFile, e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static int getRequestTimeout() {
        return Integer.parseInt(getProperty("request.timeout"));
    }

    public static int getConnectionTimeout() {
        return Integer.parseInt(getProperty("connection.timeout"));
    }

    public static int getSocketTimeout() {
        return Integer.parseInt(getProperty("socket.timeout"));
    }
}
