package com.aarogya.pages.web;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

public class SuperSpecialistPage {

	private WebDriver driver;
	private WebDriverWait wait;

	public SuperSpecialistPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(driver, this);
	}

	// ================= LOCATORS =================

	@FindBy(xpath = "//input[@placeholder='Search Case ID']")
	private WebElement searchBox;

	@FindBy(xpath = "//button[text()='View'][1]")
	private WebElement viewButton;

	@FindBy(xpath = "(//select[contains(@class,'custom-select')])[1]")
	private WebElement provisionalDiagnosisDropdown;

	@FindBy(xpath = "(//select[contains(@class,'custom-select')])[2]")
	private WebElement oralCavitySiteDropdown;

	@FindBy(xpath = "//h6[text()='Recommendation']")
	private WebElement scrollTillRecommendatn;

	@FindBy(xpath = "//label[text()='Urgent referral to the hospital']/preceding-sibling::input")
	private WebElement urgentReferralCheckbox;

	@FindBy(xpath = "//label[text()='Refer to hospital for additional investigation']/preceding-sibling::input")
	private WebElement additionalInvestigationCheckbox;

	@FindBy(xpath = "//label[text()='Retake photo']/preceding-sibling::input")
	private WebElement retakePhotoCheckbox;

	@FindBy(xpath = "//label[text()='Advice to quit the habit']/preceding-sibling::input")
	private WebElement quitHabitCheckbox;

	@FindBy(xpath = "//textarea[@placeholder='Add..']")
	private WebElement notesField;

	@FindBy(xpath = "//button[contains(text(),'Submit')]")
	private WebElement submitButton;

	@FindBy(xpath = "//p[contains(text(),'Diagnosis Submitted')]")
	private WebElement submitSuccessMsg;

	// ================= SCROLL HELPER =================

	private void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
				element);
	}

	// ================= SEARCH =================

	public void searchStoredCaseId() {
		try {
			wait.until(ExpectedConditions.visibilityOf(searchBox)).clear();
			searchBox.sendKeys(SpecialistPage.storedCaseId);
			ExtentManager.test.log(Status.PASS, "Super Specialist searched Case ID: " + SpecialistPage.storedCaseId);

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Super Specialist search failed: " + e);
		}
	}

	public void clickView() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(viewButton));
			viewButton.click();

			ExtentManager.test.log(Status.PASS, "Super Specialist clicked View button");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Super Specialist View click failed: " + e);
		}
	}

	// ================= RANDOM DROPDOWNS =================

	private void selectRandomFromDropdown(WebElement dropdown, String fieldName) {
		try {
			Select s = new Select(dropdown);
			List<WebElement> options = s.getOptions();

			int randomIndex = new Random().nextInt(options.size() - 1) + 1;

			String selectedText = options.get(randomIndex).getText().trim();
			s.selectByIndex(randomIndex);

			ExtentManager.test.log(Status.PASS, fieldName + " selected randomly: " + selectedText);

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, fieldName + " random selection failed: " + e);
		}
	}

	public void selectRandomProvisionalDiagnosis() {
		wait.until(ExpectedConditions.visibilityOf(provisionalDiagnosisDropdown));
		selectRandomFromDropdown(provisionalDiagnosisDropdown, "Provisional Diagnosis");
	}

	public void selectRandomOralCavitySite() {
		wait.until(ExpectedConditions.visibilityOf(oralCavitySiteDropdown));
		scrollToElement(oralCavitySiteDropdown);
		selectRandomFromDropdown(oralCavitySiteDropdown, "Oral Cavity Site");
	}

	// ================= RANDOM CHECKBOX =================

	public void selectRandomRecommendation() {
		try {
			scrollToElement(urgentReferralCheckbox);

			WebElement[] recommendations = { urgentReferralCheckbox, additionalInvestigationCheckbox,
					retakePhotoCheckbox, quitHabitCheckbox };

			int random = new Random().nextInt(recommendations.length);

			WebElement selected = recommendations[random];

			if (!selected.isSelected()) {
				selected.click();
			}

			String selectedId = selected.getAttribute("id");

			ExtentManager.test.log(Status.PASS, "Recommendation selected randomly: " + selectedId);

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Random Recommendation selection failed: " + e);
		}
	}

	// ================= NOTES =================

	public void addNotes(String notes) {
		try {
			wait.until(ExpectedConditions.visibilityOf(notesField));

			scrollToElement(notesField);

			notesField.sendKeys(notes);

			ExtentManager.test.log(Status.PASS, "Super Specialist added notes: " + notes);

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Super Specialist add notes failed: " + e);
		}
	}

	// ================= SUBMIT =================

	public void submitCase() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(submitButton));
			submitButton.click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOf(submitSuccessMsg));
			
			ExtentManager.test.log(Status.PASS, "Super Specialist submitted the case");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Super Specialist submit failed: " + e);
		}
	}

	public String getSubmitSuccessMessage() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(submitSuccessMsg)).getText().trim();
		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Fetching submit success message failed: " + e);
			return null;
		}
	}
}
