package com.aarogya.listener;

import com.aarogya.reports.ExtentManager;
import com.aarogya.base.BaseTest;
import com.aarogya.base.DriverManager;
import com.aarogya.base.WebDriverManager;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener extends ExtentManager implements ITestListener {

	@Override
	public void onStart(ITestContext context) {
		ExtentManager.initReport();
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String description = result.getMethod().getDescription();

		if (description == null || description.isEmpty()) {
			test = extent.createTest(testName);
		} else {
			test = extent.createTest(testName, description);
		}

		test.log(Status.INFO, "🚀 Test Started: " + testName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "✔ Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		Object testClass = result.getInstance();
		WebDriver activeDriver = null;

		try {
			// 🔹 Android test
			if (testClass instanceof BaseTest && DriverManager.getDriver() != null) {
				activeDriver = DriverManager.getDriver();
			}

			// 🔹 Web test
			if (activeDriver == null && WebDriverManager.getWebDriver() != null) {
				activeDriver = WebDriverManager.getWebDriver();
			}

			if (activeDriver == null) {
				test.log(Status.FAIL, "No active driver found for screenshot");
				return;
			}

			File src = ((TakesScreenshot) activeDriver).getScreenshotAs(OutputType.FILE);

			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String screenshotName = result.getMethod().getMethodName() + "_" + timestamp + ".png";

			String screenshotDir = System.getProperty("user.dir") + "/reports/screenshots/";
			new File(screenshotDir).mkdirs();

			FileUtils.copyFile(src, new File(screenshotDir + screenshotName));

			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAILED", ExtentColor.RED));
			test.log(Status.FAIL, result.getThrowable());
			test.addScreenCaptureFromPath("screenshots/" + screenshotName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.log(Status.SKIP, "⏭ Test Skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.flushReport();
	}
}