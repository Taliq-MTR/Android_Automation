package com.aarogya.testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aarogya.base.AssertHelper;
import com.aarogya.base.BaseTest;
import com.aarogya.base.DriverManager;
import com.aarogya.pages.LoginPage;

public class LoginTestCase extends BaseTest {

	
	@BeforeClass
	public void setAndroidExecutionType() {
	    BaseTest.setExecutionType("ANDROID");
	    DriverManager.getDriver();
	}
	
	@Test(priority = 2)
	public void loginTest() {

		LoginPage login = new LoginPage(DriverManager.getDriver());
		login.clickLogin();

		AssertHelper.softAssertEquals(login.getDashboardText(), "RECENTLY SUBMITTED",
				"Dashboard text did not match expected!");// --- Collect all soft assertions ---
	    AssertHelper.assertAll();
		
	}
	

}
