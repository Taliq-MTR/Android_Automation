package com.aarogya.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aarogya.base.BaseTest;
import com.aarogya.base.WebDriverManager;
import com.aarogya.pages.web.WebLoginPage;

public class WebLoginSample extends BaseTest {

//    @Test(priority = 4)
    public void verifyWebLogin() {

        // Driver already opens URL automatically
        WebLoginPage loginPage =
                new WebLoginPage(WebDriverManager.getWebDriver());

        // 🔐 Sample test data
        String email = "sitecoordinator1";
        String password = "password";

        // 🔥 Perform Login
        loginPage.login(email, password);

        // ✅ Assertion 1: Verify Dashboard URL
        Assert.assertEquals(
                WebDriverManager.getWebDriver().getCurrentUrl(),
                "https://staging.arogyam-midas.iisc.ac.in/dashboard",
                "❌ Dashboard URL is incorrect"
        );

        // ✅ Assertion 2: Verify Reports & Summary text
        Assert.assertTrue(
                loginPage.isReportsAndSummaryVisible(),
                "❌ Reports & Summary text is not visible"
        );
    }
}
