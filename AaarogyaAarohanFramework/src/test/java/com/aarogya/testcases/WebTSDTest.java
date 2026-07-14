package com.aarogya.testcases;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aarogya.base.BaseTest;
import com.aarogya.base.WebDriverManager;
import com.aarogya.pages.web.DentistPage;
import com.aarogya.pages.web.WebLogOutPage;
import com.aarogya.pages.web.WebLoginPage;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class WebTSDTest extends BaseTest {

	@BeforeClass

	public void setWebExecutionType() {

		BaseTest.setExecutionType("WEB");

		WebDriverManager.getWebDriver();

	}

	@Test(priority = 7)

	public void loginTSD() {

		new WebLoginPage(WebDriverManager.getWebDriver()).login("TSD1", "password");

	}

	@Test(priority = 8)

	public void verifyDashboardCountsForAllFiltersAfterSubmit() {

		DentistPage tsdpage = new DentistPage(WebDriverManager.getWebDriver());

		String[] filters = { "Today", "Last 7 days", "Last 28 days", "Last 90 days", "All time" };

		Map<String, int[]> before = new HashMap<>();

		// ---- BEFORE ----

		for (String filter : filters) {

			tsdpage.selectDashboardFilter(filter);

			before.put(filter, new int[] { tsdpage.getPendingDiagnosis(), tsdpage.getCompletedDiagnosis() });

		}

		// ---- SUBMIT ----

		// 🔹 Start Diagnosis
		tsdpage.startDiagnosisAndCaptureCaseInfo();

		String caseId = tsdpage.storedRefrenceId;
		Assert.assertNotNull(caseId, "Case ID was not captured after start diagnosis");

		ExtentManager.test.log(Status.INFO, "Diagnosis Case ID: " + caseId);

		// 🔹 Continue Diagnosis Process
		tsdpage.selectRandomProvisionalDiagnosis();
		tsdpage.selectRandomOralCavitySite();
		tsdpage.selectRandomRecommendation();
		tsdpage.addNotes("By TSD diagnosis submission through Automation");

		// 🔹 Submit
		tsdpage.submitRecord();

		// 🔹 Capture case position
		tsdpage.logCurrentCaseCounter();

		// 🔹 Go back to previous case
		tsdpage.clickPreviousCase();

		// 🔹 Verify submit message still present
		String actualMsg = tsdpage.getSubmitDiagnosisMessage();

		ExtentManager.test.log(Status.INFO, "Diagnosis Submit Message after previous: " + actualMsg);

		Assert.assertEquals(actualMsg, "Diagnosis Submitted",
				"Diagnosis submit confirmation message mismatch after previous navigation");

		// ---- Back to Dashboard ----
		tsdpage.dashboard();

		// ---- AFTER ----

		for (String filter : filters) {

			tsdpage.selectDashboardFilter(filter);

			int pendingAfter = tsdpage.getPendingDiagnosis();

			int completedAfter = tsdpage.getCompletedDiagnosis();

			int pendingBefore = before.get(filter)[0];

			int completedBefore = before.get(filter)[1];

			ExtentManager.test.log(Status.INFO, "Filter: " + filter + " | Before P:" + pendingBefore + " C:"

					+ completedBefore + " | After P:" + pendingAfter + " C:" + completedAfter);

			if (filter.equalsIgnoreCase("All time")) {

				// 🔥 STRICT VALIDATION

				Assert.assertEquals(completedAfter, completedBefore + 1, "Completed count mismatch for All time");

				Assert.assertEquals(pendingAfter, pendingBefore - 1, "Pending count mismatch for All time");

			} else {

				// ✅ CONDITIONAL VALIDATION

				if (completedAfter != completedBefore || pendingAfter != pendingBefore) {

					Assert.assertEquals(completedAfter, completedBefore + 1,

							"Completed mismatch for filter: " + filter);

					Assert.assertEquals(pendingAfter, pendingBefore - 1, "Pending mismatch for filter: " + filter);

				} else {

					ExtentManager.test.log(Status.INFO,

							"No change for filter: " + filter + " (case not in date range)");

				}

			}

		}

	}

	@Test(priority = 9)

	public void logoutTSD() {

		new WebLogOutPage(WebDriverManager.getWebDriver()).logout();

	}

}