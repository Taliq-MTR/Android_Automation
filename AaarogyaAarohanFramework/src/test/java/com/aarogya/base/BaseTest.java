package com.aarogya.base;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

	@BeforeSuite
	public void setup() {
		DriverManager.getDriver();
	}

	@AfterSuite
	public void tearDown() {
		DriverManager.quitDriver();   // Android
	    WebDriverManager.quitWebDriver(); // Web
	}
	
}
