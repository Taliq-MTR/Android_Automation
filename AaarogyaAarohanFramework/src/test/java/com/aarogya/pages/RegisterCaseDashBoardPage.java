package com.aarogya.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class RegisterCaseDashBoardPage {

	private AndroidDriver driver;

	public RegisterCaseDashBoardPage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// ------------------- LOCATORS -------------------

	@FindBy(xpath = "//android.widget.TextView[@resource-id=\"in.ac.iisc.arogyam:id/navigation_bar_item_small_label_view\" and @text=\"Dashboard\"]")
	private WebElement goToDashboard;

	@FindBy(xpath = "//android.widget.Button")
	private WebElement clcAddNewCase;

	@FindBy(id = "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
	private WebElement locatnPermisn;

	@FindBy(xpath = "//android.widget.TextView[@text='Total screened cases']")
	private WebElement totalScreenedCases;

	@FindBy(xpath = "//android.widget.TextView[@text=\"RECENTLY SUBMITTED\"]")
	private WebElement waitMainPage;

	// ------------------- DASHBOARD NAVIGATION -------------------

	public void clickDshboard() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			wait.until(ExpectedConditions.visibilityOf(goToDashboard));

			goToDashboard.click();
			ExtentManager.test.log(Status.PASS, "User Came To DashBoard Successfully");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "User Came To DashBoard Failed: " + e);

		}
	}

	public void addNewCaseBttn() {
		try {
			clcAddNewCase.click();
			Thread.sleep(200);

			if (driver
					.findElements(By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button"))
					.size() > 0) {
				locatnPermisn.click();
			}

			ExtentManager.test.log(Status.PASS, "Add New Case Button Clicked From DashBoard Successfully");
		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Add New Case Button Clicked From DashBoard Failed: " + e);
		}
	}

	// ------------------- FORM FILLING (UNCHANGED) -------------------

	public String fillBasicInfoFromDashboard() {
		try {
			AddTestPage test = new AddTestPage(driver);
			String uniqueName = test.basicInfoPage();
			ExtentManager.test.log(Status.PASS, "Basic Info Completed");
			return uniqueName;
		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Basic Info Failed: " + e);
			return null;
		}
	}

	public void fillHabitHistoryFromDashboard() {
		try {
			AddTestPage test = new AddTestPage(driver);
			test.hbitHistoryPage();
			ExtentManager.test.log(Status.PASS, "Habit History Completed");
		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Habit History Failed: " + e);
		}
	}

	public void fillCurrentConditionFromDashboard() {
		try {
			AddTestPage test = new AddTestPage(driver);
			test.screeningPage();
			ExtentManager.test.log(Status.PASS, "Current Condition Completed");
		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Current Condition Failed: " + e);
		}
	}

	public void fillImageScreeningFromDashboard() {
		try {
			AddTestPage test = new AddTestPage(driver);
			test.imageScreeningWithPermission();
			ExtentManager.test.log(Status.PASS, "Image Screening Completed");
		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Image Screening Failed: " + e);
		}
	}

	public void submitCaseFromDashboard() {
		try {
			AddTestPage test = new AddTestPage(driver);
			test.submitForm();
			// ✅ HARD WAIT FOR APP STATE STABILIZATION
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(totalScreenedCases));
			Thread.sleep(12000);
			ExtentManager.test.log(Status.INFO, "Post-submit screen stabilized");
		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Post-submit screen stabilized Failed: " + e);
		}
	}

	public String getDashboardTitleText() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(15))
					.until(ExpectedConditions.visibilityOf(totalScreenedCases));

			return totalScreenedCases.getText();

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Dashboard title not visible: " + e);
			return null;
		}
	}

	// ------------------- TEXT GETTERS FOR ASSERTION -------------------

	public String getBasicInfoText() {
		return new AddTestPage(driver).getBasicInfoText();
	}

	public String getHabitHistoryText() {
		return new AddTestPage(driver).getHabitHistoryText();
	}

	public String getCurrentConditionText() {
		return new AddTestPage(driver).getCurrentConditionText();
	}

	public String getImageScreeningText() {
		return new AddTestPage(driver).getImageScreeningText();
	}

	public String getDashboardText() {
		return new AddTestPage(driver).getDashboardText();
	}

	// ------------------- UNIVERSAL COUNTER READER -------------------
	// Finds text → returns next TextView's number
	// -------------------

	private int getCountForLabel(String labelText) {
		try {
			List<WebElement> allTexts = driver.findElements(AppiumBy.className("android.widget.TextView"));

			for (int i = 0; i < allTexts.size(); i++) {
				String text = "";
				try {
					text = allTexts.get(i).getText().trim();
				} catch (Exception ignored) {
				}

				if (labelText.equals(text)) {
					if (i + 1 < allTexts.size()) {
						String count = allTexts.get(i + 1).getText().trim();
						try {
							return Integer.parseInt(count);
						} catch (NumberFormatException nfe) {
							ExtentManager.test.log(Status.FAIL,
									"Count for '" + labelText + "' is not numeric: '" + count + "'");
							return -1;
						}
					}
				}
			}

			ExtentManager.test.log(Status.FAIL, "Label '" + labelText + "' not found!");
			return -1;

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Error reading count for '" + labelText + "': " + e);
			return -1;
		}
	}

	// ------------------- COUNT GETTERS (PUBLIC) -------------------

	public int getTotalScreenedCasesCount() {
		return getCountForLabel("Total screened cases");
	}

	public int getTodayCount() {
		return getCountForLabel("Today");
	}

	public int getThisWeekCount() {
		return getCountForLabel("This Week");
	}

	public int getThisMonthCount() {
		return getCountForLabel("This Month");
	}
}
