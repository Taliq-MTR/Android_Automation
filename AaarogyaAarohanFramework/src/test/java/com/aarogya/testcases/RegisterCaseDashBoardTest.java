package com.aarogya.testcases;

import org.testng.annotations.Test;

import com.aarogya.base.AssertHelper;
import com.aarogya.base.BaseTest;
import com.aarogya.base.DriverManager;

import com.aarogya.pages.RegisterCaseDashBoardPage;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class RegisterCaseDashBoardTest extends BaseTest {

	@Test(priority = 7) //, dependsOnMethods = "verifyWebLogout"
	public void verifyDashboardCountUpdatesAfterRegisteringCase() throws Exception {

		RegisterCaseDashBoardPage dash = new RegisterCaseDashBoardPage(DriverManager.getDriver());

		// Step 1: Navigate to Dashboard & Validate
		dash.clickDshboard();
		AssertHelper.softAssertEquals(dash.getDashboardTitleText(), "Total screened cases",
				"Dashboard title mismatch!");

		// Step 2: Capture BEFORE counts
		int beforeTotal = dash.getTotalScreenedCasesCount();
		int beforeToday = dash.getTodayCount();
		int beforeWeek = dash.getThisWeekCount();
		int beforeMonth = dash.getThisMonthCount();

		ExtentManager.test.log(Status.INFO, "BEFORE → Total:" + beforeTotal + " | Today:" + beforeToday + " | Week:"
				+ beforeWeek + " | Month:" + beforeMonth);

		// Step 3: Register a New Case using POM Methods
		dash.addNewCaseBttn();
		AssertHelper.softAssertEquals(dash.getBasicInfoText(), "Basic Information", "Basic Info page did NOT open!");

		dash.fillBasicInfoFromDashboard();
		AssertHelper.softAssertEquals(dash.getHabitHistoryText(), "Habit History", "Habit History page did NOT open!");

		dash.fillHabitHistoryFromDashboard();
		AssertHelper.softAssertEquals(dash.getCurrentConditionText(), "Current Condition",
				"Current Condition page did NOT open!");

		dash.fillCurrentConditionFromDashboard();
		AssertHelper.softAssertEquals(dash.getImageScreeningText(), "Image Screening",
				"Image Screening page did NOT open!");

		dash.fillImageScreeningFromDashboard();
		dash.submitCaseFromDashboard();
		Thread.sleep(10000);
		
		AssertHelper.softAssertEquals(dash.getDashboardTitleText(), "Total screened cases",
				"Dashboard title mismatch!");

		Thread.sleep(10000);

		// Step 5: Capture AFTER counts
		int afterTotal = dash.getTotalScreenedCasesCount();
		int afterToday = dash.getTodayCount();
		int afterWeek = dash.getThisWeekCount();
		int afterMonth = dash.getThisMonthCount();

		ExtentManager.test.log(Status.INFO, "AFTER → Total:" + afterTotal + " | Today:" + afterToday + " | Week:"
				+ afterWeek + " | Month:" + afterMonth);

		// Step 6: Assertions for Increment
		AssertHelper.softAssertEquals(String.valueOf(afterTotal), String.valueOf(beforeTotal + 1),
				"Total screened cases did NOT increase by 1!");

		AssertHelper.softAssertEquals(String.valueOf(afterToday), String.valueOf(beforeToday + 1),
				"Today cases did NOT increase by 1!");

		AssertHelper.softAssertEquals(String.valueOf(afterWeek), String.valueOf(beforeWeek + 1),
				"This week cases did NOT increase by 1!");

		AssertHelper.softAssertEquals(String.valueOf(afterMonth), String.valueOf(beforeMonth + 1),
				"This month cases did NOT increase by 1!");

		// Final assert
		AssertHelper.assertAll();
	}

}
//@Test(priority = 7)
//public void addingTestCasefromDashBoard() {
//
//	RegisterCaseDashBoardPage dash = new RegisterCaseDashBoardPage(DriverManager.getDriver());
//
//	// 1️⃣ Click Dashboard
//	dash.clickDshboard();
//	AssertHelper.softAssertEquals(dash.getDashboardTitleText(), "Total screened cases",
//			"Dashboard header did not match!");
//
//	// 2️⃣ Click Add New Case
//	dash.addNewCaseBttn();
//	AssertHelper.softAssertEquals(dash.getBasicInfoText(), "Basic Information",
//			"Basic Information Page did not open!");
//
//	// 3️⃣ Basic Info
//	dash.fillBasicInfoFromDashboard();
//	AssertHelper.softAssertEquals(dash.getHabitHistoryText(), "Habit History", "Habit History Page did not open!");
//
//	// 4️⃣ Habit History
//	dash.fillHabitHistoryFromDashboard();
//	AssertHelper.softAssertEquals(dash.getCurrentConditionText(), "Current Condition",
//			"Current Condition Page did not open!");
//
//	// 5️⃣ Current Condition
//	dash.fillCurrentConditionFromDashboard();
//	AssertHelper.softAssertEquals(dash.getImageScreeningText(), "Image Screening",
//			"Image Screening Page did not open!");
//
//	// 6️⃣ Image Screening
//	dash.fillImageScreeningFromDashboard();
//
//	// 7️⃣ Submit
//	dash.submitCaseFromDashboard();
//	AssertHelper.softAssertEquals(dash.getDashboardText(), "RECENTLY SUBMITTED",
//			"Submission Dashboard text did not match!");
//
//	AssertHelper.assertAll();
//}
