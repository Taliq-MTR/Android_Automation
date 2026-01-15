package com.aarogya.base;



import org.testng.asserts.SoftAssert;
import org.testng.Assert;

public class AssertHelper {

    private static SoftAssert softAssert = new SoftAssert();

    // ---------- Soft Assertion (Non-Blocking) ----------
    public static void softAssertTrue(boolean condition, String successMsg, String failureMsg) {
        if (condition) {
            System.out.println("✔ " + successMsg);
        } else {
            System.out.println("❌ " + failureMsg);
        }
        softAssert.assertTrue(condition, failureMsg);
    }

    public static void softAssertEquals(String actual, String expected, String failureMsg) {
        softAssert.assertEquals(actual, expected, failureMsg);
    }

    // ---------- Hard Assertion (Stops test execution) ----------
    public static void assertTrue(boolean condition, String failureMessage) {
        Assert.assertTrue(condition, failureMessage);
    }

    public static void assertEquals(String actual, String expected, String failureMessage) {
        Assert.assertEquals(actual, expected, failureMessage);
    }

    // ---------- Call at the end of the test ----------
    public static void assertAll() {
        softAssert.assertAll();
    }
}
