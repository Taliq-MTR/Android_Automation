package com.aarogya.pages.web;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//p[text()='Reports']")
	private WebElement reportsTab;

	@FindBy(xpath = "//select")
	private WebElement sortDropdown;

	@FindBy(xpath = "//div[text()='Study setting']")
	private WebElement studySettingColumn;

	private String latestRow = "(//div[contains(@class,'flex') and contains(@class,'min-w-full')])[2]";

	private String latestReferenceIdXpath = latestRow + "/div[2]//p";

	private String firstNameXpath = latestRow + "/div[5]//p";

	private String lastNameXpath = latestRow + "/div[6]//p";

	public void openReports() {

		try {

			wait.until(ExpectedConditions.visibilityOf(reportsTab));
			reportsTab.click();

			Thread.sleep(3000);

			if (driver.findElements(By.xpath("//button//span[text()='Close']")).size() > 0) {

				driver.findElement(By.xpath("//button//span[text()='Close']")).click();

				ExtentManager.test.log(Status.INFO, "Popup closed successfully");
			}

			ExtentManager.test.log(Status.PASS, "Reports page opened successfully");

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Reports page opening failed : " + e.getMessage());
		}
	}

	public void sortByRecentCasesFirst() {

		try {

			wait.until(ExpectedConditions.visibilityOf(sortDropdown));

			sortDropdown.sendKeys("Recent cases first");

			Thread.sleep(5000);

			if (driver.findElements(By.xpath("//button//span[text()='Close']")).size() > 0) {

				driver.findElement(By.xpath("//button//span[text()='Close']")).click();
			}

			scrollToStudySettingColumn();

			ExtentManager.test.log(Status.INFO, "Sorted reports by Recent cases first");

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Sorting reports failed : " + e.getMessage());
		}
	}

	public void scrollToStudySettingColumn() {

		try {

			JavascriptExecutor js = (JavascriptExecutor) driver;

			wait.until(ExpectedConditions.visibilityOf(studySettingColumn));

			js.executeScript("arguments[0].scrollIntoView({block:'nearest',inline:'center'});", studySettingColumn);

			Thread.sleep(2000);

			ExtentManager.test.log(Status.INFO, "Scrolled horizontally to Study Setting column");

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Unable to scroll to Study Setting column : " + e.getMessage());
		}
	}

	public String getLatestReferenceId() {

		try {

			WebElement referenceId = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(latestReferenceIdXpath)));

			String value = referenceId.getText().trim();

			ExtentManager.test.log(Status.INFO, "Latest Reference ID : " + value);

			return value;

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Unable to get Reference ID : " + e.getMessage());

			return null;
		}
	}

	public String getLatestFirstName() {

		try {

			WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(firstNameXpath)));

			String value = firstName.getText().trim();

			ExtentManager.test.log(Status.INFO, "Latest First Name : " + value);

			return value;

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Unable to get First Name : " + e.getMessage());

			return null;
		}
	}

	public String getLatestLastName() {

		try {

			WebElement lastName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(lastNameXpath)));

			String value = lastName.getText().trim();

			ExtentManager.test.log(Status.INFO, "Latest Last Name : " + value);

			return value;

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Unable to get Last Name : " + e.getMessage());

			return null;
		}
	}
}