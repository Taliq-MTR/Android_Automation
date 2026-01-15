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

public class SiteCoOrdinatorPage {

	private WebDriver driver;
	private WebDriverWait wait;

	public SiteCoOrdinatorPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver, this);
	}

	// ---------- REPORTS ----------
	@FindBy(xpath = "//p[text()='Reports']")
	private WebElement reportsTab;

	@FindBy(xpath = "//select")
	private WebElement sortDropdown;

	@FindBy(xpath = "//div[contains(text(), 'Unique case ID')]/following-sibling::div[1]")
	private WebElement firstCaseId;
	
	@FindBy(xpath = "//button//span[text()='Close']")
	private WebElement popupClose;

	// ---------- LATEST ROW (ROW INDEX = 2) ----------
	private String latestRow = "(//div[contains(@class,'flex') and contains(@class,'min-w-full')])[2]";

	private String firstNameXpath = latestRow + "/div[4]//p";
	private String lastNameXpath = latestRow + "/div[5]//p";

	// ---------- ACTIONS ----------

	public void openReports() {
		try {
		wait.until(ExpectedConditions.visibilityOf(reportsTab)).click();
		Thread.sleep(2000);
		if(popupClose.isDisplayed()) {
			popupClose.click();
		} 
		ExtentManager.test.log(Status.INFO, "Navigated to Reports page");
		ExtentManager.test.log(Status.PASS, "Reports On site Co-Ordinator Opened");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "Reports On site Co-Ordinator Not Opened:" + e);
		}
	}

	public void sortByRecentCasesFirst() {
		try {
		wait.until(ExpectedConditions.visibilityOf(sortDropdown));
		sortDropdown.sendKeys("Recent cases first");
		
			Thread.sleep(4000);
		
		ExtentManager.test.log(Status.INFO, "Sorted reports by Recent cases first");
		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Sorted reports by Recent cases first Failed:" + e);
		}
	}

	// ---------- DATA GETTERS ----------

	public String getLatestFirstName() {
		WebElement el = wait
				.until(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.xpath(firstNameXpath)));
		String value = el.getText().trim();
		ExtentManager.test.log(Status.INFO, "Web First Name: " + value);
		return value;
	}

	public String getLatestLastName() {
		WebElement el = wait
				.until(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.xpath(lastNameXpath)));
		String value = el.getText().trim();
		ExtentManager.test.log(Status.INFO, "Web Last Name: " + value);
		return value;
	}

	public String getLatestCaseId() {
		WebElement el = wait.until(ExpectedConditions.visibilityOf(firstCaseId));
		String value = el.getText().trim();
		ExtentManager.test.log(Status.INFO, "Web Case ID: " + value);
		return value;
	}
}
