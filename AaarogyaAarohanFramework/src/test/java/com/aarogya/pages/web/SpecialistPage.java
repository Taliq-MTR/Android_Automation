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

	public static String storedCaseId;

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

	@FindBy(xpath = "//button[contains(text(),'Second Opinion')]")
	private WebElement secondOpinionButton;

	@FindBy(xpath = "//p[contains(text(),'Sent for second opinion')]")
	private WebElement secondOpinionSubmitMsg;

	// ================= METHODS =================

//    public void findFirstCompletedCaseAndStoreId() {
//        try {
//            for (WebElement row : tableRows) {
//
//                String status = row.findElement(By.xpath(".//div[6]"))
//                        .getText().trim();
//
//                if (status.equalsIgnoreCase("Completed")) {
//
//                    storedCaseId = row.findElement(By.xpath(".//div[2]"))
//                            .getText().trim();
//
//                    ExtentManager.test.log(Status.PASS,
//                            "Completed Case Found with ID: " + storedCaseId);
//                    return;
//                }
//            }
//
//            ExtentManager.test.log(Status.FAIL,
//                    "No Completed case found in table");
//
//        } catch (Exception e) {
//            ExtentManager.test.log(Status.FAIL,
//                    "Finding Completed Case Failed: " + e);
//        }
//    }

	// 🔥 NEW METHOD FOR UPLOAD STATUS TABLE
	public void findFirstCompletedUploadAndStoreCaseId() {
		try {
			for (WebElement row : uploadRows) {

				String status = row.findElement(By.xpath(".//span[6]")).getText().trim();

				if (status.equalsIgnoreCase("Completed")) {

					storedCaseId = row.findElement(By.xpath(".//span[2]")).getText().trim();

					ExtentManager.test.log(Status.PASS, "Upload Status Completed. Case ID Stored: " + storedCaseId);

					return;
				}
			}

			ExtentManager.test.log(Status.FAIL, "No row found with Upload Status = Completed");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Finding completed upload case failed: " + e);
		}
	}

	public void searchCaseId() {
		try {
			wait.until(ExpectedConditions.visibilityOf(searchBox)).clear();
			searchBox.sendKeys(storedCaseId);
			Thread.sleep(2000);
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

	// ================= RANDOM DROPDOWN =================

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

			// 🔥 Scroll to notes
			scrollToElement(notesField);

			notesField.sendKeys(notes);

			ExtentManager.test.log(Status.PASS, "Notes Added: " + notes);

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Add Notes Failed: " + e);
		}
	}

	// ================= SUBMIT =================

	public void submitSecondOpinion() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(secondOpinionButton)).click();
			wait.until(ExpectedConditions.visibilityOf(secondOpinionSubmitMsg));
			
			ExtentManager.test.log(Status.PASS, "Second Opinion Submitted Successfully");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Second Opinion Submit Failed: " + e);
		}
	}

	public String getSecondOpinionSubmitMessage() {
		return wait.until(ExpectedConditions.visibilityOf(secondOpinionSubmitMsg)).getText().trim();
	}

	private void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
				element);
	}

}
