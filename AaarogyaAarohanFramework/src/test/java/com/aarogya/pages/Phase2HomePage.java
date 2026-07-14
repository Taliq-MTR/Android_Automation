package com.aarogya.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class Phase2HomePage {

	private AndroidDriver driver;

	public Phase2HomePage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//android.widget.EditText")
	private WebElement slctServer;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Demo Staging, Phase 2\"]")
	private WebElement tanuhServr;

	@FindBy(xpath = "//android.widget.Button")
	private WebElement clickContinueBtn;

	public boolean isCitySelectionPageDisplayed() {

		return driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Select your server\"]"))
				.size() > 0;
	}

	public void selectStagingServer2() {
		try {
			ExtentManager.test.log(Status.INFO, "Site Selection page detected");

			slctServer.click();

			ExtentManager.test.log(Status.INFO, "Opened Site Selection dropdown");

			// Scroll until "Demo Staging, Phase 2" is visible and click it
			driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
					+ ".scrollIntoView(new UiSelector().text(\"Demo Staging, Phase 2\"))"));
			Thread.sleep(1000);

			tanuhServr.click();

			ExtentManager.test.log(Status.INFO, "'Staging Phase2, TANUH' server selected");

			clickContinueBtn.click();

			ExtentManager.test.log(Status.PASS, "Staging Phase2, TANUH server selected successfully");

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Unable to select Staging Phase2, TANUH server : " + e.getMessage());

			throw new RuntimeException(e);
		}
	}

}