package com.aarogya.pages.web;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class SpecialistPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public static String storedCaseId;

    public SpecialistPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // ================= LOCATORS =================

    // Table rows
    @FindBy(xpath = "//div[contains(@class,'table-row')]")
    private List<WebElement> tableRows;

    // Search box
    @FindBy(xpath = "//input[@placeholder='Search']")
    private WebElement searchBox;

    // View button
    @FindBy(xpath = "//button[normalize-space()='View']")
    private WebElement viewButton;

    // Drop-down & Fields
    @FindBy(id = "provisionalDiagnosis")
    private WebElement provisionalDiagnosisDropdown;

    @FindBy(id = "oralCavitySite")
    private WebElement oralCavitySiteDropdown;

    @FindBy(id = "recommendation")
    private WebElement recommendationDropdown;

    @FindBy(id = "notes")
    private WebElement notesField;

    @FindBy(xpath = "//button[normalize-space()='Second Opinion']")
    private WebElement secondOpinionButton;

    // ================= METHODS =================

    public void findFirstCompletedCaseAndStoreId() {
        try {
            for (WebElement row : tableRows) {

                String status = row.findElement(By.xpath(".//div[6]")).getText().trim();

                if (status.equalsIgnoreCase("Completed")) {

                    storedCaseId = row.findElement(By.xpath(".//div[2]")).getText().trim();

                    ExtentManager.test.log(Status.PASS,
                            "Completed Case Found with ID: " + storedCaseId);
                    return;
                }
            }

            ExtentManager.test.log(Status.FAIL, "No Completed case found in table");

        } catch (Exception e) {
            ExtentManager.test.log(Status.FAIL, "Finding Completed Case Failed: " + e);
        }
    }

    public void searchCaseId() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchBox)).clear();
            searchBox.sendKeys(storedCaseId);
            searchBox.sendKeys(Keys.ENTER);

            ExtentManager.test.log(Status.PASS, "Case ID searched: " + storedCaseId);

        } catch (Exception e) {
            ExtentManager.test.log(Status.FAIL, "Search Case ID Failed: " + e);
        }
    }

    public void clickView() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(viewButton)).click();
            ExtentManager.test.log(Status.PASS, "Clicked View button");

        } catch (Exception e) {
            ExtentManager.test.log(Status.FAIL, "Click View Failed: " + e);
        }
    }

    public void selectProvisionalDiagnosis(String value) {
        try {
            Select s = new Select(provisionalDiagnosisDropdown);
            s.selectByVisibleText(value);

            ExtentManager.test.log(Status.PASS, "Provisional Diagnosis Selected: " + value);

        } catch (Exception e) {
            ExtentManager.test.log(Status.FAIL, "Provisional Diagnosis Selection Failed: " + e);
        }
    }

    public void selectOralCavitySite(String value) {
        try {
            Select s = new Select(oralCavitySiteDropdown);
            s.selectByVisibleText(value);

            ExtentManager.test.log(Status.PASS, "Oral Cavity Site Selected: " + value);

        } catch (Exception e) {
            ExtentManager.test.log(Status.FAIL, "Oral Cavity Site Selection Failed: " + e);
        }
    }

    public void selectRecommendation(String value) {
        try {
            Select s = new Select(recommendationDropdown);
            s.selectByVisibleText(value);

            ExtentManager.test.log(Status.PASS, "Recommendation Selected: " + value);

        } catch (Exception e) {
            ExtentManager.test.log(Status.FAIL, "Recommendation Selection Failed: " + e);
        }
    }

    public void addNotes(String notes) {
        try {
            notesField.sendKeys(notes);
            ExtentManager.test.log(Status.PASS, "Notes Added");

        } catch (Exception e) {
            ExtentManager.test.log(Status.FAIL, "Add Notes Failed: " + e);
        }
    }

    public void submitSecondOpinion() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(secondOpinionButton)).click();
            ExtentManager.test.log(Status.PASS, "Second Opinion Submitted Successfully");

        } catch (Exception e) {
            ExtentManager.test.log(Status.FAIL, "Second Opinion Submit Failed: " + e);
        }
    }
}
