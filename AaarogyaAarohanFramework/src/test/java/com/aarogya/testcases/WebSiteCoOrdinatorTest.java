package com.aarogya.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aarogya.base.WebDriverManager;
import com.aarogya.pages.web.SiteCoOrdinatorPage;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class WebSiteCoOrdinatorTest {

	// 🔗 Value set from Android test
	public static String expectedFirstName;

	@Test(priority = 5)
	public void verifyLatestCaseFromMobileOnWeb() {

		SiteCoOrdinatorPage sitePage = new SiteCoOrdinatorPage(WebDriverManager.getWebDriver());

		sitePage.openReports();
		sitePage.sortByRecentCasesFirst();

		String webFirstName = sitePage.getLatestFirstName();
		String webLastName = sitePage.getLatestLastName();
		String webCaseId = sitePage.getLatestCaseId();

		ExtentManager.test.log(Status.INFO, "Expected First Name (Mobile): " + expectedFirstName);
		ExtentManager.test.log(Status.INFO, "Actual First Name (Web): " + webFirstName);

		Assert.assertEquals(webFirstName, expectedFirstName, "First name mismatch between Mobile & Web");

		ExtentManager.test.log(Status.PASS, "Latest mobile case verified successfully on Web");
	}
}
