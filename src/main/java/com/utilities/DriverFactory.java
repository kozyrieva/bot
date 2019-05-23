package com.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverFactory {

    // This method return a WebDriver object
    public static WebDriver open(String browserType){
        // Create WebDriver
        if ("chrome".equalsIgnoreCase(browserType)) {
            System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Software\\chromedriver.exe");
            return new ChromeDriver();
        } else if ("firefox".equalsIgnoreCase(browserType)) {
            System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\Software\\geckodriver.exe");
            return new FirefoxDriver();
        } else {
            System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Software\\IEDriverServer.exe");
            return new InternetExplorerDriver();
        }
    }
}
