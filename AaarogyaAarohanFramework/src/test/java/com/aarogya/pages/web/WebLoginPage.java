package com.aarogya.pages.web;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class WebLoginPage {

	private WebDriver driver;
	private WebDriverWait wait;

	public WebLoginPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(driver, this);
	}

	// ---------- LOCATORS ----------

	@FindBy(xpath = "//button[normalize-space()='Login with keycloak']")
	private WebElement clcToOpenLoginPage;

	@FindBy(id = "kc-page-title")
	private WebElement signInHeader;

	@FindBy(id = "username")
	private WebElement userNameField;

	@FindBy(id = "password")
	private WebElement passwordField;

	@FindBy(xpath = "//input[@type='submit']")
	private WebElement loginButton;

	@FindBy(xpath = "//p[normalize-space()='Reports & Summary']")
	private WebElement reportsAndSummaryText;

	// ---------- ACTION METHODS ----------

	public void clickToLoginPage() {
		wait.until(ExpectedConditions.elementToBeClickable(clcToOpenLoginPage)).click();
	}

	public boolean isLoginPageDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(signInHeader)).isDisplayed();
	}

	public void enterEmail(String email) {
		wait.until(ExpectedConditions.visibilityOf(userNameField)).clear();
		userNameField.sendKeys(email);
	}

	public void enterPassword(String password) {
		wait.until(ExpectedConditions.visibilityOf(passwordField)).clear();
		passwordField.sendKeys(password);
	}

	public void clickLogin() {
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
	}

	// ---------- COMBINED LOGIN FLOW ----------

	public void login(String email, String password) {
		try {
		clickToLoginPage();
		enterEmail(email);
		enterPassword(password);
		clickLogin();

		// wait till dashboard loads
		wait.until(ExpectedConditions.visibilityOf(reportsAndSummaryText));
		ExtentManager.test.log(Status.PASS, "Web Login SuccessFull");
		}catch(Exception e) {
			ExtentManager.test.log(Status.FAIL, "Web Login Failed:" + e);
		}
	}

	// ---------- VALIDATION METHODS ----------

	public boolean isReportsAndSummaryVisible() {
		return wait.until(ExpectedConditions.visibilityOf(reportsAndSummaryText)).isDisplayed();
	}
}
