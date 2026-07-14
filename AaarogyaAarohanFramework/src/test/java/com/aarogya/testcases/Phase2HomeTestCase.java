package com.aarogya.testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aarogya.base.BaseTest;
import com.aarogya.base.DriverManager;
import com.aarogya.pages.Phase2HomePage;

public class Phase2HomeTestCase {

	
	@BeforeClass
	public void setAndroidExecutionType() {
	    BaseTest.setExecutionType("ANDROID");
	    DriverManager.getDriver();
	}
	
	@Test(priority = 1)
	public void selectCityTest() {

		Phase2HomePage home = new Phase2HomePage(DriverManager.getDriver());

		// Check if Site Selection page is displayed
		if (home.isCitySelectionPageDisplayed()) {

			System.out.println("Site selection page detected → Selecting Staging Phase2, TANUH server.");

			home.selectStagingServer2();

			System.out.println("Successfully selected Staging Phase2, TANUH server.");

		} else {

			System.out.println("Site already configured → Skipping server selection.");

		}
	}
}
