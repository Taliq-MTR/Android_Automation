package com.aarogya.testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aarogya.base.AssertHelper;
import com.aarogya.base.BaseTest;
import com.aarogya.base.DriverManager;
import com.aarogya.pages.AddDraftCasePage;

public class Phase2AddDraftTestCase extends BaseTest {
	
	@BeforeClass
	public void setAndroidExecutionType() {

	    BaseTest.setExecutionType("ANDROID");

	    DriverManager.getDriver();
	}
	@Test(priority = 17)
	public void addDraftCase() {
		AddDraftCasePage draft = new AddDraftCasePage(DriverManager.getDriver());

		// Step 1: Create Draft Case and capture name
		String expectedName = draft.addDraftCases();
		draft.clickBackBttn();

		// Step 2: Read the name from draft list
		String actualName = draft.getDraftName(expectedName);

		// Step 3: Assert name matches EXACTLY like your previous pattern
		AssertHelper.softAssertEquals(actualName, expectedName, "Draft Case name did not match expected!");

		AssertHelper.assertAll();
	}

	@Test(priority = 18)
	public void dltingDraftCase() throws InterruptedException {

		AddDraftCasePage dltCase = new AddDraftCasePage(DriverManager.getDriver());

		dltCase.addDraftCases();
		dltCase.clickBackBttn();

		Thread.sleep(4000);
		// Validate delete button appears initially
		AssertHelper.softAssertEquals(dltCase.isDeleteButtonVisible() ? "VISIBLE" : "NOT_VISIBLE", "VISIBLE",
				"Delete button is NOT visible before deleting draft!");

		// 🔥 Delete drafts until delete button disappears
		int maxAttempts = 10;
		int attempts = 0;

		while (dltCase.isDeleteButtonVisible() && attempts < maxAttempts) {
			dltCase.dltDraftCases();
			Thread.sleep(2000); // UI refresh
			attempts++;
		}

		// Final validation: delete button should be gone
		AssertHelper.softAssertEquals(dltCase.isDeleteButtonVisible() ? "VISIBLE" : "NOT_VISIBLE", "NOT_VISIBLE",
				"Delete button still visible — draft not deleted properly!");

		AssertHelper.assertAll();
	}

	@Test(priority = 19)
	public void submitDraftAndVerifyName() {
		AddDraftCasePage draft = new AddDraftCasePage(DriverManager.getDriver());

		// create draft and capture returned name
		String createdName = draft.addDraftCases();

		// go back to drafts list / cases page as your flow requires
		draft.clickBackBttn();

		// submit the draft (your existing method)
		draft.registeredDraftCasesPH2();

		// -- NEW VALIDATION: Check Recently Submitted contains the created case
		boolean isVisible = draft.isCaseVisibleInRecentlySubmitted(createdName);

		AssertHelper.softAssertEquals(isVisible ? "VISIBLE" : "NOT_VISIBLE", "VISIBLE",
				"Recently Submitted case name not visible!");

		AssertHelper.assertAll();
	}

}
