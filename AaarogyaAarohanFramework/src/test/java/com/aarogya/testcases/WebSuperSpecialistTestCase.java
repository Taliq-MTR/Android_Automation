package com.aarogya.testcases;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aarogya.base.BaseTest;
import com.aarogya.base.WebDriverManager;
import com.aarogya.pages.web.SpecialistPage;
import com.aarogya.pages.web.SuperSpecialistPage;
import com.aarogya.pages.web.WebLogOutPage;
import com.aarogya.pages.web.WebLoginPage;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class WebSuperSpecialistTestCase extends BaseTest {

	@BeforeClass
	public void setWebExecutionType() {
		BaseTest.setExecutionType("WEB");
		WebDriverManager.getWebDriver();
	}

	@Test(priority = 12)
	public void loginSuperSpecialist() {

		WebLoginPage loginPage = new WebLoginPage(WebDriverManager.getWebDriver());

		loginPage.login("sspecialist1", "password");

		ExtentManager.test.log(Status.PASS, "Super Specialist Login Successful");
	}

	@Test(priority = 13)
	public void verifyDashboardCountsForAllFiltersAfterSubmit() {

		SuperSpecialistPage page = new SuperSpecialistPage(WebDriverManager.getWebDriver());

		String[] filters = { "Today", "Last 7 days", "Last 28 days", "Last 90 days", "All time" };

		Map<String, int[]> before = new HashMap<>();

		// ================= BEFORE COUNTS =================

		for (String filter : filters) {

			page.selectDashboardFilter(filter);

			before.put(filter, new int[] { page.getPendingDiagnosis(), page.getCompletedDiagnosis() });

			ExtentManager.test.log(Status.INFO, "Before Counts | Filter: " + filter + " | Pending: "
					+ page.getPendingDiagnosis() + " | Completed: " + page.getCompletedDiagnosis());
		}

		// ================= PROCESS SECOND OPINION CASE =================

		String caseId = SpecialistPage.secondOpinionRefrenceId;

		ExtentManager.test.log(Status.INFO, "Second Opinion Reference ID Received: " + caseId);

		Assert.assertNotNull(caseId, "Second Opinion Reference ID is NULL");

		page.searchStoredCaseId();

		page.clickView();

		page.selectRandomProvisionalDiagnosis();

		page.selectRandomOralCavitySite();

		page.selectRandomRecommendation();

		page.addNotes("Super Specialist review for Reference ID: " + caseId);

		page.submitCase();

		String submitMessage = page.getSubmitSuccessMessage();

		ExtentManager.test.log(Status.INFO, "Submit Message: " + submitMessage);

		Assert.assertEquals(submitMessage, "Diagnosis Submitted");

		ExtentManager.test.log(Status.PASS, "Diagnosis submitted successfully for Reference ID: " + caseId);

		page.goToDashboard();

		// ================= AFTER COUNTS =================

		for (String filter : filters) {

			page.selectDashboardFilter(filter);

			int pendingAfter = page.getPendingDiagnosis();

			int completedAfter = page.getCompletedDiagnosis();

			int pendingBefore = before.get(filter)[0];

			int completedBefore = before.get(filter)[1];

			ExtentManager.test.log(Status.INFO,
					"Filter: " + filter + " | Before Pending=" + pendingBefore + " Completed=" + completedBefore
							+ " | After Pending=" + pendingAfter + " Completed=" + completedAfter);

			if (filter.equalsIgnoreCase("All time")) {

				Assert.assertEquals(completedAfter, completedBefore + 1, "Completed count mismatch for All Time");

				Assert.assertEquals(pendingAfter, pendingBefore - 1, "Pending count mismatch for All Time");

			} else {

				if (completedAfter != completedBefore || pendingAfter != pendingBefore) {

					Assert.assertEquals(completedAfter, completedBefore + 1,
							"Completed count mismatch for filter: " + filter);

					Assert.assertEquals(pendingAfter, pendingBefore - 1,
							"Pending count mismatch for filter: " + filter);

				} else {

					ExtentManager.test.log(Status.INFO,
							"No count change for filter: " + filter + " (Case not present in this date range)");
				}
			}
		}

		ExtentManager.test.log(Status.PASS, "Dashboard count validation completed successfully");
	}

	@Test(priority = 14)
	public void logoutSuperSpecialist() {

		WebLogOutPage logoutPage = new WebLogOutPage(WebDriverManager.getWebDriver());

		logoutPage.logout();

		ExtentManager.test.log(Status.PASS, "Super Specialist Logout Successful");
	}
}