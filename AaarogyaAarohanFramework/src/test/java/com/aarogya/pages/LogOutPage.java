package com.aarogya.pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;

public class LogOutPage {

	private AndroidDriver driver;

	public LogOutPage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// ------------------- LOCATORS -------------------
	@FindBy(xpath = "(//android.widget.ImageView[@content-desc=\"Filter\"])[5]")
	private WebElement clcProfile;

	@FindBy(xpath = "//android.widget.TextView[@text='Profile']")
	private WebElement profileHeader;

	@FindBy(xpath = "//android.widget.TextView[@text='LOGOUT']")
	private WebElement logOutBttn;

	@FindBy(xpath = "//android.widget.ScrollView/android.view.View/android.widget.Button")
	private WebElement loginButton;

	@FindBy(xpath = "//android.widget.TextView[@text='Username']")
	private WebElement usernameText;

	@FindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[2]/android.view.View")
	private WebElement syncPopUp;

	public void openProfile() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOf(syncPopUp));
			Thread.sleep(5000);
			wait.until(ExpectedConditions.visibilityOf(clcProfile));
			clcProfile.click();

			wait.until(ExpectedConditions.visibilityOf(profileHeader));

			ExtentManager.test.log(Status.PASS, "The Profile Page Opened SuccessFully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "The Profile Page Opening Failed:" + e);
		}
	}

	public void loggingOut() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOf(logOutBttn));
			Thread.sleep(500);
			logOutBttn.click();
			ExtentManager.test.log(Status.INFO, "LogOut Button Clicked");
			wait.until(ExpectedConditions.visibilityOf(loginButton));
			wait.until(ExpectedConditions.visibilityOf(usernameText));

			ExtentManager.test.log(Status.PASS, "User LogOut SuccessFully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "User LogOut Failed:" + e);
		}

	}

	// ------------------- GETTERS FOR ASSERTIONS -------------------

	// For asserting profile screen open
	public String getProfileHeaderText() {
		try {
			return profileHeader.getText();
		} catch (Exception e) {
			return null;
		}
	}

	// For asserting logout success (we’ll check "Username" text)
	public String getLoginPageText() {
		try {
			return usernameText.getText();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean isLoginButtonVisible() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			wait.until(ExpectedConditions.visibilityOf(loginButton));

			return true;

		} catch (Exception e) {

			return false;
		}
	}

}
