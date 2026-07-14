package com.aarogya.base;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class AssertHelper {

    private static ThreadLocal<SoftAssert> softAssert =
            ThreadLocal.withInitial(SoftAssert::new);

    // ---------- Soft Assertion ----------

    public static void softAssertTrue(
            boolean condition,
            String successMsg,
            String failureMsg) {

        if (condition) {
            System.out.println("✔ " + successMsg);
        } else {
            System.out.println("❌ " + failureMsg);
        }

        softAssert.get().assertTrue(condition, failureMsg);
    }

    public static void softAssertEquals(
            String actual,
            String expected,
            String failureMsg) {

        softAssert.get().assertEquals(
                actual,
                expected,
                failureMsg);
    }

    public static void softAssertEquals(
            boolean actual,
            boolean expected,
            String failureMsg) {

        softAssert.get().assertEquals(
                actual,
                expected,
                failureMsg);
    }

    // ---------- Hard Assertions ----------

    public static void assertTrue(
            boolean condition,
            String failureMessage) {

        Assert.assertTrue(condition, failureMessage);
    }

    public static void assertEquals(
            String actual,
            String expected,
            String failureMessage) {

        Assert.assertEquals(
                actual,
                expected,
                failureMessage);
    }

    public static void assertEquals(
            int actual,
            int expected,
            String message) {

        Assert.assertEquals(
                actual,
                expected,
                message);
    }

    // ---------- Assert All ----------

    public static void assertAll() {

        try {

            softAssert.get().assertAll();

        } finally {

            // IMPORTANT
            softAssert.remove();
        }
    }

    // ---------- Reset ----------

    public static void resetSoftAssert() {

        softAssert.set(new SoftAssert());
    }
}