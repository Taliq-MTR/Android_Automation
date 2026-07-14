package com.aarogya.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;

public class AddDraftCasePage {

	private AndroidDriver driver;

	public AddDraftCasePage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	// ------------------- LOCATORS -------------------

	@FindBy(xpath = "//android.widget.FrameLayout[@content-desc=\"Cases\"]")
	private WebElement goToCases;

	@FindBy(xpath = "//android.widget.ImageButton")
	private WebElement clcBackBttn;

	@FindBy(xpath = "//android.widget.TextView[@text=\"DRAFTS\"]")
	private WebElement waitForDraft;

	@FindBy(xpath = "(//android.view.View[@content-desc=\"Filter\"])[1]")
	private WebElement draftEditBttn;

	@FindBy(xpath = "(//android.view.View[@content-desc=\"Filter\"])[2]")
	private WebElement draftDltBttn;

	@FindBy(xpath = "//android.view.ViewGroup/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.widget.Button")
	private WebElement dltNo;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Yes\"]")
	private WebElement dltYes;

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"in.ac.iisc.arogyam:id/pagination_next_button\"]")
	private WebElement nxtBttn;

	@FindBy(xpath = "//android.widget.TextView[@text=\"RECENTLY SUBMITTED\"]")
	private WebElement waitMainPage;

	public String addDraftCases() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			Thread.sleep(800);
			wait.until(ExpectedConditions.visibilityOf(goToCases));
			goToCases.click();
			Thread.sleep(5000);
			AddNewCasePage creatTest = new AddNewCasePage(driver);
			creatTest.clickAddTestBttn();
			// 🔥 Capture unique generated name
			String uniqueName = creatTest.basicInfoPage();
			creatTest.hbitHistoryPage();
			creatTest.screeningPage();

			ExtentManager.test.log(Status.PASS, "Data Added For Draft Successfully");

			return uniqueName;

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Data Added For Draft Failed: " + e);
			return null;
		}

	}

	public void clickBackBttn() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			Thread.sleep(800);
			wait.until(ExpectedConditions.visibilityOf(clcBackBttn));
			clcBackBttn.click();
			wait.until(ExpectedConditions.visibilityOf(waitForDraft));
			ExtentManager.test.log(Status.PASS, "Draft Case Created Successfully");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Draft Case Creation Failed: " + e);

		}

	}

	public boolean isDeleteButtonVisible() {
		try {
			return draftDltBttn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void dltDraftCases() {
		try {
			draftDltBttn.click();
			ExtentManager.test.log(Status.INFO, "Delete Button Clicked");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOf(dltNo));
			dltNo.click();
			ExtentManager.test.log(Status.INFO, "No option Clicked");
			draftDltBttn.click();
			ExtentManager.test.log(Status.INFO, "Delete Button Clicked");
			wait.until(ExpectedConditions.visibilityOf(dltYes));
			dltYes.click();
			ExtentManager.test.log(Status.INFO, "Yes option Clicked");

			ExtentManager.test.log(Status.PASS, "The Case Deleted Through Draft Case");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "The Case not Deleted Through Draft Case:" + e);
		}

	}

	public void registeredDraftCases() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOf(draftEditBttn));
			Thread.sleep(2000);
			draftEditBttn.click();
			ExtentManager.test.log(Status.INFO, "Draft Edit button Clicked");
			wait.until(ExpectedConditions.visibilityOf(nxtBttn));
			nxtBttn.click();
			ExtentManager.test.log(Status.INFO, "Next button Clicked");
			wait.until(ExpectedConditions.visibilityOf(nxtBttn));
			nxtBttn.click();
			ExtentManager.test.log(Status.INFO, "Next button Clicked");
			AddNewCasePage test = new AddNewCasePage(driver);
			test.imageScreeningWithPermission();
			test.imagePreview();
			test.submitForm();
			Thread.sleep(2000);
			test.waitForPage();
			ExtentManager.test.log(Status.PASS, "The Case Registered Through Draft Case");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "The Case not Registered Through Draft Case:" + e);
		}

	}
	
	public void registeredDraftCasesPH2() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOf(draftEditBttn));
			Thread.sleep(2000);
			draftEditBttn.click();
			ExtentManager.test.log(Status.INFO, "Draft Edit button Clicked");
			wait.until(ExpectedConditions.visibilityOf(nxtBttn));
			nxtBttn.click();
			ExtentManager.test.log(Status.INFO, "Next button Clicked");
			wait.until(ExpectedConditions.visibilityOf(nxtBttn));
			nxtBttn.click();
			ExtentManager.test.log(Status.INFO, "Next button Clicked");
			AddNewCasePage test = new AddNewCasePage(driver);
			test.imageScreeningWithPermission();
			test.imagePreview();
			test.submitForm();
			test.caseAIResultScreen();
			Thread.sleep(2000);
			test.waitForPage();
			ExtentManager.test.log(Status.PASS, "The Case Registered Through Draft Case");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "The Case not Registered Through Draft Case:" + e);
		}

	}

	public String getDraftName(String expectedName) {
		try {
			String xpath = "//android.widget.TextView[contains(@text,'" + expectedName + "')]";
			return driver.findElement(By.xpath(xpath)).getText();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean isEditButtonVisible() {
		try {
			return draftEditBttn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getDashboardText() {
		try {
			return waitMainPage.getText();
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean isCaseVisibleInRecentlySubmitted(String firstName) {
		try {
			String xpath = "//android.widget.TextView[@text='" + firstName + "']";
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

			ExtentManager.test.log(Status.PASS, "Case visible in Recently Submitted: " + firstName);
			return true;

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Case NOT visible in Recently Submitted: " + firstName);
			return false;
		}
	}

}
