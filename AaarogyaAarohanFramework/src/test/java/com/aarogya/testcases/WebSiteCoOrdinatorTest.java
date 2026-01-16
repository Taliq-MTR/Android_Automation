package com.aarogya.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aarogya.base.WebDriverManager;
import com.aarogya.pages.web.SiteCoOrdinatorPage;
import com.aarogya.pages.web.WebLogOutPage;
import com.aarogya.pages.web.WebLoginPage;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class WebSiteCoOrdinatorTest {

	// 🔗 Value set from Android test
	public static String expectedFirstName;

	@Test(priority = 4)
	public void verifyLatestCaseFromMobileOnWeb() {

		// 🔐 LOGIN
		WebLoginPage loginPage = new WebLoginPage(WebDriverManager.getWebDriver());
		loginPage.login("sitecoordinator1", "password");
		ExtentManager.test.log(Status.INFO, "Site Co-Ordinator Login SuccessFull");

		SiteCoOrdinatorPage sitePage = new SiteCoOrdinatorPage(WebDriverManager.getWebDriver());

		sitePage.openReports();
		sitePage.sortByRecentCasesFirst();

		String webFirstName = sitePage.getLatestFirstName();

		ExtentManager.test.log(Status.INFO, "Expected First Name (Mobile): " + expectedFirstName);
		ExtentManager.test.log(Status.INFO, "Actual First Name (Web): " + webFirstName);

		Assert.assertEquals(webFirstName, expectedFirstName, "First name mismatch between Mobile & Web");

		ExtentManager.test.log(Status.PASS, "Latest mobile case verified successfully on Web");

	}
	
	@Test(priority = 5)
	public void logoutSiteCoordinator() {

	    WebLogOutPage logoutPage =
	            new WebLogOutPage(WebDriverManager.getWebDriver());

	    logoutPage.logout();

	    ExtentManager.test.log(Status.INFO, "Site Co-Ordinator Logout Successful");
	}
}
