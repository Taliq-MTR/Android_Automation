package com.aarogya.base;

import org.testng.annotations.AfterSuite;

public class BaseTest {

    @AfterSuite(alwaysRun = true)
    public void tearDown() {

        DriverManager.quitDriver();       // Android
        WebDriverManager.quitWebDriver(); // Web
    }
    
    private static ThreadLocal<String> executionType = new ThreadLocal<>();

    public static void setExecutionType(String type) {
        executionType.set(type);
    }

    public static String getExecutionType() {
        return executionType.get();
    }
    
}