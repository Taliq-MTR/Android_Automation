package com.aarogya.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aarogya.base.AssertHelper;
import com.aarogya.base.BaseTest;
import com.aarogya.base.WebDriverManager;
import com.aarogya.pages.web.SpecialistPage;
import com.aarogya.pages.web.WebLogOutPage;
import com.aarogya.pages.web.WebLoginPage;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class WebSpecialistTestCase  {

	@Test(priority = 7)
	public void logInSpecialist() {
		// 🔐 LOGIN
		WebLoginPage loginPage = new WebLoginPage(WebDriverManager.getWebDriver());
		loginPage.login("specialist-1", "password");
		ExtentManager.test.log(Status.PASS, "Specialist Login SuccessFull");
	}

	@Test(priority = 8)
	public void verifySpecialistSubmitCaseForSecondOpinon() {

		SpecialistPage specialistPage = new SpecialistPage(WebDriverManager.getWebDriver());

		// 🔹 Step 1: Capture completed case
		specialistPage.findFirstCompletedUploadAndStoreCaseId();

		String usedCaseId = SpecialistPage.storedCaseId;

		ExtentManager.test.log(Status.INFO, "Case ID picked for Specialist: " + usedCaseId);

		AssertHelper.assertTrue(usedCaseId != null, "No completed case found to process");

		// 🔹 Step 2: Search case
		specialistPage.searchCaseId();

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

	@Test(priority = 9)
	public void logoutSpecialist() {

		WebLogOutPage logoutPage = new WebLogOutPage(WebDriverManager.getWebDriver());
		logoutPage.logout();
		ExtentManager.test.log(Status.PASS, "Specialist Logout Successful");
	}
}
