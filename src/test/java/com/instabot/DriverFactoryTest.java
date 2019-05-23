package com.instabot;

import com.utilities.DriverFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverFactoryTest {

    @Test
    public void openChromeDriver() {
        WebDriver webDriver = DriverFactory.open("chrome");
        Assert.assertEquals(ChromeDriver.class, webDriver.getClass());
    }

    @Test
    public void openFirefoxDriver() {
        WebDriver webDriver = DriverFactory.open("firefox");
        Assert.assertEquals(FirefoxDriver.class, webDriver.getClass());
    }

    // TODO - fix the test
//    @Test
//    public void openNull() {
//        WebDriver webDriver  = DriverFactory.open(null);
//        Assert.assertEquals(InternetExplorerDriver.class, webDriver.getClass());
//    }

    // TODO - fix the test
//    @Test
//    public void openInternetExplorerDriver()
//    {
//        WebDriver webDriver = DriverFactory.open("other");
//        Assert.assertEquals(InternetExplorerDriver.class, webDriver.getClass());
//    }
}
