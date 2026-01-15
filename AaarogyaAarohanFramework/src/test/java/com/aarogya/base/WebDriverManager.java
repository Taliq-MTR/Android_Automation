package com.aarogya.base;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverManager {

	private static WebDriver webDriver;

	public static WebDriver getWebDriver() {
		if (webDriver == null) {
			webDriver = new ChromeDriver();
			webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			webDriver.manage().window().maximize();
			webDriver.get("https://staging.arogyam-midas.iisc.ac.in/dashboard");
		}
		return webDriver;
	}

	public static void quitWebDriver() {
		if (webDriver != null) {
			webDriver.quit();
			webDriver = null;
		}
	}
}
