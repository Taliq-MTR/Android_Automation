package com.aarogya.listener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aarogya.base.DriverManager;
import com.aarogya.base.WebDriverManager;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

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
			ExtentManager.test = extent.createTest(testName);
		} else {
			ExtentManager.test = extent.createTest(testName, description);
		}

		ExtentManager.test.log(Status.INFO, "🚀 Test Started : " + testName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		ExtentManager.test.log(Status.PASS, "✔ Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		try {

			WebDriver activeDriver = null;

			String className = result.getTestClass().getRealClass().getSimpleName();

			System.out.println("Failed Test Class : " + className);

			// WEB TESTS
			if (className.startsWith("Web")) {

				activeDriver = WebDriverManager.getCurrentWebDriver();

				ExtentManager.test.log(Status.INFO, "Capturing WEB Screenshot");

			}
			// MOBILE TEST
			else {

				activeDriver = DriverManager.getCurrentDriver();

				ExtentManager.test.log(Status.INFO, "Capturing ANDROID Screenshot");
			}

			ExtentManager.test.log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " FAILED", ExtentColor.RED));

			ExtentManager.test.fail(result.getThrowable());

			if (activeDriver != null) {

				File src = ((TakesScreenshot) activeDriver).getScreenshotAs(OutputType.FILE);

				String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

				String screenshotName = result.getMethod().getMethodName() + "_" + timestamp + ".png";

				String screenshotDir = System.getProperty("user.dir") + File.separator + "reports" + File.separator
						+ "screenshots" + File.separator;

				new File(screenshotDir).mkdirs();

				File destination = new File(screenshotDir + screenshotName);

				FileUtils.copyFile(src, destination);

				ExtentManager.test.addScreenCaptureFromPath("screenshots/" + screenshotName);

				ExtentManager.test.log(Status.INFO, "Screenshot attached successfully");
			} else {

				ExtentManager.test.log(Status.WARNING, "Driver is NULL. Screenshot not captured.");
			}

		} catch (Exception e) {

			ExtentManager.test.fail("Failed to capture screenshot : " + e.getMessage());

			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		ExtentManager.test.log(Status.SKIP, "⏭ Test Skipped");
	}

	@Override
	public void onFinish(ITestContext context) {

		ExtentManager.flushReport();
	}
}