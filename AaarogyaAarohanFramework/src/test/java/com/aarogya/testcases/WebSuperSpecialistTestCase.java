package com.aarogya.testcases;

import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aarogya.base.BaseTest;
import com.aarogya.base.WebDriverManager;
import com.aarogya.pages.web.SuperSpecialistPage;
import com.aarogya.pages.web.WebLogOutPage;
import com.aarogya.pages.web.WebLoginPage;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class WebSuperSpecialistTestCase extends BaseTest {

	@Test(priority = 12)
	public void loginSuperSpecialist() {
		new WebLoginPage(WebDriverManager.getWebDriver()).login("sspecialist1", "password");
	}

	@Test(priority = 13)
	public void verifyDashboardCountsForAllFiltersAfterSubmit() {

		SuperSpecialistPage page = new SuperSpecialistPage(WebDriverManager.getWebDriver());

		String[] filters = { "Today", "Last 7 days", "Last 28 days", "Last 90 days", "All time" };

		Map<String, int[]> before = new HashMap<>();

		// ---- BEFORE ----
		for (String filter : filters) {
			page.selectDashboardFilter(filter);
			before.put(filter, new int[] { page.getPendingDiagnosis(), page.getCompletedDiagnosis() });
		}

		// ---- SUBMIT ----
		page.searchStoredCaseId();
		page.clickView();
		page.selectRandomProvisionalDiagnosis();
		page.selectRandomOralCavitySite();
		page.selectRandomRecommendation();
		page.addNotes("Super specialist review");
		page.submitCase();

		Assert.assertEquals(page.getSubmitSuccessMessage(), "Diagnosis Submitted");

		page.goToDashboard();

		// ---- AFTER ----
		for (String filter : filters) {

			page.selectDashboardFilter(filter);

			int pendingAfter = page.getPendingDiagnosis();
			int completedAfter = page.getCompletedDiagnosis();

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

	@Test(priority = 14)
	public void logoutSuperSpecialist() {
		new WebLogOutPage(WebDriverManager.getWebDriver()).logout();
	}
}
