
package com.aarogya.testcases;

import org.testng.annotations.Test;

import com.aarogya.base.AssertHelper;
import com.aarogya.base.BaseTest;
import com.aarogya.base.DriverManager;
import com.aarogya.pages.AddTestPhase2Page;

public class AddTestCasePhase2Test extends BaseTest {

	@Test(priority = 3)
	public void addingTestCase() {
		AddTestPhase2Page addTestPh2 = new AddTestPhase2Page(DriverManager.getDriver());
		AssertHelper.softAssertEquals(addTestPh2.getAddBttnText(), "Add new case",
				"Add New Case text did not match expected!");// --- Collect all soft assertions ---
		addTestPh2.clickAddTestBttn();
		AssertHelper.softAssertEquals(addTestPh2.getBasicInfoText(), "Basic Information",
				"Basic Information Page text did not match expected!");// --- Collect all soft assertions ---
		// 🔥 Capture the unique created name
		String createdName = addTestPh2.basicInfoPage();
		// ✅ PASS TO WEB TEST
		WebSiteCoOrdinatorTest.expectedFirstName = createdName;
		AssertHelper.softAssertEquals(addTestPh2.getHabitHistoryText(), "Habit History",
				"Habit History Page text did not match expected!");// --- Collect all soft assertions ---
		addTestPh2.hbitHistoryPage();
		AssertHelper.softAssertEquals(addTestPh2.getCurrentConditionText(), "Current Condition",
				"Current Condition Page text did not match expected!");// --- Collect all soft assertions ---
		addTestPh2.screeningPage();
		AssertHelper.softAssertEquals(addTestPh2.getImageScreeningText(), "Image Screening",
				"Image Screening Form text did not match expected!");// --- Collect all soft assertions ---
		addTestPh2.imageScreeningWithPermission();
		addTestPh2.submitForm();
		addTestPh2.waitForPage();

		AssertHelper.softAssertEquals(addTestPh2.getDashboardText(), "RECENTLY SUBMITTED",
				"Cases Page text did not match expected!");

		// ⭐ NEW VALIDATION: Check Recently Submitted contains the created case
		boolean isVisible = addTestPh2.isCaseVisibleInRecentlySubmitted(createdName);

		AssertHelper.softAssertEquals(isVisible ? "VISIBLE" : "NOT_VISIBLE", "VISIBLE",
				"Recently Submitted case name not visible!");

		// ⭐ NEW ASSERTION: Sync must complete
		boolean syncCompleted = addTestPh2.waitUntilSyncSafelyCompletes();

		AssertHelper.softAssertEquals(syncCompleted ? "COMPLETED" : "NOT_COMPLETED", "COMPLETED",
				"Background sync did not complete successfully!");

		AssertHelper.assertAll();

	}
}