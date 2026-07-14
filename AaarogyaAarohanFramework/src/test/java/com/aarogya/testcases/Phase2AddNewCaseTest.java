
package com.aarogya.testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aarogya.base.AssertHelper;
import com.aarogya.base.BaseTest;
import com.aarogya.base.DriverManager;
import com.aarogya.pages.AddNewCasePage;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class Phase2AddNewCaseTest extends BaseTest {

	@BeforeClass
	public void setAndroidExecutionType() {
		BaseTest.setExecutionType("ANDROID");
		DriverManager.getDriver();
	}

	@Test(priority = 3)
	public void AddingNewase() {
		AddNewCasePage addTest = new AddNewCasePage(DriverManager.getDriver());
		AssertHelper.softAssertTrue(addTest.isAddNewCaseVisible(), "Add New Case button is visible",
				"Add New Case button is not visible");
		addTest.clickAddTestBttn();
		AssertHelper.softAssertEquals(addTest.getBasicInfoText(), "Basic Information",
				"Basic Information Page text did not match expected!");// --- Collect all soft assertions ---
		// 🔥 Capture the unique created name
		String createdName = addTest.basicInfoPage();
		ExtentManager.test.log(Status.INFO, "Created Case Name = " + createdName);

		System.out.println("Created Case Name = " + createdName);
		// ✅ PASS TO WEB TEST
		WebSiteCoOrdinatorTest.expectedFirstName = createdName;
		AssertHelper.softAssertEquals(addTest.getHabitHistoryText(), "Habit History",
				"Habit History Page text did not match expected!");// --- Collect all soft assertions ---
		addTest.hbitHistoryPage();
		AssertHelper.softAssertEquals(addTest.getCurrentConditionText(), "Current Condition",
				"Current Condition Page text did not match expected!");// --- Collect all soft assertions ---
		addTest.screeningPage();
		AssertHelper.softAssertEquals(addTest.getImageScreeningText(), "Image Screening",
				"Image Screening Form text did not match expected!");// --- Collect all soft assertions ---
		addTest.imageScreeningWithPermission();
		addTest.imagePreview();
		addTest.submitForm();
		addTest.caseAIResultScreen();
		addTest.waitForPage();

		AssertHelper.softAssertEquals(addTest.getDashboardText(), "RECENTLY SUBMITTED",
				"Cases Page text did not match expected!");

		// ⭐ NEW VALIDATION: Check Recently Submitted contains the created case
		boolean isVisible = addTest.isCaseVisibleInRecentlySubmitted(createdName);

		AssertHelper.softAssertEquals(isVisible ? "VISIBLE" : "NOT_VISIBLE", "VISIBLE",
				"Recently Submitted case name not visible!");
		addTest.captureReferenceIdAndVisitedTime();

		AssertHelper.softAssertTrue(
				AddNewCasePage.generatedReferenceId != null
						&& !AddNewCasePage.generatedReferenceId.equalsIgnoreCase("Not Available"),
				"Reference ID generated successfully", "Reference ID was not generated");

		// ⭐ NEW ASSERTION: Sync must complete
		boolean syncCompleted = addTest.waitUntilSyncSafelyCompletes();

		AssertHelper.softAssertEquals(syncCompleted ? "COMPLETED" : "NOT_COMPLETED", "COMPLETED",
				"Background sync did not complete successfully!");

		AssertHelper.assertAll();

	}
}