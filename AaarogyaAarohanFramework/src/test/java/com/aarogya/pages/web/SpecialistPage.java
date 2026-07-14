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

public class SpecialistPage {

	private WebDriver driver;
	private WebDriverWait wait;

	public static String storedRefrenceId;
	public static String secondOpinionRefrenceId;

	public SpecialistPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(driver, this);
	}

	// ================= LOCATORS =================

	@FindBy(xpath = "//div[contains(@class,'table-row')]")
	private List<WebElement> tableRows;

	@FindBy(xpath = "//li[contains(@class,'flex items-center justify-between')]")
	private List<WebElement> uploadRows;

	@FindBy(xpath = "//input[@placeholder='Search Reference ID']")
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

	@FindBy(xpath = "//button[contains(text(),'Second Opinion')]")
	private WebElement secondOpinionButton;

	@FindBy(xpath = "//p[contains(text(),'Sent for second opinion')]")
	private WebElement secondOpinionSubmitMsg;

	@FindBy(css = "select.text-base.text-black")
	private WebElement filterDropdown;

	@FindBy(xpath = "//div[text()='Total cases']/following-sibling::div")
	private WebElement totalCases;

	@FindBy(xpath = "//div[text()='Pending for diagnosis']/following-sibling::div")
	private WebElement pendingDiagnosis;

	@FindBy(xpath = "//div[text()='Completed diagnosis']/following-sibling::div")
	private WebElement completedDiagnosis;

	@FindBy(xpath = "//button[text()='Start diagnosis']")
	private WebElement startDiagnosis;

	@FindBy(xpath = "//p[contains(@class,'truncate') and contains(@class,'font-semibold')]")
	private WebElement RefrenceIdValue;

	@FindBy(xpath = "//p[text()='Missing images']")
	private WebElement missingImageTag;

	@FindBy(xpath = "//button[text()='Submit']")
	private WebElement caseSubmitBttn;

	@FindBy(xpath = "//p[text()='Diagnosis Submitted']")
	private WebElement diagnosisSubmitMsg;

	@FindBy(xpath = "//span[text()='Aarogya Aarohan']")
	private WebElement dashBoard;

	@FindBy(xpath = "//p[contains(text(),'Case')]")
	private WebElement caseCounter;

	@FindBy(xpath = "//div[text()='Previous']")
	private WebElement previousButton;

//	@FindBy(xpath = "(//div[contains(@class,'cursor-pointer')])[4]")
//	private WebElement clearSearchButton;

	// ================= TASK SUMMARY =================

	@FindBy(xpath = "//div[text()='Completed diagnosis']/following-sibling::div")
	private WebElement completedDiagnosisCount;

	@FindBy(xpath = "//div[text()='Pending for diagnosis']/following-sibling::div")
	private WebElement pendingDiagnosisCount;

	// ================= METHODS =================

	public void startDiagnosisAndCaptureCaseInfo() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(startDiagnosis)).click();

			wait.until(ExpectedConditions.visibilityOf(RefrenceIdValue));

			storedRefrenceId = RefrenceIdValue.getText().trim();

			ExtentManager.test.log(Status.PASS, "Diagnosis started for Refrence ID: " + storedRefrenceId);

			// 🔹 Safe optional info
			logMissingImageInfoIfPresent();

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Start Diagnosis or Refrence ID capture failed: " + e);
		}
	}

	public void logMissingImageInfoIfPresent() {
		try {

			List<WebElement> missingImages = driver.findElements(By.xpath("//p[text()='Missing images']"));

			if (!missingImages.isEmpty()) {
				ExtentManager.test.log(Status.INFO,
						"Missing Images tag is displayed for Refrence ID: " + storedRefrenceId);
			} else {
				ExtentManager.test.log(Status.INFO,
						"No Missing Images tag displayed for Refrence ID: " + storedRefrenceId);
			}

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Failed while checking Missing Images tag: " + e);
		}
	}

	// 🔥 NEW METHOD FOR UPLOAD STATUS TABLE
	public void findFirstCompletedUploadAndStoreRefrenceId() {
		try {
			for (WebElement row : uploadRows) {

				String status = row.findElement(By.xpath(".//span[6]")).getText().trim();

				if (status.equalsIgnoreCase("Completed")) {

					storedRefrenceId = row.findElement(By.xpath(".//span[2]")).getText().trim();

					ExtentManager.test.log(Status.PASS,
							"Upload Status Completed. Refrence ID Stored: " + storedRefrenceId);

					return;
				}
			}

			ExtentManager.test.log(Status.FAIL, "No row found with Upload Status = Completed");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Finding completed upload case failed: " + e);
		}
	}

	public void searchRefrenceId() {
		try {
			wait.until(ExpectedConditions.visibilityOf(searchBox)).clear();
			searchBox.sendKeys(storedRefrenceId);
			Thread.sleep(2000);
			ExtentManager.test.log(Status.PASS, "Refrence ID searched: " + storedRefrenceId);

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Search Refrence ID Failed: " + e);
		}
	}

	public void clickView() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(viewButton)).click();
			ExtentManager.test.log(Status.PASS, "Clicked View button");
			Thread.sleep(2000);
		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Click View Failed: " + e);
		}
	}

	// ================= RANDOM DROPDOWN =================

	private void selectRandomFromDropdown(WebElement dropdown, String fieldName) {
		try {
			Select s = new Select(dropdown);
			List<WebElement> options = s.getOptions();

			int randomIndex = new Random().nextInt(options.size() - 1) + 1;

			String selectedText = options.get(randomIndex).getText().trim();
			s.selectByIndex(randomIndex);
			Thread.sleep(randomIndex);
			Thread.sleep(2000);
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
		selectRandomFromDropdown(oralCavitySiteDropdown, "Oral Cavity Site");
	}

	public void captureReferenceIdForSecondOpinion() {

		try {

			// Clear old search
			wait.until(ExpectedConditions.visibilityOf(searchBox));

			searchBox.clear();

			Thread.sleep(1000);

			if (driver.findElements(By.xpath("//div[contains(@class,'cursor-pointer')]")).size() > 0) {

				driver.findElement(By.xpath("//div[contains(@class,'cursor-pointer')]")).click();

				Thread.sleep(2000);
			}

			// First row reference id
			WebElement firstReferenceId = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("(//ul/li[position()>1])[1]/span[2]")));

			secondOpinionRefrenceId = firstReferenceId.getText().trim();

			ExtentManager.test.log(Status.PASS, "Second Opinion Reference ID Stored: " + secondOpinionRefrenceId);

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Failed to capture Second Opinion Reference ID: " + e);
		}
	}

	public void searchSecondOpinionReferenceId() {

		try {

			wait.until(ExpectedConditions.visibilityOf(searchBox));

			searchBox.clear();

			searchBox.sendKeys(secondOpinionRefrenceId);

			Thread.sleep(2000);

			ExtentManager.test.log(Status.PASS, "Second Opinion Reference ID searched: " + secondOpinionRefrenceId);

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Second Opinion search failed: " + e);
		}
	}

	// ================= RANDOM CHECKBOX =================

	public void selectRandomRecommendation() {
		try {
			scrollToElement(urgentReferralCheckbox);

			WebElement[] recommendations = { urgentReferralCheckbox, additionalInvestigationCheckbox,
					retakePhotoCheckbox, quitHabitCheckbox };

			int random = new Random().nextInt(recommendations.length);

			WebElement selected = recommendations[random];
			Thread.sleep(2000);
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

			// 🔥 Scroll to notes
			scrollToElement(notesField);
			Thread.sleep(900);
			notesField.sendKeys(notes);
			Thread.sleep(2000);
			ExtentManager.test.log(Status.PASS, "Notes Added: " + notes);

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Add Notes Failed: " + e);
		}
	}

	// ================= SUBMIT FOR SECOND OPINION =================

	public void submitSecondOpinion() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(secondOpinionButton)).click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOf(secondOpinionSubmitMsg));
			Thread.sleep(2000);
			ExtentManager.test.log(Status.PASS, "Second Opinion Submitted Successfully");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Second Opinion Submit Failed: " + e);
		}
	}

	// ================= SUBMIT RECORD =================

	public void submitRecord() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(caseSubmitBttn)).click();
			Thread.sleep(15000);

			ExtentManager.test.log(Status.PASS, "Diagnosis Submitted Successfully");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Diagnosis Submit Failed: " + e);
		}
	}

	// ================= TASK SUMMARY =================

	public void selectFilterByVisibleText(String value) {
		Select select = new Select(filterDropdown);
		select.selectByVisibleText(value);
	}

	public void selectDashboardFilter(String filter) {
		try {
			Select select = new Select(filterDropdown);
			select.selectByVisibleText(filter);

			ExtentManager.test.log(Status.PASS, "Dashboard filter selected: " + filter);

			Thread.sleep(2000); // wait for refresh

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Filter selection failed for: " + filter + " Error: " + e);
		}
	}

	public void captureDashboardCounts(String filter) {
		try {

			int total = getTotalCases();
			int pending = getPendingDiagnosis();
			int completed = getCompletedDiagnosis();

			ExtentManager.test.log(Status.INFO, "Filter: " + filter + " | Total: " + total + " | Pending: " + pending
					+ " | Completed: " + completed);
			ExtentManager.test.log(Status.PASS, "Capturing dashboard counts");
		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Capturing dashboard counts failed for " + filter + ": " + e);
		}
	}

	// ================= DASHBOARD =================

	public void dashboard() {
		try {
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOf(diagnosisSubmitMsg));
			dashBoard.click();
			Thread.sleep(2000);
			ExtentManager.test.log(Status.PASS, "Came Back To Dashboard");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Failed to go back to Dashboard: " + e);
		}
	}

	public void logCurrentCaseCounter() {
		try {
			String counter = wait.until(ExpectedConditions.visibilityOf(caseCounter)).getText().trim();

			ExtentManager.test.log(Status.INFO, "Current Case Position: " + counter);

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Failed to capture case counter: " + e);
		}
	}

	public void clickPreviousCase() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(previousButton)).click();
			Thread.sleep(2000);
			ExtentManager.test.log(Status.PASS, "Clicked Previous case button");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Click Previous case failed: " + e);
		}
	}

	public String getSecondOpinionSubmitMessage() {
		return wait.until(ExpectedConditions.visibilityOf(secondOpinionSubmitMsg)).getText().trim();
	}

	public String getSubmitDiagnosisMessage() {
		return wait.until(ExpectedConditions.visibilityOf(diagnosisSubmitMsg)).getText().trim();
	}

	private void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
				element);
	}

	public int getTotalCases() {
		return Integer.parseInt(totalCases.getText().trim());
	}

	public int getPendingDiagnosis() {
		return Integer.parseInt(pendingDiagnosis.getText().trim());
	}

	public int getCompletedDiagnosis() {
		return Integer.parseInt(completedDiagnosis.getText().trim());
	}

	public int getCompletedDiagnosisCount() {
		return Integer.parseInt(wait.until(ExpectedConditions.visibilityOf(completedDiagnosis)).getText().trim());
	}

	public int getPendingDiagnosisCount() {
		return Integer.parseInt(wait.until(ExpectedConditions.visibilityOf(pendingDiagnosis)).getText().trim());
	}

	public int[] getDashboardCountsAfterSubmit(int completedBefore) {

		wait.until(ExpectedConditions
				.not(ExpectedConditions.textToBePresentInElement(completedDiagnosis, String.valueOf(completedBefore))));

		int completedAfter = getCompletedDiagnosisCount();
		int pendingAfter = getPendingDiagnosisCount();

		return new int[] { completedAfter, pendingAfter };
	}

}
