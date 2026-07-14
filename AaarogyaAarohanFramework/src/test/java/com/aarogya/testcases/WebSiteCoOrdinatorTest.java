package com.aarogya.testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aarogya.base.AssertHelper;
import com.aarogya.base.BaseTest;
import com.aarogya.base.WebDriverManager;
import com.aarogya.pages.AddNewCasePage;
import com.aarogya.pages.web.SiteCoOrdinatorPage;
import com.aarogya.pages.web.WebLogOutPage;
import com.aarogya.pages.web.WebLoginPage;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class WebSiteCoOrdinatorTest extends BaseTest {

	@BeforeClass
	public void setWebExecutionType() {
		BaseTest.setExecutionType("WEB");
		WebDriverManager.getWebDriver();
	}

	public static String expectedFirstName;

	@Test(priority = 4)
	public void logInSiteCoordinator() {

		WebLoginPage loginPage = new WebLoginPage(WebDriverManager.getWebDriver());

		loginPage.login("sitecoordinator1", "password");

		ExtentManager.test.log(Status.PASS, "Site Co-Ordinator Login Successful");
	}

	@Test(priority = 5)
	public void verifyLatestCaseFromMobileOnWeb() {

		SiteCoOrdinatorPage sitePage = new SiteCoOrdinatorPage(WebDriverManager.getWebDriver());

		sitePage.openReports();

		sitePage.sortByRecentCasesFirst();

		String webReferenceId = sitePage.getLatestReferenceId();

		ExtentManager.test.log(Status.INFO, "Expected Reference ID (Mobile): " + AddNewCasePage.generatedReferenceId);

		ExtentManager.test.log(Status.INFO, "Actual Reference ID (Web): " + webReferenceId);

		AssertHelper.assertEquals(webReferenceId, AddNewCasePage.generatedReferenceId,
				"Reference ID mismatch between Mobile & Web");

		ExtentManager.test.log(Status.PASS, "Latest mobile case verified successfully on Web");
	}

	@Test(priority = 6)
	public void logoutSiteCoordinator() {

		WebLogOutPage logoutPage = new WebLogOutPage(WebDriverManager.getWebDriver());

		logoutPage.logout();

		ExtentManager.test.log(Status.PASS, "Site Co-Ordinator Logout Successful");
	}
}