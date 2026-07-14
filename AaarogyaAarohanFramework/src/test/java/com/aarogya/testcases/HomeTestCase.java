package com.aarogya.testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aarogya.base.BaseTest;
import com.aarogya.base.DriverManager;
import com.aarogya.pages.HomePage;

public class HomeTestCase extends BaseTest {

	@BeforeClass
	public void setAndroidExecutionType() {

		BaseTest.setExecutionType("ANDROID");
		DriverManager.getDriver();
	}

	@Test(priority = 1)
	public void selectCityTest() {

		HomePage home = new HomePage(DriverManager.getDriver());

		if (home.isCitySelectionPageDisplayed()) {

			System.out.println("Site Selection Page Found.");

			home.selectStagingServer();

			System.out.println("Server configured successfully.");

		} else {

			System.out.println("Site already configured. Continuing execution.");

		}
	}
}