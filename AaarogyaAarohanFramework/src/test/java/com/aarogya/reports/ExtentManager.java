package com.aarogya.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	public static ExtentReports extent;
	public static ExtentSparkReporter spark;
	public static ExtentTest test;

	public static void initReport() {
		// Report always generated at reports/AutomationReport.html
		String reportPath = System.getProperty("user.dir") + "/reports/AutomationReport.html";

		spark = new ExtentSparkReporter(reportPath);
		spark.config().setDocumentTitle("Aarogya Automation Report");
		spark.config().setReportName("Appium Test Execution Report");
		spark.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(spark);

		// Add system info
		extent.setSystemInfo("Tester", "Imran Ansari");
		extent.setSystemInfo("Platform", "Android");
		extent.setSystemInfo("Automation", "Appium + TestNG");
	}

	public static void flushReport() {
		extent.flush();
	}
}