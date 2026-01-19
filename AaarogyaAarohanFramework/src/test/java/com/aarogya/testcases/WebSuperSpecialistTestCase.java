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

public class WebSuperSpecialistTestCase  {

	@Test(priority = 10)
	public void logInSuperSpecialist() {
		// 🔐 LOGIN
		WebLoginPage loginPage = new WebLoginPage(WebDriverManager.getWebDriver());
		loginPage.login("sspecialist1", "password");
		ExtentManager.test.log(Status.PASS, "Super Specialist Login SuccessFull");
	}

	@Test(priority = 11)
	public void verifySuperSpecialistSubmitCase() {

		SuperSpecialistPage superPage = new SuperSpecialistPage(WebDriverManager.getWebDriver());

		String usedCaseId = SpecialistPage.storedCaseId;

		ExtentManager.test.log(Status.INFO, "Case ID picked for Super Specialist: " + usedCaseId);

		AssertHelper.assertTrue(usedCaseId != null, "No Case ID available for Super Specialist flow");

		// 🔹 Step 1: Search Case ID
		superPage.searchStoredCaseId();

		// 🔹 Step 2: Click View
		superPage.clickView();

		// 🔹 Step 3: Select Provisional Diagnosis
		superPage.selectRandomProvisionalDiagnosis();

		// 🔹 Step 4: Select Oral Cavity Site
		superPage.selectRandomOralCavitySite();

		// 🔹 Step 5: Select Recommendation (scroll handled internally now)
		superPage.selectRandomRecommendation();

		// 🔹 Step 6: Add Notes
		superPage.addNotes("Super specialist automation final review");

		// 🔹 Step 7: Submit Case
		superPage.submitCase();

		// 🔹 Step 8: Assertion on success message
		String actualMsg = superPage.getSubmitSuccessMessage();

		ExtentManager.test.log(Status.INFO, "Super Specialist Submit Message: " + actualMsg);

		AssertHelper.assertEquals(actualMsg, "Diagnosis Submitted",
				"Super specialist submission confirmation message mismatch");

		ExtentManager.test.log(Status.PASS, "Super Specialist successfully submitted case for Case ID: " + usedCaseId);
	}

	@Test(priority = 12)
	public void logoutSuperSpecialist() {

		WebLogOutPage logoutPage = new WebLogOutPage(WebDriverManager.getWebDriver());
		logoutPage.logout();
		ExtentManager.test.log(Status.PASS, "Super Specialist Logout Successful");
	}
}
