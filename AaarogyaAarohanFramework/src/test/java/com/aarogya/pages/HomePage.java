package com.aarogya.pages;



import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private AndroidDriver driver;

    public HomePage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//android.widget.EditText[contains(@text,'KLE Society')]")
    private WebElement txtSelectCity;

    @FindBy(xpath = "//android.widget.TextView[@text=\"AIIMS, Delhi and NCI, Jhajjar\"]")
    private WebElement SlctCity1;
    
    @FindBy(xpath = "//android.widget.Button")
    private WebElement ClickContbtn;
  
    
    public boolean isCitySelectionPageDisplayed() {
        try {
            return txtSelectCity.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    
    public void clickCity() {
        txtSelectCity.click();
        SlctCity1.click();
        ClickContbtn.click();
    }

}

