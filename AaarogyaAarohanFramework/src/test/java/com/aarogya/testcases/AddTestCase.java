package com.aarogya.testcases;

import org.testng.annotations.Test;

import com.aarogya.base.AssertHelper;
import com.aarogya.base.BaseTest;
import com.aarogya.base.DriverManager;
import com.aarogya.pages.AddTestPage;

public class AddTestCase extends BaseTest {

	@Test(priority = 3)
	public void addingTestCase() {
		AddTestPage addTest = new AddTestPage(DriverManager.getDriver());
		AssertHelper.softAssertEquals(addTest.getAddBttnText(), "Add new case",
				"Add New Case text did not match expected!");// --- Collect all soft assertions ---
		addTest.clickAddTestBttn();
		AssertHelper.softAssertEquals(addTest.getBasicInfoText(), "Basic Information",
				"Basic Information Page text did not match expected!");// --- Collect all soft assertions ---
		// 🔥 Capture the unique created name
		String createdName = addTest.basicInfoPage();
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
		addTest.submitForm();
		addTest.waitForPage();

		AssertHelper.softAssertEquals(addTest.getDashboardText(), "RECENTLY SUBMITTED",
				"Cases Page text did not match expected!");

		// ⭐ NEW VALIDATION: Check Recently Submitted contains the created case
		boolean isVisible = addTest.isCaseVisibleInRecentlySubmitted(createdName);

		AssertHelper.softAssertEquals(isVisible ? "VISIBLE" : "NOT_VISIBLE", "VISIBLE",
				"Recently Submitted case name not visible!");

		// ⭐ NEW ASSERTION: Sync must complete
		boolean syncCompleted = addTest.waitUntilSyncSafelyCompletes();

		AssertHelper.softAssertEquals(syncCompleted ? "COMPLETED" : "NOT_COMPLETED", "COMPLETED",
				"Background sync did not complete successfully!");

		AssertHelper.assertAll();

	}
}