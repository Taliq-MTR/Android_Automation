package com.aarogya.testcases;

import org.testng.annotations.Test;
import com.aarogya.base.BaseTest;
import com.aarogya.base.DriverManager;
import com.aarogya.pages.HomePage;

public class HomeTestCase extends BaseTest {

    @Test(priority = 1)
    public void selectCityTest() {

        HomePage home = new HomePage(DriverManager.getDriver());

        // check if onboarding page is visible
        if (home.isCitySelectionPageDisplayed()) {
            System.out.println("City selection page detected → running setup.");
            home.clickCity();
        } else {
            System.out.println("City page NOT visible → skipping test as already configured.");
        }

    }
}
