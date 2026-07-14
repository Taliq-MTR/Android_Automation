package com.aarogya.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class LoginPage {

	private AndroidDriver driver;

	public LoginPage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// ------------------- LOCATORS -------------------

	@FindBy(xpath = "//android.widget.ScrollView/android.widget.EditText[1]")
	private WebElement entrEmail;

	@FindBy(xpath = "//android.widget.ScrollView/android.widget.EditText[2]")
	private WebElement entrPass;

	@FindBy(xpath = "//android.widget.CheckBox")
	private WebElement ClickCheckBox;

	@FindBy(xpath = "//android.widget.ScrollView/android.view.View/android.widget.Button")
	private WebElement ClickLogin;

	@FindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]")
	private WebElement setPin1;

	@FindBy(xpath = "//android.widget.Button")
	private WebElement clcSetPin;

	@FindBy(xpath = "//android.widget.TextView[@text='flw1 will use this PIN to login']")
	private WebElement waitPin;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Syncing cases\"]")
	private WebElement waitSync;

	@FindBy(xpath = "//android.widget.TextView[@text=\"RECENTLY SUBMITTED\"]")
	private WebElement waitMainPage;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"Notification Permission\"]")
	private WebElement notificatnPermission;
	
	@FindBy(xpath = "//android.view.ViewGroup/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.Button")
	private WebElement notificatnYes;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]")
	private WebElement notificatnAllow;
	

	// ------------------- ACTION METHODS -------------------

	public void clickLogin() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(350));
		// Login
		entrEmail.sendKeys("flw1");
		entrPass.sendKeys("password");
		ClickCheckBox.click();
		ClickLogin.click();
		
		if(driver
				.findElements(By.xpath("//android.widget.TextView[@text=\"Notification Permission\"]"))
				.size() > 0)  {
			notificatnYes.click();
			notificatnAllow.click();
		}
		addPin();
		

	}

	// ------------------- PIN ENTRY -------------------

	// Simple PIN entry method (always 1111)
	
	public void addPin() {
		// Wait for PIN screen
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(250));
				wait.until(ExpectedConditions.visibilityOf(waitPin));

				// Tap first PIN box only
				setPin1.click();

				// Enter PIN (1111)
				enterPin();

				// Click SET PIN button
				clcSetPin.click();

				// Wait for Main screen
				wait.until(ExpectedConditions.visibilityOf(waitMainPage));
	}
	
	public void enterPin() {
		for (int i = 0; i < 4; i++) {
			driver.pressKey(new KeyEvent(AndroidKey.DIGIT_1));
		}
	}

	public String getDashboardText() {
	    try {
	        return waitMainPage.getText();
	    } catch (Exception e) {
	        return null;
	    }
	}
	
}
