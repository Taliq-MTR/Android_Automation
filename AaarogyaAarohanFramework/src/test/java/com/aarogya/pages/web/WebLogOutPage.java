package com.aarogya.pages.web;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class WebLogOutPage {

	private WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;

	public WebLogOutPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		this.actions = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	// ---------- LOCATORS ----------

	// ✅ User profile / logo (hover target)
	@FindBy(xpath = "//div[contains(@class,'ant-dropdown-trigger') and contains(@class,'cursor-pointer')]")
	private WebElement userProfileLogo;

	// ✅ Logout button inside dropdown
	@FindBy(xpath = "//button[normalize-space()='Logout']")
	private WebElement logoutButton;

	// ✅ Login page validation after logout
	@FindBy(xpath = "//button[normalize-space()='Login with keycloak']")
	private WebElement loginWithKeycloakBtn;

	// ---------- ACTIONS ----------

	public void logout() {
		try {
		Thread.sleep(1500);
		wait.until(ExpectedConditions.visibilityOf(userProfileLogo));
		actions.moveToElement(userProfileLogo).perform();

		wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();

		// wait until login page is visible
		wait.until(ExpectedConditions.visibilityOf(loginWithKeycloakBtn));
		ExtentManager.test.log(Status.PASS, "Web LogOut SuccessFull");
		}catch (Exception e){
			ExtentManager.test.log(Status.FAIL, "Web LogOut Failed:" + e);
		}
		
	}

	// ---------- VALIDATION ----------

	public boolean isLogoutSuccessful() {
		return wait.until(ExpectedConditions.visibilityOf(loginWithKeycloakBtn)).isDisplayed();
	}
}
