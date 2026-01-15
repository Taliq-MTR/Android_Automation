package com.aarogya.base;

import java.time.Duration;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.FluentWait;

import io.appium.java_client.android.AndroidDriver;

public class CommonMethods {
	
	public static void scrollDown()
	{
		AndroidDriver driver = DriverManager.getDriver();
	final Dimension size = driver.manage().window().getSize();
	 
	final int startX = size.width / 2;
	final int startY = (int) (size.height * 0.8);
	final int endY   = (int) (size.height * 0.3);
	 
	final PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
	final Sequence swipeDown = new Sequence(finger, 1);
	 
	// Move to start point
	swipeDown.addAction(finger.createPointerMove(Duration.ZERO,
	        PointerInput.Origin.viewport(), startX, startY));
	 
	// Finger down
	swipeDown.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
	 
	// Move to end point (Scroll Down)
	swipeDown.addAction(finger.createPointerMove(Duration.ofMillis(600),
	        PointerInput.Origin.viewport(), startX, endY));
	 
	// Finger up
	swipeDown.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
	 
	driver.perform(Arrays.asList(swipeDown));
	 
	}
	
	 // 🔥 Reusable Fluent Wait Click method
    public static void fluentClick(AndroidDriver driver, WebElement element) {
        FluentWait<AndroidDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(ElementClickInterceptedException.class);

        fluentWait.until(new Function<AndroidDriver, Boolean>() {
            @Override
            public Boolean apply(AndroidDriver driver) {
                try {
                    element.click();
                    return true; // success
                } catch (Exception e) {
                    return false; // try again
                }
            }
        });
    }

}
