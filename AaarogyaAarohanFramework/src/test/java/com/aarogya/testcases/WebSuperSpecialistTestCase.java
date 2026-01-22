package com.aarogya.testcases;

import org.testng.annotations.Test;

import com.aarogya.base.AssertHelper;
import com.aarogya.base.BaseTest;
import com.aarogya.base.WebDriverManager;
import com.aarogya.pages.web.SpecialistPage;
import com.aarogya.pages.web.SuperSpecialistPage;
import com.aarogya.pages.web.WebLogOutPage;
import com.aarogya.pages.web.WebLoginPage;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class WebSuperSpecialistTestCase {

	@Test(priority = 12)
	public void logInSuperSpecialist() {
		// 🔐 LOGIN
		WebLoginPage loginPage = new WebLoginPage(WebDriverManager.getWebDriver());
		loginPage.login("sspecialist1", "password");
		ExtentManager.test.log(Status.PASS, "Super Specialist Login SuccessFull");
	}

	@Test(priority = 13)
	public void verifySuperSpecialistSubmitCaseAndDashboard() {

		SuperSpecialistPage superPage = new SuperSpecialistPage(WebDriverManager.getWebDriver());

		String usedCaseId = SpecialistPage.storedCaseId;

		AssertHelper.assertTrue(usedCaseId != null, "No Case ID available for Super Specialist flow");

		// ---- Capture Dashboard BEFORE ----
		int completedBefore = superPage.getCompletedDiagnosisCount();
		int pendingBefore = superPage.getPendingDiagnosisCount();

		ExtentManager.test.log(Status.INFO,
				"Super Specialist Dashboard BEFORE | Completed: " + completedBefore + " | Pending: " + pendingBefore);

		// ---- Perform Submit Flow ----
		superPage.searchStoredCaseId();
		superPage.clickView();
		superPage.selectRandomProvisionalDiagnosis();
		superPage.selectRandomOralCavitySite();
		superPage.selectRandomRecommendation();
		superPage.addNotes("Super specialist automation final review");
		superPage.submitCase();

		String actualMsg = superPage.getSubmitSuccessMessage();
		AssertHelper.assertEquals(actualMsg, "Diagnosis Submitted",
				"Super specialist submission confirmation message mismatch");

		ExtentManager.test.log(Status.PASS,
				"Super Specialist submitted diagnosis successfully for Case ID: " + usedCaseId);

		superPage.dashboard();

		// ---- Capture Dashboard AFTER ----
		int[] counts = superPage.getDashboardCountsAfterSubmit(completedBefore);

		int completedAfter = counts[0];
		int pendingAfter = counts[1];

		ExtentManager.test.log(Status.INFO,
				"Super Specialist Dashboard AFTER | Completed: " + completedAfter + " | Pending: " + pendingAfter);

		// ---- Assertions ----
		AssertHelper.assertEquals(completedAfter, completedBefore + 1, "Completed diagnosis count not increased");

		AssertHelper.assertEquals(pendingAfter, pendingBefore - 1, "Pending diagnosis count not decreased");

		ExtentManager.test.log(Status.PASS, "Task summary validated successfully for Super Specialist");
	}

	@Test(priority = 14)
	public void logoutSuperSpecialist() {

		WebLogOutPage logoutPage = new WebLogOutPage(WebDriverManager.getWebDriver());
		logoutPage.logout();
		ExtentManager.test.log(Status.PASS, "Super Specialist Logout Successful");
	}
}
