package com.aarogya.testcases;

import org.testng.annotations.Test;

import com.aarogya.base.AssertHelper;
import com.aarogya.base.BaseTest;
import com.aarogya.base.DriverManager;
import com.aarogya.pages.LogOutPage;

public class LogOutTestCase extends BaseTest {

	@Test(priority = 11) //, dependsOnMethods = "verifyWebLogout"
	public void logOutTest() {

		LogOutPage logOut = new LogOutPage(DriverManager.getDriver());

		// STEP 1: Open Profile and validate
		logOut.openProfile();
		AssertHelper.softAssertEquals(logOut.getProfileHeaderText(), "Profile", "Profile page text did not match!");

		// STEP 2: Perform logout and validate login screen
		logOut.loggingOut();
		AssertHelper.softAssertEquals(logOut.getLoginPageText(), "Username", "Login page not visible after logout!");

		// Collect all soft assertions
		AssertHelper.assertAll();
	}

}
