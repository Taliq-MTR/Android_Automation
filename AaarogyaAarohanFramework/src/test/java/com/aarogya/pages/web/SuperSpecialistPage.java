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
	@FindBy(xpath = "//input[@placeholder='Search Case ID']")
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
		Select s = new Select(dropdown);
		List<WebElement> options = s.getOptions();
		s.selectByIndex(new Random().nextInt(options.size() - 1) + 1);
	}

	// ---------- Actions ----------
	public void searchStoredCaseId() {
		searchBox.clear();
		searchBox.sendKeys(SpecialistPage.storedCaseId);
	}

	public void clickView() {
		viewButton.click();
	}

	public void selectRandomProvisionalDiagnosis() {
		selectRandom(provisionalDiagnosisDropdown);
	}

	public void selectRandomOralCavitySite() {
		scrollTo(oralCavitySiteDropdown);
		selectRandom(oralCavitySiteDropdown);
	}

	public void selectRandomRecommendation() {
		WebElement[] options = { urgentReferralCheckbox, additionalInvestigationCheckbox, retakePhotoCheckbox,
				quitHabitCheckbox };
		WebElement selected = options[new Random().nextInt(options.length)];
		scrollTo(selected);
		if (!selected.isSelected())
			selected.click();
	}

	public void addNotes(String notes) {
		scrollTo(notesField);
		notesField.sendKeys(notes);
	}

	public void submitCase() {
		submitButton.click();
		wait.until(ExpectedConditions.visibilityOf(submitSuccessMsg));
	}

	public String getSubmitSuccessMessage() {
		return submitSuccessMsg.getText().trim();
	}

	public void goToDashboard() {
		dashboard.click();
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
