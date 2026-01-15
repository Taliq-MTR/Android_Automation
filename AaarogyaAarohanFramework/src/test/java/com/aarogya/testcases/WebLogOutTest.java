package com.aarogya.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aarogya.base.BaseTest;
import com.aarogya.base.WebDriverManager;
import com.aarogya.pages.web.WebLogOutPage;

public class WebLogOutTest extends BaseTest {

	@Test(priority = 6)
	public void verifyWebLogout() {

		// Precondition:
		// User must already be logged in before this test runs

		WebLogOutPage logOutPage = new WebLogOutPage(WebDriverManager.getWebDriver());

		// Perform logout
		logOutPage.logout();

		// Validate logout
		Assert.assertTrue(logOutPage.isLogoutSuccessful(), "❌ Logout failed – Login page not displayed");
	}

}
