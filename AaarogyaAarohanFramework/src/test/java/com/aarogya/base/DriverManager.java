package com.aarogya.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private static AndroidDriver driver;

    private DriverManager() {
        // prevent object creation
    }

    public static AndroidDriver getDriver() {

        if (driver == null) {
            try {

                BaseOptions options = new BaseOptions()

                        // ================= BASIC CAPS =================
                        .amend("platformName", "Android")
                        .amend("appium:automationName", "UiAutomator2")
                        .amend("appium:deviceName", "Android Device")
                        .amend("appium:udid", "ZN4227T7R6")

                        // ================= APP DETAILS =================
                        .amend("appium:appPackage", "in.ac.iisc.arogyam")
                        .amend(
                                "appium:appActivity",
                                "org.smartregister.fhircore.quest.ui.splash.SplashActivity"
                        )

                        // ================= 🔥 CRITICAL FIXES 🔥 =================
                        // Prevent Appium from touching secure system settings
                        .amend("appium:noReset", true)
                        .amend("appium:fullReset", false)
                        .amend("appium:skipDeviceInitialization", true)
                        .amend("appium:skipServerInstallation", true)

                        // MUST be false to avoid WRITE_SECURE_SETTINGS error
                        .amend("appium:autoGrantPermissions", false)
                        .amend("appium:disableSuppressAccessibilityService", false)

                        // ================= SAFE & OPTIONAL =================
                        .amend("appium:ignoreHiddenApiPolicyError", true)
                        .amend("appium:ensureWebviewsHavePages", true)
                        .amend("appium:nativeWebScreenshot", true)
                        .amend("appium:newCommandTimeout", 3600)
                        .amend("appium:enforceXPath1", true)
                        .amend("appium:connectHardwareKeyboard", true);

                driver = new AndroidDriver(
                        new URL("http://127.0.0.1:4723"),
                        options
                );

                driver.manage()
                        .timeouts()
                        .implicitlyWait(Duration.ofSeconds(20));

            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Appium server URL", e);
            }
        }

        return driver;
    }

    public static void quitDriver() {

        if (driver != null) {
            try {
                driver.quit();   // clean quit without secure settings access
            } finally {
                driver = null;
            }
        }
    }
}
