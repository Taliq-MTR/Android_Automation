package com.aarogya.testcases;

import org.testng.annotations.Test;

import com.aarogya.base.AssertHelper;
import com.aarogya.base.BaseTest;
import com.aarogya.base.DriverManager;
import com.aarogya.pages.RecommendationsPage;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class RecommendationsTest extends BaseTest {

	@Test(priority = 10)
	public void recommendationFlowTest() {

		RecommendationsPage page = new RecommendationsPage(DriverManager.getDriver());

		// 🔹 Open Recommendations
		page.clickRecommendationsBttn();

		AssertHelper.softAssertEquals(page.isRecommendationsPageLoaded() ? "LOADED" : "NOT_LOADED", "LOADED",
				"Page not loaded");

		// 🔥 Capture case
		String caseName = page.getFirstNotContactedName();
		ExtentManager.test.log(Status.INFO, "Case: " + caseName);
//		System.out.println("Case: " + caseName);

		// 🔹 Cancel validation
		page.cancelStatusChange();

		AssertHelper.softAssertEquals(page.isChangeStatusClosed() ? "CLOSED" : "OPEN", "CLOSED", "Cancel failed");

		// 🔹 Move → Not Responded
		String screenedTime = page.statusChangeToPendingAsNotResponded();
		ExtentManager.test.log(Status.INFO, "Screened Time: " + screenedTime);
//		System.out.println("Screened Time: " + screenedTime);

		// ✅ Validate in Pending tab
		page.clickPendingTab();

		AssertHelper.softAssertEquals(page.isCaseVisible(caseName) ? "FOUND" : "NOT_FOUND", "FOUND",
				"Not moved to NOT RESPONDED");

		// 🔹 Move → Agreed
		page.notRespondedStatusChangeToAgrred(caseName);

		AssertHelper.softAssertEquals(page.isCaseVisible(caseName) ? "FOUND" : "NOT_FOUND", "NOT_FOUND",
				"Case still present in NOT RESPONDED (should be moved to AGREED)");
		// 🔹 Move → Follow-up Done (Completed)
		page.statusChangedToCompleted(caseName);

		// 🔥 Ensure we are in Completed tab
		page.clickCompletedTab();

		// 🔥 Deep validation (your matching logic)
		page.checkCaseInCompleted(caseName, screenedTime);

		// ✅ Final Status Validation
		AssertHelper.softAssertEquals(page.isCaseCompleted(caseName) ? "COMPLETED" : "NOT_COMPLETED", "COMPLETED",
				"Case is not marked as COMPLETED");

		// 🔹 Final Assert
		AssertHelper.assertAll();
	}
}