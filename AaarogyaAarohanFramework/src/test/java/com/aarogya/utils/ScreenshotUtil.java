package com.aarogya.utils;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aarogya.base.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {

    public static String captureScreenshot(String testName) {

        // Create unique screenshot name
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String filePath = System.getProperty("user.dir") +
                "/reports/screenshots/" + testName + "_" + timestamp + ".png";

        File src = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(src, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }
}
