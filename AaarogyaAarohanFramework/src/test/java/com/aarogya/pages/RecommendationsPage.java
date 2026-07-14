package com.aarogya.pages;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aarogya.reports.ExtentManager;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class RecommendationsPage {
	private AndroidDriver driver;

	@FindBy(xpath = "//android.widget.FrameLayout[@content-desc=\"Recommendations\"]")
	private WebElement recommendatnBttn;

	@FindBy(xpath = "(//android.widget.TextView[@text='NOT CONTACTED']/following::android.widget.TextView)[1]/..")
	private WebElement firstNotContactedCard;

	@FindBy(xpath = "(//android.view.ViewGroup[@clickable='true' and .//android.widget.TextView[contains(@text,'Phone')]])[2]")
	private WebElement firstNotRespondedCard;

	@FindBy(xpath = "//android.widget.TextView[@text=\"NOT RESPONDED\"]")
	private WebElement notRespondedArrow;

	@FindBy(xpath = "(//android.widget.TextView[@text='AGREED FOR FOLLOW UP']/following::android.view.ViewGroup)[1]")
	private WebElement firstAgreedFollowUpCard;

	@FindBy(xpath = "(//android.view.ViewGroup[@clickable='true'])[1]")
	private WebElement firstCompletedCard;

	@FindBy(xpath = "//android.widget.TextView[@text=\"CHANGE STATUS\"]")
	private WebElement changeStatus;

	@FindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View[3]/android.widget.Button")
	private WebElement changeStatusCancelBttn;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Not responded\"]")
	private WebElement notRespondedBttn;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Didn't agree for follow up\"]")
	private WebElement didntAgreeForFollowUpBttn;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Agreed, follow up not done\"]")
	private WebElement AgreedFollowUpNotDoneBttn;

	@FindBy(xpath = "//android.widget.TextView[@text=\"Follow up done\"]")
	private WebElement FollowUpDoneBttn;

	@FindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View[4]/android.widget.Button")
	private WebElement updateStatus;

	@FindBy(xpath = "//android.widget.TextView[@text=\"CLOSE\"]")
	private WebElement closeBttn;

	@FindBy(xpath = "//android.widget.HorizontalScrollView/android.view.View[2]")
	private WebElement pendingBttn;

	@FindBy(xpath = "//android.widget.HorizontalScrollView/android.view.View[3]")
	private WebElement completedBttn;

	@FindBy(xpath = "(//android.widget.TextView[@text='NOT CONTACTED']/following::android.widget.TextView[not(contains(@text,'Phone'))])[1]")
	private WebElement firstNotContactedName;

	@FindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[3]")
	private WebElement clcSearch;

	@FindBy(xpath = "//android.widget.EditText")
	private WebElement entrValueInSearch;

	@FindBy(xpath = "//android.widget.TextView[contains(@text,'Screened on')]")
	private WebElement screenedTimeText;

	@FindBy(xpath = "//android.view.View[@content-desc=\"Drawer Menu\"]")
	private WebElement backBttn;

	public RecommendationsPage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickRecommendationsBttn() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOf(recommendatnBttn));
			Thread.sleep(5000);
			recommendatnBttn.click();

			ExtentManager.test.log(Status.PASS, "Recommendations Button Clicked SuccessFully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "Recommendations Button Clicked Failed:" + e);
		}

	}

	public void clickPendingTab() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		By pending = By.xpath("//android.widget.TextView[@text='Pending']");

		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(pending));

		try {
			element.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("mobile: clickGesture",
					Map.of("elementId", ((RemoteWebElement) element).getId()));
		}
	}

	public void cancelStatusChange() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			firstNotContactedCard.click();

			wait.until(ExpectedConditions.visibilityOf(changeStatus));

			changeStatusCancelBttn.click();

			// 🔥 wait for popup to close
			wait.until(ExpectedConditions.invisibilityOf(changeStatus));

			ExtentManager.test.log(Status.PASS, "Cancel working");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Cancel failed: " + e);
		}
	}

	public String statusChangeToPendingAsNotResponded() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			firstNotContactedCard.click();

			// 🔥 Wait for screened time to appear
			wait.until(ExpectedConditions.visibilityOf(screenedTimeText));

			// 🔥 Capture time HERE
			String screenedTime = getScreenedTime();
			ExtentManager.test.log(Status.INFO, "Captured Screened Time: " + screenedTime);
//			System.out.println("Captured Screened Time: " + screenedTime);

			wait.until(ExpectedConditions.visibilityOf(changeStatus));

			notRespondedBttn.click();
			updateStatus.click();

			wait.until(ExpectedConditions.invisibilityOf(changeStatus));

			ExtentManager.test.log(Status.PASS, "Moved to Not Responded");

			return screenedTime; // 🔥 RETURN IT

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Failed: " + e);
			return null;
		}
	}

	public void notRespondedStatusChangeToAgrred(String caseName) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

			clickPendingTab(); // use separate method

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='NOT RESPONDED']")));

			// 🔥 Click using dynamic name
			clickCaseByName(caseName);

			wait.until(ExpectedConditions.visibilityOf(changeStatus));

			AgreedFollowUpNotDoneBttn.click();
			updateStatus.click();
			wait.until(ExpectedConditions.invisibilityOf(changeStatus));
			Thread.sleep(4000);
			ExtentManager.test.log(Status.INFO, "Status changed to Agreed for: " + caseName);
			ExtentManager.test.log(Status.PASS,
					"The Case Recommendation Changed To \"Agreed,Follow Up Done\" SuccessFully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL,
					"he Case Recommendation Changed To \"Agreed,Follow Up Done\" Failed:" + e);
		}
	}

	public void statusChangedToCompleted(String caseName) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(notRespondedArrow));
			notRespondedArrow.click();
			Thread.sleep(400);
			// 🔥 Click using dynamic name
			clickCaseByName(caseName);
			wait.until(ExpectedConditions.visibilityOf(changeStatus));
			FollowUpDoneBttn.click();
			Thread.sleep(4000);
			wait.until(ExpectedConditions.elementToBeClickable(updateStatus));
			updateStatus.click();
			Thread.sleep(4000);
			ExtentManager.test.log(Status.PASS, "The Case Goes To Completed SuccessFully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentManager.test.log(Status.FAIL, "The Case Goes To Completed is Failed:" + e);
		}

	}

	public void checkCaseInCompleted(String caseName, String screenedTime) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

			// 🔹 Step 1: Open Completed tab
			wait.until(ExpectedConditions.elementToBeClickable(completedBttn)).click();

			// 🔹 Step 2: Click Search
			wait.until(ExpectedConditions.elementToBeClickable(clcSearch)).click();

			// 🔹 Step 3: Enter search
			WebElement input = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.EditText")));

			input.clear();
			input.sendKeys(caseName);
			ExtentManager.test.log(Status.INFO, "Searching for: " + caseName);
//			System.out.println("Searching for: " + caseName);

			Thread.sleep(2000); // as per your instruction

			// 🔹 Extract time
			String timeOnly = screenedTime;
			if (screenedTime != null && screenedTime.contains(" ")) {
				timeOnly = screenedTime.substring(screenedTime.lastIndexOf(" ") + 1);
			}
			ExtentManager.test.log(Status.INFO, "Matching Time: " + timeOnly);
//			System.out.println("Matching Time: " + timeOnly);

			// 🔥 Step 4: Loop first 5 records
			for (int i = 1; i <= 5; i++) {

				try {

					String nameXpath = "(//android.widget.TextView[@text='" + caseName + "'])[" + i + "]";
					WebElement name = driver.findElement(By.xpath(nameXpath));
					ExtentManager.test.log(Status.INFO, "Checking index: " + i);
//					System.out.println("Checking index: " + i);

					// 🔥 Click name (simple & stable)
					try {
						name.click();
					} catch (Exception e) {
						int x = name.getLocation().getX() + name.getSize().getWidth() / 2;
						int y = name.getLocation().getY() + name.getSize().getHeight() / 2;

						((JavascriptExecutor) driver).executeScript("mobile: clickGesture", Map.of("x", x, "y", y));
					}

					// 🔹 Wait for detail screen
					wait.until(ExpectedConditions.visibilityOf(closeBttn));

					// 🔥 Check time on detail screen
					boolean matched = driver
							.findElements(By.xpath("//android.widget.TextView[contains(@text,'" + timeOnly + "')]"))
							.size() > 0;

					if (matched) {
						ExtentManager.test.log(Status.INFO, "✅ Correct record found at index: " + i);
//						System.out.println("✅ Correct record found at index: " + i);

						closeBttn.click();
						return; // ✅ STOP HERE
					} else {
						ExtentManager.test.log(Status.INFO, "❌ Time not matched, checking next");
//						System.out.println("❌ Time not matched, checking next");

						closeBttn.click(); // go back
						Thread.sleep(1000);
					}

				} catch (Exception e) {
					ExtentManager.test.log(Status.INFO, "No element at index: " + i);
//					System.out.println("No element at index: " + i);
				}
			}

			throw new RuntimeException("❌ Matching record NOT found");

		} catch (Exception e) {
			ExtentManager.test.log(Status.FAIL, "Completed verification failed: " + e);
		}
	}

	public String getScreenedTime() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement timeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//android.widget.TextView[contains(@text,'Screened on')]/following::android.widget.TextView[1]")));

			String time = timeElement.getText();
			ExtentManager.test.log(Status.INFO, "Captured Screened Time: " + time);
//			System.out.println("Captured Screened Time: " + time);

			return time;

		} catch (Exception e) {
			return null;
		}
	}

	public boolean isRecommendationsPageLoaded() {
		try {
			return recommendatnBttn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isChangeStatusClosed() {
		try {
			return !changeStatus.isDisplayed();
		} catch (Exception e) {
			// If element not found → means closed
			return true;
		}
	}

	public boolean isCompletedCardVisible() {
		try {
			return firstCompletedCard.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickCaseByName(String caseName) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
				+ ".scrollIntoView(new UiSelector().text(\"" + caseName + "\"))"));

		By locator = By.xpath("//android.widget.TextView[@text='" + caseName + "']");

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		element.click(); // NO ancestor
	}

	public String getFirstNotContactedName() {
		return driver
				.findElement(By.xpath(
						"(//android.widget.TextView[@text='NOT CONTACTED']/following::android.widget.TextView)[1]"))
				.getText();
	}

	public String getFirstNotRespondedName() {
		return driver
				.findElement(By.xpath(
						"(//android.widget.TextView[@text='NOT RESPONDED']/following::android.widget.TextView)[1]"))
				.getText();
	}

	public String getFirstAgreedName() {
		return driver
				.findElement(By.xpath(
						"(//android.widget.TextView[contains(@text,'AGREED')]/following::android.widget.TextView)[1]"))
				.getText();
	}

	public String getSuccessMessage() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			// 🔹 Try Toast (Android native)
			WebElement toast = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Toast")));

			String message = toast.getText();
			ExtentManager.test.log(Status.INFO, "Toast Message: " + message);
//			System.out.println("Toast Message: " + message);
			return message;

		} catch (Exception e1) {

			try {
				// 🔹 Try normal UI text (Snackbar / Label)
				WebElement msgElement = driver.findElement(By.xpath("//*[contains(@text,'Status updated')]"));

				String message = msgElement.getText();
				ExtentManager.test.log(Status.INFO, "UI Message: " + message);
//				System.out.println("UI Message: " + message);
				return message;

			} catch (Exception e2) {

				// 🔥 FINAL FALLBACK (VERY IMPORTANT)
				ExtentManager.test.log(Status.INFO, "No success message found");
//				System.out.println("No success message found");
				return "NO_MESSAGE";
			}
		}
	}

	public boolean isCasePresent(String caseName) {
		return driver.findElements(By.xpath("//android.widget.TextView[@text='" + caseName + "']")).size() > 0;
	}

	public void searchCase(String caseName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		clcSearch.click();

		WebElement input = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.EditText")));

		input.clear();
		input.sendKeys(caseName);

		try {
			driver.hideKeyboard();
		} catch (Exception e) {
		}
		try {
			Thread.sleep(1500);
		} catch (Exception e) {
		}

	}

	public boolean isCaseVisible(String caseName) {
		return driver.findElements(By.xpath("//android.widget.TextView[@text='" + caseName + "']")).size() > 0;
	}

	public void clickCompletedTab() {
		completedBttn.click();
	}

	public void clickBackBttn() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			wait.until(ExpectedConditions.visibilityOf(backBttn));

			wait.until(ExpectedConditions.elementToBeClickable(backBttn));

			backBttn.click();

			ExtentManager.test.log(Status.PASS, "Back button clicked successfully");

		} catch (Exception e) {

			ExtentManager.test.log(Status.WARNING, "Normal click failed. Trying Android BACK key.");

			driver.navigate().back();
		}
	}

	public boolean isCaseCompleted(String caseName) {

		try {
			return driver.findElements(By.xpath("//android.widget.TextView[@text='" + caseName + "']"
					+ "/following::android.widget.TextView[@text='Completed'][1]")).size() > 0;

		} catch (Exception e) {
			return false;
		}
	}

}
