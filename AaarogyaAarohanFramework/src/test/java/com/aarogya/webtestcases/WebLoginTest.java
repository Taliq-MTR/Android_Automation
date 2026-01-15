package com.aarogya.webtestcases;

import org.testng.annotations.Test;

import com.aarogya.base.BaseTest;
import com.aarogya.base.WebDriverManager;
import com.aarogya.pages.web.WebLoginPage;

public class WebLoginTest extends BaseTest {

    @Test
    public void verifyWebLogin() {

        // Driver already opens URL automatically
        WebLoginPage loginPage =
                new WebLoginPage(WebDriverManager.getWebDriver());

        // 🔐 Sample test data
        String email = "testuser@aarogyam.com";
        String password = "Test@123";

        // 🔥 Perform Login
        loginPage.login(email, password);
    }
}
