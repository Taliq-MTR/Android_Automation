package com.aarogya.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class HomePage {

	private AndroidDriver driver;

	public HomePage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//android.widget.TextView[@text=\"Select your server\"]")
	private WebElement slctServerTxt;

	@FindBy(xpath = "//android.widget.EditText")
	private WebElement slctServer;

	@FindBy(xpath = "//android.widget.TextView[@text='Staging, IISC']")
	private WebElement iiscServr;

	@FindBy(xpath = "//android.widget.Button")
	private WebElement clickContinueBtn;

	public boolean isCitySelectionPageDisplayed() {

		return driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Select your server\"]"))
				.size() > 0;
	}

	public void selectStagingServer() {

		try {

			ExtentManager.test.log(Status.INFO, "Site Selection page detected");

			slctServer.click();

			ExtentManager.test.log(Status.INFO, "Opened Site Selection dropdown");

			driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
					+ ".scrollIntoView(new UiSelector().text(\"Staging, IISC\"))"));

			Thread.sleep(1000);

			iiscServr.click();

			ExtentManager.test.log(Status.INFO, "'Staging, IISC' server selected");

			clickContinueBtn.click();

			ExtentManager.test.log(Status.PASS, "Staging, IISC server selected successfully");

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Unable to select Staging, IISC server : " + e.getMessage());

			throw new RuntimeException(e);
		}
	}
}