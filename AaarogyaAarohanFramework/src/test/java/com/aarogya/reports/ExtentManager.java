package com.aarogya.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    public static ExtentReports extent;
    public static ExtentTest test;   // KEEP PUBLIC

    public static void initReport() {

        String reportPath =
                System.getProperty("user.dir")
                        + "/reports/AutomationReport.html";

        ExtentSparkReporter spark =
                new ExtentSparkReporter(reportPath);

        spark.config().setDocumentTitle("Aarogya Automation Report");
        spark.config().setReportName("Automation Execution Report");
        spark.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(spark);

        extent.setSystemInfo("Tester", "Imran Ansari");
        extent.setSystemInfo("Platform", "Android/Web");
        extent.setSystemInfo("Framework", "Appium + Selenium");
    }

    public static void flushReport() {

        if (extent != null) {
            extent.flush();
        }
    }
}