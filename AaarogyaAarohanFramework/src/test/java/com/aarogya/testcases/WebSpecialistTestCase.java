package com.aarogya.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aarogya.base.AssertHelper;
import com.aarogya.base.BaseTest;
import com.aarogya.base.WebDriverManager;
import com.aarogya.pages.web.SpecialistPage;
import com.aarogya.pages.web.WebLogOutPage;
import com.aarogya.pages.web.WebLoginPage;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class WebSpecialistTestCase extends BaseTest {

	@BeforeClass
	public void setWebExecutionType() {
		BaseTest.setExecutionType("WEB");
		WebDriverManager.getWebDriver();
	}

	@Test(priority = 7)
	public void logInSpecialist() {
		// 🔐 LOGIN
		WebLoginPage loginPage = new WebLoginPage(WebDriverManager.getWebDriver());
		loginPage.login("specialist1", "password1");
		ExtentManager.test.log(Status.PASS, "Specialist Login SuccessFull");
	}

	@Test(priority = 8)
	public void verifyDashboardFilterCountsForSpecialist() {

		SpecialistPage specialistPage = new SpecialistPage(WebDriverManager.getWebDriver());

		String[] filters = { "Today", "Last 7 days", "Last 28 days", "Last 90 days", "All time" };

		for (String filter : filters) {

			// Apply filter
			specialistPage.selectDashboardFilter(filter);

			// Capture counts in report
			specialistPage.captureDashboardCounts(filter);

			// Fetch values for assertion
			int total = specialistPage.getTotalCases();
			int pending = specialistPage.getPendingDiagnosis();
			int completed = specialistPage.getCompletedDiagnosis();

			ExtentManager.test.log(Status.INFO, "Asserting counts for filter: " + filter);

			// 🔹 Assertions only in test
			Assert.assertTrue(total >= 0, "Total cases invalid for " + filter);
			Assert.assertTrue(pending >= 0, "Pending diagnosis invalid for " + filter);
			Assert.assertTrue(completed >= 0, "Completed diagnosis invalid for " + filter);

			Assert.assertEquals(total, pending + completed, "Mismatch in dashboard counts for filter: " + filter);

			ExtentManager.test.log(Status.PASS, "Dashboard count validation passed for filter: " + filter);
		}
	}

	@Test(priority = 9)
	public void verifySpecialistDiagnosisSubmissionFlow() {

		SpecialistPage specialistPage = new SpecialistPage(WebDriverManager.getWebDriver());

		// ---- Apply All time filter first ----
		specialistPage.selectDashboardFilter("All time");

		// ---- Capture Dashboard BEFORE ----
		int completedBefore = specialistPage.getCompletedDiagnosisCount();
		int pendingBefore = specialistPage.getPendingDiagnosisCount();

		ExtentManager.test.log(Status.INFO,
				"Dashboard BEFORE | Completed: " + completedBefore + " Pending: " + pendingBefore);

		// 🔹 Start Diagnosis
		specialistPage.startDiagnosisAndCaptureCaseInfo();

		String caseId = SpecialistPage.storedRefrenceId;
		Assert.assertNotNull(caseId, "Case ID was not captured after start diagnosis");

		ExtentManager.test.log(Status.INFO, "Diagnosis Case ID: " + caseId);

		// 🔹 Continue Diagnosis Process
		specialistPage.selectRandomProvisionalDiagnosis();
		specialistPage.selectRandomOralCavitySite();
		specialistPage.selectRandomRecommendation();
		specialistPage.addNotes("Specialist automation diagnosis submission");

		// 🔹 Submit
		specialistPage.submitRecord();

		// 🔹 Capture case position
		specialistPage.logCurrentCaseCounter();

		// 🔹 Go back to previous case
		specialistPage.clickPreviousCase();

		// 🔹 Verify submit message still present
		String actualMsg = specialistPage.getSubmitDiagnosisMessage();

		ExtentManager.test.log(Status.INFO, "Diagnosis Submit Message after previous: " + actualMsg);

		Assert.assertEquals(actualMsg, "Diagnosis Submitted",
				"Diagnosis submit confirmation message mismatch after previous navigation");

		// ---- Back to Dashboard ----
		specialistPage.dashboard();

		// ---- Apply All time filter again ----
		specialistPage.selectDashboardFilter("All time");

		// ---- Capture Dashboard AFTER ----
		int[] counts = specialistPage.getDashboardCountsAfterSubmit(completedBefore);

		int completedAfter = counts[0];
		int pendingAfter = counts[1];

		ExtentManager.test.log(Status.INFO,
				"Dashboard AFTER | Completed: " + completedAfter + " Pending: " + pendingAfter);

		// ---- Assertions ----
		Assert.assertEquals(completedAfter, completedBefore + 1,
				"Completed diagnosis count not increased for Specialist");

		Assert.assertEquals(pendingAfter, pendingBefore - 1, "Pending diagnosis count not decreased for Specialist");

		ExtentManager.test.log(Status.PASS,
				"Dashboard counts validated successfully for Specialist | Case ID: " + caseId);
	}

	@Test(priority = 10)
	public void verifySpecialistSubmitCaseForSecondOpinon() {

		SpecialistPage specialistPage = new SpecialistPage(WebDriverManager.getWebDriver());

		// 🔹 Step 1: Capture completed case
		specialistPage.captureReferenceIdForSecondOpinion();

		String usedCaseId = SpecialistPage.secondOpinionRefrenceId;

		ExtentManager.test.log(Status.INFO, "Case ID picked for Specialist: " + usedCaseId);

		AssertHelper.assertTrue(usedCaseId != null, "No completed case found to process");

		// 🔹 Step 2: Search case
		specialistPage.searchSecondOpinionReferenceId();

		// 🔹 Step 3: Click View
		specialistPage.clickView();

		// 🔹 Step 4: Fill form
		specialistPage.selectRandomProvisionalDiagnosis();
		specialistPage.selectRandomOralCavitySite();
		specialistPage.selectRandomRecommendation();
		specialistPage.addNotes("Specialist automation second opinion");

		// 🔹 Step 5: Submit
		specialistPage.submitSecondOpinion();

		// 🔹 Step 6: Assertion on success message
		String actualMsg = specialistPage.getSecondOpinionSubmitMessage();

		ExtentManager.test.log(Status.INFO, "Second Opinion Submit Message: " + actualMsg);

		AssertHelper.assertEquals(actualMsg, "Sent for second opinion",
				"Second opinion submit confirmation message mismatch");

		ExtentManager.test.log(Status.PASS, "Second opinion successfully submitted for Case ID: " + usedCaseId);
	}

	@Test(priority = 11)
	public void logoutSpecialist() {

		WebLogOutPage logoutPage = new WebLogOutPage(WebDriverManager.getWebDriver());
		logoutPage.logout();
		ExtentManager.test.log(Status.PASS, "Specialist Logout Successful");
	}
}
