package com.aarogya.pages.web;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	// ---------- Case Flow ----------

	@FindBy(xpath = "//input[@placeholder='Search Reference ID']")
	private WebElement searchBox;

	@FindBy(xpath = "//button[text()='View'][1]")
	private WebElement viewButton;

	@FindBy(xpath = "(//select[contains(@class,'custom-select')])[1]")
	private WebElement provisionalDiagnosisDropdown;

	@FindBy(xpath = "(//select[contains(@class,'custom-select')])[2]")
	private WebElement oralCavitySiteDropdown;

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

	@FindBy(xpath = "//span[text()='Aarogya Aarohan']")
	private WebElement dashboard;

	// ---------- Dashboard ----------

	@FindBy(css = "select.text-base.text-black")
	private WebElement filterDropdown;

	@FindBy(xpath = "//div[text()='Pending for diagnosis']/following-sibling::div")
	private WebElement pendingDiagnosis;

	@FindBy(xpath = "//div[text()='Completed diagnosis']/following-sibling::div")
	private WebElement completedDiagnosis;

	// ---------- Helpers ----------

	private void scrollTo(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
	}

	private void selectRandom(WebElement dropdown) {
		Select select = new Select(dropdown);
		List<WebElement> options = select.getOptions();

		if (options.size() > 1) {
			select.selectByIndex(new Random().nextInt(options.size() - 1) + 1);
		}
	}

	// ---------- Actions ----------

	public void searchStoredCaseId() {

		try {

			String caseId = SpecialistPage.secondOpinionRefrenceId;

			searchBox.clear();
			searchBox.sendKeys(caseId);

			ExtentManager.test.log(Status.INFO, "Searching Second Opinion Reference ID: " + caseId);

			Thread.sleep(2000);

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Failed to search Reference ID: " + e.getMessage());
		}
	}

	public void clickView() {

		try {

			wait.until(ExpectedConditions.elementToBeClickable(viewButton));
			viewButton.click();

			ExtentManager.test.log(Status.PASS, "View button clicked successfully");

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Failed to click View button: " + e.getMessage());
		}
	}

	public void selectRandomProvisionalDiagnosis() {

		selectRandom(provisionalDiagnosisDropdown);

		ExtentManager.test.log(Status.INFO, "Random Provisional Diagnosis selected");
	}

	public void selectRandomOralCavitySite() {

		scrollTo(oralCavitySiteDropdown);

		selectRandom(oralCavitySiteDropdown);

		ExtentManager.test.log(Status.INFO, "Random Oral Cavity Site selected");
	}

	public void selectRandomRecommendation() {

		WebElement[] options = { urgentReferralCheckbox, additionalInvestigationCheckbox, retakePhotoCheckbox,
				quitHabitCheckbox };

		WebElement selected = options[new Random().nextInt(options.length)];

		scrollTo(selected);

		if (!selected.isSelected()) {
			selected.click();
		}

		ExtentManager.test.log(Status.INFO, "Random Recommendation selected");
	}

	public void addNotes(String notes) {

		scrollTo(notesField);

		notesField.clear();
		notesField.sendKeys(notes);

		ExtentManager.test.log(Status.INFO, "Notes added successfully");
	}

	public void submitCase() {

		try {

			scrollTo(submitButton);

			submitButton.click();

			wait.until(ExpectedConditions.visibilityOf(submitSuccessMsg));

			ExtentManager.test.log(Status.PASS, "Diagnosis submitted successfully");

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Diagnosis submission failed: " + e.getMessage());
		}
	}

	public String getSubmitSuccessMessage() {

		return submitSuccessMsg.getText().trim();
	}

	public void goToDashboard() {

		dashboard.click();

		ExtentManager.test.log(Status.INFO, "Navigated back to Dashboard");
	}

	// ---------- Dashboard ----------

	public void selectDashboardFilter(String filter) {

		String oldText = completedDiagnosis.getText().trim();

		new Select(filterDropdown).selectByVisibleText(filter);

		try {

			wait.until(
					ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(completedDiagnosis, oldText)));

		} catch (TimeoutException e) {

			ExtentManager.test.log(Status.INFO, "No count change for filter: " + filter + " (valid behavior)");
		}
	}

	public int getPendingDiagnosis() {

		return Integer.parseInt(pendingDiagnosis.getText().trim());
	}

	public int getCompletedDiagnosis() {

		return Integer.parseInt(completedDiagnosis.getText().trim());
	}
}