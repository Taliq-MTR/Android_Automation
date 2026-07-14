package com.aarogya.pages;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aarogya.base.CommonMethods;
import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AddNewCasePage {

	private AndroidDriver driver;
	public static String generatedReferenceId;
	public static String visitedDateTime;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Add new case\"]")
	private WebElement addNewCase;

	@FindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]")
	private WebElement clcAddnewTestBttn;

	@FindBy(id = "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
	private WebElement locatnPermisn;

	@FindBy(xpath = "//android.widget.RadioButton[@text=\"Field/primary care\"]")
	private WebElement primaryCare;

	@FindBy(xpath = "//android.widget.RadioButton[@text=\"Hospital\"]")
	private WebElement hospital;

	@FindBy(xpath = "//android.widget.TextView[@resource-id=\"in.ac.iisc.arogyam:id/question\" and @text=\"Basic Information\"]")
	private WebElement basicInfo;

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]")
	private WebElement Hospital;

	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"in.ac.iisc.arogyam:id/text_input_edit_text\" and @text=\"First name *\"]")
	private WebElement firstName;

	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"in.ac.iisc.arogyam:id/text_input_edit_text\" and @text=\"Last name *\"]")
	private WebElement LastName;

	@FindBy(xpath = "//android.widget.RadioButton[@text=\"By years\"]")
	private WebElement ageByYeaars;

	@FindBy(xpath = "//android.widget.RadioButton[@text=\"By date of birth\"]")
	private WebElement ageByDOB;

	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"in.ac.iisc.arogyam:id/text_input_edit_text\" and @text=\"By years *\"]")
	private WebElement EntrAgeNmbr;

	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"in.ac.iisc.arogyam:id/text_input_edit_text\" and @text=\"dd/mm/yyyy\"]")
	private WebElement entrDOB;

	@FindBy(xpath = "//android.widget.RadioButton[@text=\"Male\"]")
	private WebElement gndrMale;

	@FindBy(xpath = "//android.widget.RadioButton[@text=\"Female\"]")
	private WebElement gndrFemale;

	@FindBy(xpath = "//android.widget.RadioButton[@text=\"Other\"]")
	private WebElement gndrOther;

	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"in.ac.iisc.arogyam:id/text_input_edit_text\" and @text=\"Primary contact number *\"]")
	private WebElement primrycontcNmbr;

	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"in.ac.iisc.arogyam:id/text_input_edit_text\" and @text=\"Secondary contact number \"]")
	private WebElement secndrycontcNmbr;

	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"in.ac.iisc.arogyam:id/text_input_edit_text\" and @text=\"House number & street \"]")
	private WebElement streetName;

	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"in.ac.iisc.arogyam:id/text_input_edit_text\" and @text=\"Village/Town/Area *\"]")
	private WebElement townNmae;

	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"in.ac.iisc.arogyam:id/text_input_edit_text\" and @text=\"Pincode \"]")
	private WebElement pinCode;

	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"in.ac.iisc.arogyam:id/text_input_edit_text\" and @text=\"Krishnagiri\"]")
	private WebElement districtname;

	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"in.ac.iisc.arogyam:id/text_input_edit_text\" and @text=\"Tamil Nadu\"]")
	private WebElement slctState;

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"in.ac.iisc.arogyam:id/pagination_next_button\"]")
	private WebElement nxtBttnBasicInfo;

	@FindBy(xpath = "//android.widget.TextView[@resource-id=\"in.ac.iisc.arogyam:id/question\" and @text=\"Habit History\"]")
	private WebElement habitHistory;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Current\"])[1]")
	private WebElement cigrateStatusCurrent;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Past\"])[1]")
	private WebElement cigrateStatusPast;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Never\"])[1]")
	private WebElement cigrateStatusNever;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Current\"])[2]")
	private WebElement smokelessTobaccoStatusCurrent;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Past\"])[2]")
	private WebElement smokelessTobaccoStatusPast;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Never\"])[2]")
	private WebElement smokelessTobaccoStatusNever;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Current\"])[3]")
	private WebElement arecaNutStatusCurrent;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Past\"])[3]")
	private WebElement arecaNutStatusPast;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Never\"])[3]")
	private WebElement arecaNutStatusNever;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Current\"])[4]")
	private WebElement alcohalStatusCurrent;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Past\"])[4]")
	private WebElement alcohalStatusPast;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Never\"])[4]")
	private WebElement alcohalStatusNever;

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"in.ac.iisc.arogyam:id/pagination_next_button\"]")
	private WebElement nxtBttnHabitHistry;

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"in.ac.iisc.arogyam:id/pagination_previous_button\"]")
	private WebElement previousBttnHabitHistry;

	@FindBy(xpath = "//android.widget.TextView[@resource-id=\"in.ac.iisc.arogyam:id/question\" and @text=\"Current Condition\"]")
	private WebElement currentCondition;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Yes\"])[1]")
	private WebElement ableToOpenMouthSlctYes;

	@FindBy(xpath = "(//android.widget.Button[@text=\"No\"])[1]")
	private WebElement ableToOpenMouthSlctNo;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Yes\"])[2]")
	private WebElement patchSlctYes;

	@FindBy(xpath = "(//android.widget.Button[@text=\"No\"])[2]")
	private WebElement patchSlctNo;

	@FindBy(xpath = "(//android.widget.Button[@text=\"Yes\"])[3]")
	private WebElement selfreportedSlctYes;

	@FindBy(xpath = "(//android.widget.Button[@text=\"No\"])[3]")
	private WebElement selfreportedSlctNo;

	@FindBy(xpath = "//android.widget.TextView[@resource-id=\"in.ac.iisc.arogyam:id/question\" and @text=\"Image Screening\"]")
	private WebElement imageScreening;

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"in.ac.iisc.arogyam:id/take_photo\"]")
	private WebElement clcAddPhoto;

	@FindBy(id = "in.ac.iisc.arogyam:id/captureButton")
	private WebElement capturePic;

	@FindBy(id = "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
	private WebElement cmraPermissionBttn;

	@FindBy(xpath = "//android.widget.TextView[@resource-id=\"in.ac.iisc.arogyam:id/photo_retake\"]")
	private WebElement clcRetake;

	@FindBy(xpath = "//android.widget.TextView[@resource-id=\"in.ac.iisc.arogyam:id/photo_select\"]")
	private WebElement clcDone;

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"in.ac.iisc.arogyam:id/review_mode_button\"]")
	private WebElement reviewBttn;

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"in.ac.iisc.arogyam:id/pagination_previous_button\"]")
	private WebElement previousBttnScreening;

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"in.ac.iisc.arogyam:id/review_mode_edit_button\"]")
	private WebElement editBttn;

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"in.ac.iisc.arogyam:id/submit_questionnaire\"]")
	private WebElement submitBttn;

	@FindBy(xpath = "//android.widget.TextView[@text=\"RECENTLY SUBMITTED\"]")
	private WebElement waitMainPage;

	@FindBy(xpath = "(//android.widget.ImageView[@content-desc='Filter'])[6]")
	private WebElement filterIcon;

	@FindBy(xpath = "//android.widget.TextView[contains(@text,'Images Left')]")
	private WebElement imagesLeftText;

	@FindBy(xpath = "//android.widget.TextView[contains(@text,'Patients Left')]")
	private WebElement patientsLeftText;

	@FindBy(xpath = "//android.widget.Button")
	private WebElement okayButton;

	@FindBy(xpath = "//android.widget.TextView[@text='Syncing data…']")
	private WebElement syncingDataPopup;

	@FindBy(xpath = "//android.widget.ImageView[@content-desc=\"Photo Preview\"]")
	private WebElement photoPreview;

	@FindBy(xpath = "//android.widget.ImageButton")
	private WebElement closeImagePreview;

	@FindBy(xpath = "//android.widget.TextView[@text=\"AI Result\"]")
	private WebElement txtAIResult;

	@FindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[2]/android.widget.Button")
	private WebElement stillReferBttn;

	@FindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[3]/android.widget.Button")
	private WebElement completeBttn;

	@FindBy(xpath = "(//android.widget.TextView[@text='Reference ID'])[1]")
	private WebElement referenceIdLabel;

	@FindBy(xpath = "//android.widget.TextView[@text='Visited']")
	private WebElement visitedLabel;

	public AddNewCasePage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean isAddNewCaseVisible() {

		try {
			return clcAddnewTestBttn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickAddTestBttn() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(clcAddnewTestBttn));
			Thread.sleep(200);
			clcAddnewTestBttn.click();
			Thread.sleep(200);
			if (driver
					.findElements(By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button"))
					.size() > 0) {
				locatnPermisn.click();
				Thread.sleep(7000);
				ExtentManager.test.log(Status.INFO, "Add New Case Button Clicked");
			}

			ExtentManager.test.log(Status.PASS, "Add New Case Button Clicked SuccessFully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "Add New Case Button Clicked Failed:" + e);
		}

	}

	public String getBasicInfoText() {
		try {
			return basicInfo.getText();
		} catch (Exception e) {
			return null;
		}
	}

	public String basicInfoPage() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			wait.until(ExpectedConditions.visibilityOf(primaryCare));
			selectRandomCareType();
			// 🔥 Create unique alphabet-only first name
			String uniqueFirstName = "Automation " + generateAlphabeticSuffix(4);

			firstName.sendKeys(uniqueFirstName);
			ExtentManager.test.log(Status.INFO, "First Name Added: " + uniqueFirstName);

			LastName.sendKeys("Script");
			ExtentManager.test.log(Status.INFO, "last name Added");
			Thread.sleep(800);
			CommonMethods.scrollDown();
			Thread.sleep(800);
			selectRandomAgeInput();

			selectRandomGender();
			CommonMethods.scrollDown();
			Thread.sleep(500);
			enterRandomContactNumbers();
			enterRandomIndianStreet();
			enterRandomIndianTownOrArea();
			enterRandomPinCode();

			CommonMethods.scrollDown();
//			districtname.clear();
//			districtname.sendKeys("Nagpur");
//			slctState.clear();
//			slctState.sendKeys("Maharashtra");
			Thread.sleep(800);
			nxtBttnBasicInfo.click();
			ExtentManager.test.log(Status.INFO, "Next button Clicked");

			ExtentManager.test.log(Status.PASS, "Details filled correctly on Basic Information Page");

			return uniqueFirstName; // <-- return name
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "Details Not filled on Basic Information Page:" + e);
			return null; // <-- REQUIRED return for catch block
		}

	}

	public String getHabitHistoryText() {
		try {
			return habitHistory.getText();
		} catch (Exception e) {
			return null;
		}
	}

	public void hbitHistoryPage() {
		try {
			selectRandomCigaretteStatus();
			selectRandomSmokelessTobaccoStatus();
			selectRandomArecaNutStatus();
			selectRandomAlcoholStatus();

			nxtBttnHabitHistry.click();
			ExtentManager.test.log(Status.INFO, "Next button Clicked");

			ExtentManager.test.log(Status.PASS, "Details filled correctly on Habit History Page");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "Details Not filled on Habit History Page:" + e);
		}

	}

	public String getCurrentConditionText() {
		try {
			return currentCondition.getText();
		} catch (Exception e) {
			return null;
		}
	}

	public void screeningPage() {
		try {
			selectRandomAbleToOpenMouth();
			selectRandomPatchStatus();
			selectRandomSelfReportedStatus();

			ExtentManager.test.log(Status.PASS, "Details filled on Screening Page for Current Condition Module");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL,
					"Details Not filled on Screening Page for Current Condition Module:" + e);
		}
	}

	public String getImageScreeningText() {
		try {
			return imageScreening.getText();
		} catch (Exception e) {
			return null;
		}
	}

	public void imageScreening() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(clcAddPhoto));
			clcAddPhoto.click();
			Thread.sleep(1000);
			ExtentManager.test.log(Status.INFO, "Click Camera Open & Camera Open");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			capturePic.click();
			Thread.sleep(2000);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			clcRetake.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			capturePic.click();
			Thread.sleep(2000);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			clcDone.click();

			ExtentManager.test.log(Status.PASS, "Image Uploaded SuccessFully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "Image Uploading Failed:" + e);
		}
	}

	public void submitForm() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			Thread.sleep(8000);
			wait.until(ExpectedConditions.visibilityOf(reviewBttn));
			reviewBttn.click();
			ExtentManager.test.log(Status.INFO, "Review Button Clicked");
			wait.until(ExpectedConditions.visibilityOf(editBttn));
			editBttn.click();
			ExtentManager.test.log(Status.INFO, "Edit Button Clicked");
			wait.until(ExpectedConditions.visibilityOf(reviewBttn));
			reviewBttn.click();
			ExtentManager.test.log(Status.INFO, "Review Button Clicked");
			wait.until(ExpectedConditions.visibilityOf(submitBttn));
			submitBttn.click();
			ExtentManager.test.log(Status.INFO, "Submit Button Clicked");
			Thread.sleep(2000);
			ExtentManager.test.log(Status.PASS, "Form Submitted");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "Form not Submitted:" + e);
		}
	}

	public void waitForPage() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));

			// Dashboard section appears
			wait.until(ExpectedConditions.visibilityOf(waitMainPage));

			ExtentManager.test.log(Status.INFO, "RECENTLY SUBMITTED section visible");

			// Optional wait for sync popup to disappear
			try {

				wait.until(ExpectedConditions.invisibilityOfElementLocated(
						By.xpath("//android.widget.TextView[contains(@text,'Syncing')]")));

				ExtentManager.test.log(Status.INFO, "Sync completed");

			} catch (Exception e) {

				ExtentManager.test.log(Status.WARNING, "Sync still running, proceeding with validation");
			}

			ExtentManager.test.log(Status.PASS, "Dashboard loaded successfully");

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Dashboard not loaded: " + e.getMessage());

			throw e;
		}
	}

	public void captureReferenceIdAndVisitedTime() {

		try {

			// Reference ID
			WebElement referenceIdElement = driver
					.findElement(AppiumBy.androidUIAutomator("new UiSelector().textMatches(\"[0-9]{8}\")"));

			generatedReferenceId = referenceIdElement.getText().trim();

			if ("Not Available".equalsIgnoreCase(generatedReferenceId)) {

				ExtentManager.test.log(Status.FAIL, "Reference ID is showing Not Available");

			} else {

				ExtentManager.test.log(Status.PASS, "Generated Reference ID : " + generatedReferenceId);
			}

			// Visited Date Time
			WebElement visitedElement = driver
					.findElement(AppiumBy.androidUIAutomator("new UiSelector().textMatches(\".*[0-9]{4}.*(AM|PM)\")"));

			visitedDateTime = visitedElement.getText().trim();

			ExtentManager.test.log(Status.INFO, "Visited Date Time : " + visitedDateTime);

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL,
					"Failed to capture Reference ID / Visited Date Time : " + e.getMessage());
		}
	}

	public String getDashboardText() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

			wait.until(ExpectedConditions.visibilityOf(waitMainPage));

			String text = waitMainPage.getText();

			ExtentManager.test.log(Status.INFO, "Dashboard Text Found: " + text);

			return text;

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Unable to find RECENTLY SUBMITTED section");

			return null;
		}
	}

	public static String generateAlphabeticSuffix(int length) {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int index = (int) (Math.random() * alphabet.length());
			sb.append(alphabet.charAt(index));
		}
		return sb.toString();
	}

	public boolean isCaseVisibleInRecentlySubmitted(String firstName) {

		try {

			ExtentManager.test.log(Status.INFO, "Searching for case: " + firstName);

			String xpath = "//android.widget.TextView[contains(@text,'" + firstName + "')]";

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));

			WebElement caseElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

			ExtentManager.test.log(Status.PASS, "Case found in Recently Submitted: " + caseElement.getText());

			return true;

		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "Case NOT found in Recently Submitted: " + firstName);

			return false;
		}
	}

	public boolean waitUntilSyncSafelyCompletes() {

		long startTime = System.currentTimeMillis();
		long maxWaitTime = 5 * 60 * 1000;

		try {
			ExtentManager.test.log(Status.INFO, "Waiting for background sync to complete (polling every 30 seconds)");

			while (System.currentTimeMillis() - startTime < maxWaitTime) {

				Thread.sleep(30_000);

				try {
					filterIcon.click();

					int imagesLeft = extractCount(imagesLeftText.getText());
					int patientsLeft = extractCount(patientsLeftText.getText());

					ExtentManager.test.log(Status.INFO,
							"Sync Status → Images: " + imagesLeft + ", Patients: " + patientsLeft);

					okayButton.click();

					if (imagesLeft == 0 && patientsLeft == 0) {
						ExtentManager.test.log(Status.PASS, "Sync completed successfully");
						return true; // ✅ SUCCESS
					}

				} catch (Exception e) {
					ExtentManager.test.log(Status.WARNING, "Popup issue during sync check, retrying safely");
				}
			}

			return false; // ❌ timeout

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Sync verification failed");
			return false;
		}
	}

	private int extractCount(String text) {
		return Integer.parseInt(text.replaceAll("\\D+", ""));
	}

	public void imageScreeningWithPermission() {

		try {
			clcAddPhoto.click();
			ExtentManager.test.log(Status.INFO, "Camera open clicked");
			Thread.sleep(2000);
			handleCameraPermissionOnce();

			capturePic.click();
			ExtentManager.test.log(Status.INFO, "Image captured");

			clcRetake.click();
			ExtentManager.test.log(Status.INFO, "Retake clicked");

			capturePic.click();
			ExtentManager.test.log(Status.INFO, "Image re-captured");

			clcDone.click();
			ExtentManager.test.log(Status.PASS, "Image uploaded successfully");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Camera flow failed: " + e.getMessage());
			throw new RuntimeException("Camera flow failed", e);
		}
	}

	public void handleCameraPermissionOnce() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

			By permissionBtn = By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button");

			// Wait briefly for popup to appear
			Thread.sleep(1000);

			int permissionCount = driver.findElements(permissionBtn).size();

			switch (permissionCount) {

			case 1:
				wait.until(ExpectedConditions.elementToBeClickable(permissionBtn)).click();
				ExtentManager.test.log(Status.INFO, "Camera permission granted");
				break;

			case 0:
				ExtentManager.test.log(Status.INFO, "Camera permission already granted");
				break;

			default:
				ExtentManager.test.log(Status.WARNING, "Unexpected permission count: " + permissionCount);
			}

		} catch (Exception e) {
			ExtentManager.test.log(Status.WARNING, "Permission handling failed: " + e.getMessage());
		}
	}

	public void imagePreview() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(photoPreview));
			photoPreview.click();
			closeImagePreview.click();
			ExtentManager.test.log(Status.PASS, "Image Preview SuccessFully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "Image Preview Failed:" + e);
		}
	}

	public void caseAIResultScreen() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			// Check if AI Result page is displayed
			wait.until(ExpectedConditions.visibilityOf(txtAIResult));

			ExtentManager.test.log(Status.INFO, "AI Result Page Visible Successfully");

			// Randomly choose one button
			if (new Random().nextBoolean()) {
				stillReferBttn.click();
				ExtentManager.test.log(Status.INFO, "'Still Refer' button clicked");
			} else {
				completeBttn.click();
				ExtentManager.test.log(Status.INFO, "'Complete' button clicked");
			}
			ExtentManager.test.log(Status.PASS, "AI Result Page Visible and Perform Action Successfully");
		} catch (Exception e) {

			ExtentManager.test.log(Status.FAIL, "AI Result Page Visible Failed:" + e);
		}
	}

	private void enterRandomContactNumbers() {

		String primaryNumber = generateIndianMobileNumber();
		String secondaryNumber;

		// ensure both numbers are different
		do {
			secondaryNumber = generateIndianMobileNumber();
		} while (secondaryNumber.equals(primaryNumber));

		primrycontcNmbr.sendKeys(primaryNumber);
		ExtentManager.test.log(Status.PASS, "Primary Contact Number Added: " + primaryNumber);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		secndrycontcNmbr.sendKeys(secondaryNumber);
		ExtentManager.test.log(Status.PASS, "Secondary Contact Number Added: " + secondaryNumber);
	}

//	Making method to select random data for more realistic data 
	private String selectRandomCareType() {

		WebElement[] options = { primaryCare, hospital };
		String[] labels = { "Field/primary care", "Hospital" };

		int randomIndex = new java.util.Random().nextInt(options.length);

		options[randomIndex].click();

		ExtentManager.test.log(Status.PASS, "Care Type Selected Randomly: " + labels[randomIndex]);

		return labels[randomIndex];
	}

	private void selectRandomAgeInput() {

		Random random = new Random();

		// 0 = By Years, 1 = By DOB
		int option = random.nextInt(2);

		if (option == 0) {
			// ---------------- BY YEARS ----------------

			int age = 31 + random.nextInt(69); // 31–99
			EntrAgeNmbr.sendKeys(String.valueOf(age));

			ExtentManager.test.log(Status.PASS, "Age selection: By Years | Age entered: " + age);

		} else {
			// ---------------- BY DATE OF BIRTH ----------------
			ageByDOB.click();

			int age = 31 + random.nextInt(69); // 31–99

			int currentYear = java.time.LocalDate.now().getYear();
			int birthYear = currentYear - age;

			int day = 1 + random.nextInt(28); // safe for all months
			int month = 1 + random.nextInt(12);

			String dob = String.format("%02d/%02d/%d", day, month, birthYear);

			entrDOB.sendKeys(dob);

			ExtentManager.test.log(Status.PASS, "Age selection: By DOB | DOB entered: " + dob + " (Age ≈ " + age + ")");
		}
	}

	private String selectRandomGender() {

		WebElement[] genderOptions = { gndrMale, gndrFemale, gndrOther };

		String[] genderLabels = { "Male", "Female", "Other" };

		int randomIndex = new java.util.Random().nextInt(genderOptions.length);

		genderOptions[randomIndex].click();

		ExtentManager.test.log(Status.PASS, "Gender selected randomly: " + genderLabels[randomIndex]);

		return genderLabels[randomIndex];
	}

	private String generateIndianMobileNumber() {

		Random random = new Random();

		// Indian mobile numbers start with 6–9
		int firstDigit = 6 + random.nextInt(4); // 6,7,8,9

		StringBuilder mobile = new StringBuilder();
		mobile.append(firstDigit);

		for (int i = 0; i < 9; i++) {
			mobile.append(random.nextInt(10));
		}

		return mobile.toString();
	}

	private void enterRandomIndianStreet() {

		String[] housePrefixes = { "H.No", "House No", "Plot No", "Flat No", "Door No", "Survey No" };

		String[] streetNames = { "MG Road", "Nehru Nagar", "Shivaji Nagar", "Gandhi Chowk", "Ambedkar Nagar",
				"Tilak Road", "Subhash Nagar", "Rajiv Nagar", "Indira Colony", "Patel Nagar", "Ashok Nagar",
				"Vivekanand Colony", "Sai Nagar", "Laxmi Nagar", "Jawahar Ward" };

		String[] streetSuffix = { "", "Main Road", "Cross Road", "Lane", "Street", "Extension" };

		Random random = new Random();

		String prefix = housePrefixes[random.nextInt(housePrefixes.length)];
		int houseNumber = 1 + random.nextInt(999);
		String street = streetNames[random.nextInt(streetNames.length)];
		String suffix = streetSuffix[random.nextInt(streetSuffix.length)];

		String fullStreet = prefix + " " + houseNumber + ", " + street + (suffix.isEmpty() ? "" : " " + suffix);

		streetName.sendKeys(fullStreet);

		ExtentManager.test.log(Status.PASS, "Street Name Entered: " + fullStreet);
	}

	private void enterRandomIndianTownOrArea() {

		String[] townsAndAreas = { "Kamptee", "Nagpur", "Wardha", "Amravati", "Chandrapur", "Yavatmal", "Gondia",
				"Bhandara", "Katol", "Umred", "Hingna", "Kalmeshwar", "Ramtek", "Mouda", "Saoner", "Butibori",
				"Manewada", "Sadar", "Civil Lines", "Trimurti Nagar", "Pratap Nagar", "Dharampeth", "Sitabuldi",
				"Mahal", "Itwari", "Koradi" };

		Random random = new Random();
		String town = townsAndAreas[random.nextInt(townsAndAreas.length)];

		townNmae.sendKeys(town);

		ExtentManager.test.log(Status.PASS, "Town / Area Entered: " + town);
	}

	private void enterRandomPinCode() {

		Random random = new Random();

		// Indian PIN code: 6 digits, first digit 1–9
		int firstDigit = 1 + random.nextInt(9); // 1–9

		StringBuilder pin = new StringBuilder();
		pin.append(firstDigit);

		for (int i = 0; i < 5; i++) {
			pin.append(random.nextInt(10));
		}

		String pinCodeValue = pin.toString();

		pinCode.sendKeys(pinCodeValue);

		ExtentManager.test.log(Status.PASS, "Pin Code Entered: " + pinCodeValue);
	}

	private void selectRandomCigaretteStatus() {

		Random random = new Random();

		int option = random.nextInt(3); // 0,1,2

		if (option == 0) {
			cigrateStatusCurrent.click();
			ExtentManager.test.log(Status.PASS, "Cigarette/Bidi Status Selected: Current");

		} else if (option == 1) {
			cigrateStatusPast.click();
			ExtentManager.test.log(Status.PASS, "Cigarette/Bidi Status Selected: Past");

		} else {
			cigrateStatusNever.click();
			ExtentManager.test.log(Status.PASS, "Cigarette/Bidi Status Selected: Never");
		}
	}

	private void selectRandomSmokelessTobaccoStatus() {

		Random random = new Random();
		int option = random.nextInt(3); // 0,1,2

		if (option == 0) {
			smokelessTobaccoStatusCurrent.click();
			ExtentManager.test.log(Status.PASS, "Smokeless Tobacco Status Selected: Current");

		} else if (option == 1) {
			smokelessTobaccoStatusPast.click();
			ExtentManager.test.log(Status.PASS, "Smokeless Tobacco Status Selected: Past");

		} else {
			smokelessTobaccoStatusNever.click();
			ExtentManager.test.log(Status.PASS, "Smokeless Tobacco Status Selected: Never");
		}
	}

	private void selectRandomArecaNutStatus() {

		Random random = new Random();
		int option = random.nextInt(3); // 0,1,2

		if (option == 0) {
			arecaNutStatusCurrent.click();
			ExtentManager.test.log(Status.PASS, "Areca Nut Status Selected: Current");

		} else if (option == 1) {
			arecaNutStatusPast.click();
			ExtentManager.test.log(Status.PASS, "Areca Nut Status Selected: Past");

		} else {
			arecaNutStatusNever.click();
			ExtentManager.test.log(Status.PASS, "Areca Nut Status Selected: Never");
		}
	}

	private void selectRandomAlcoholStatus() {

		Random random = new Random();
		int option = random.nextInt(3); // 0,1,2

		if (option == 0) {
			alcohalStatusCurrent.click();
			ExtentManager.test.log(Status.PASS, "Alcohol Status Selected: Current");

		} else if (option == 1) {
			alcohalStatusPast.click();
			ExtentManager.test.log(Status.PASS, "Alcohol Status Selected: Past");

		} else {
			alcohalStatusNever.click();
			ExtentManager.test.log(Status.PASS, "Alcohol Status Selected: Never");
		}
	}

	private void selectRandomAbleToOpenMouth() {

		Random random = new Random();
		int option = random.nextInt(2); // 0 = Yes, 1 = No

		if (option == 0) {
			ableToOpenMouthSlctYes.click();
			ExtentManager.test.log(Status.PASS, "Able To Open Mouth: Yes");
		} else {
			ableToOpenMouthSlctNo.click();
			ExtentManager.test.log(Status.PASS, "Able To Open Mouth: No");
		}
	}

	private void selectRandomPatchStatus() {

		Random random = new Random();
		int option = random.nextInt(2); // 0 = Yes, 1 = No

		if (option == 0) {
			patchSlctYes.click();
			ExtentManager.test.log(Status.PASS, "Patch Present: Yes");
		} else {
			patchSlctNo.click();
			ExtentManager.test.log(Status.PASS, "Patch Present: No");
		}
	}

	private void selectRandomSelfReportedStatus() {

		Random random = new Random();
		int option = random.nextInt(2); // 0 = Yes, 1 = No

		if (option == 0) {
			selfreportedSlctYes.click();
			ExtentManager.test.log(Status.PASS, "Self Reported Symptoms: Yes");
		} else {
			selfreportedSlctNo.click();
			ExtentManager.test.log(Status.PASS, "Self Reported Symptoms: No");
		}
	}

	private boolean isSyncInProgress() {
		try {
			return driver.findElements(By.xpath("//android.widget.TextView[contains(@text,'Uploading')]")).size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

}
