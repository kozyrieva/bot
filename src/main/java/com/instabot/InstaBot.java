package com.instabot;

import com.utilities.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class InstaBot {
    public static final String FIND_INSTAGRAM_ELEMENT_CSS = ".NXVPg.Szr5J";
    public static final String INSTAGRAM_LABEL_CSS = ".cq2ai>[aria-label='Instagram']";
    private static final String COMMENT_ELEMENT_XPATH = "//textarea[contains(@aria-label,'Add a comment')]";
    private static final String COMMENT_ICON_CSS = "[aria-label='Comment']";
    private static final String SUBMIT_BUTTON_XPATH = "//button[@type='submit']";
    private static final String TURN_ON_NOTIFICATIONS_XPATH = "//h2[contains(text(),'Turn on Notifications')]";
    private static final String NOT_NOW_BUTTON_XPATH = "//button[contains(text(),'Not Now')]";
    private static final String DOWNLOAD_APP_NOT_NOW = "//a[contains(text(),'Not Now')]";
    private static final String HASH_TAG_CSS = "input.XTCLo";
    private static final String PICTURE_CSS = ".eLAPa";
    private static final String FOLLOW_BUTTON_CSS = ".oW_lN._0mzm-";
    private static final String LIKE_BUTTON_CSS = ".dCJp8 [aria-label='Like']";
    private static final String PHOTO_COUNT_0N_THE_PAGE = ".-nal3 .g47SY";
    private static final String RIGHT_ARROW_BUTTON = ".HBoOv.coreSpriteRightPaginationArrow";
    private static final String CLOSE_PICTURE_BUTTON_CSS = "button.ckWGn";
    private static final String USERNAME = "username";
    private static final String PASSSWORD = "password";
    private static final String FORGOT_PASSWORD_LINK_TEXT = "Forgot password?";
    private static final String LOGIN_LINK_TEXT = "Log in";
    private static final String FOLLOW_BUTTON_TEXT = "Follow";
    private static final int WAIT_TIME_MILLISECONDS = 2000;

    private WebDriver driver;
    private WebDriverWait wait;

    public InstaBot(String browserType) {
        driver = DriverFactory.open(browserType);
        wait = new WebDriverWait(driver, 20);
    }

    public void openAndNavigateTo(String url) {
        // Open browser, Navigate to our website
        driver.get(url);
    }

    public WebElement findElementByCSS(String element) {
        try {
            WebElement instaElement = driver.findElement(By.cssSelector(element));
            return instaElement;
        } catch (Exception e) {
            System.out.println("Cannot find an Element" + element);
        }
        return null;
    }
    public WebElement findElementByID(String element) {
        try {
            WebElement instaElement = driver.findElement(By.id(element));
            return instaElement;
        } catch (Exception e) {
            System.out.println("Cannot find an Element" + element);
        }
        return null;
    }

    public void login(String name, String password) throws InterruptedException {
        //Enter the login data
        WebElement logInLink = driver.findElement(By.partialLinkText(LOGIN_LINK_TEXT));
        logInLink.click();
        // Enter the Login information
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(FORGOT_PASSWORD_LINK_TEXT)));
        WebElement userNameElement = driver.findElement(By.name(USERNAME));
        userNameElement.sendKeys(name);
        WebElement passwordElement = driver.findElement(By.name(PASSSWORD));
        passwordElement.sendKeys(password);
        // Click submit button
        WebElement submitButton = driver.findElement(By.xpath(SUBMIT_BUTTON_XPATH));
        submitButton.click();
        Thread.sleep(WAIT_TIME_MILLISECONDS);
        try {
            driver.findElement(By.xpath(DOWNLOAD_APP_NOT_NOW)).click();
        } catch (Exception e) {
            System.out.println("Download App hasn't been shown " + e.getMessage());
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TURN_ON_NOTIFICATIONS_XPATH)));
        WebElement turnOffNotification = driver.findElement(By.xpath(NOT_NOW_BUTTON_XPATH));
        turnOffNotification.click();
        Thread.sleep(WAIT_TIME_MILLISECONDS);
    }

    public void enterHashtagAndNavigate(String tag) throws InterruptedException {
        // Select a # and search
        WebElement hashtag = driver.findElement(By.cssSelector(HASH_TAG_CSS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(INSTAGRAM_LABEL_CSS)));
        hashtag.sendKeys(tag);
        Thread.sleep(WAIT_TIME_MILLISECONDS);
        hashtag.sendKeys(Keys.ENTER);
        Thread.sleep(WAIT_TIME_MILLISECONDS);
        hashtag.sendKeys(Keys.ENTER);
        Thread.sleep(WAIT_TIME_MILLISECONDS);
    }

    public void enterUserNameAndNavigate(String url, String username) throws InterruptedException {
        // Select a # and search
        driver.get(url + username + "/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(INSTAGRAM_LABEL_CSS)));
        Thread.sleep(WAIT_TIME_MILLISECONDS);
    }

    public void followCommentLikePictures(List<String> comments) throws InterruptedException {
        Actions action = new Actions(driver);
        int count = Integer.parseInt(driver.findElement(By.cssSelector(PHOTO_COUNT_0N_THE_PAGE)).getText().replaceAll(",", ""));
        List<WebElement> instaPictures = driver.findElements(By.cssSelector(PICTURE_CSS));
        action.moveToElement(instaPictures.get(0)).click().build().perform();
        Thread.sleep(WAIT_TIME_MILLISECONDS);
        System.out.println("Found " + count + " pictures");
        for (int i = 0; i < count; i++) {
            System.out.println("Processing picture: " + i);
            // action.moveToElement(instaPictures.get(i)).click().build().perform();
            Thread.sleep(WAIT_TIME_MILLISECONDS);
            int random = (int) (Math.random() * (comments.size()));
            boolean wasFollowed = followUser();
            if (wasFollowed) {
                commentPicture(comments.get(random));
            }
            likePicture();
            boolean lastPicture = nextPicture();
            if (lastPicture) {
                break;
            }
        }
    }

    public void scrollPage() throws InterruptedException {
        Actions action = new Actions(driver);
        // Shourtcut key for Infinite scroll (Ctrl+End)
        action.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
        Thread.sleep(WAIT_TIME_MILLISECONDS - 1000);
    }

    private void likePicture() {
        try {
            WebElement like = driver.findElement(By.cssSelector(LIKE_BUTTON_CSS));
            like.click();
            Thread.sleep(WAIT_TIME_MILLISECONDS);
        } catch (Exception e) {
            System.out.println("Already Liked");
        }
    }

    private boolean followUser() {
        try {
            WebElement follow = driver.findElement(By.cssSelector(FOLLOW_BUTTON_CSS));
            if (follow.getText().equals(FOLLOW_BUTTON_TEXT)) {
                follow.click();
                Thread.sleep(WAIT_TIME_MILLISECONDS);
                return true;
            } else {
                System.out.println("You are already following this user");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Follow button is absent.");
        }
        return false;
    }

    private void commentPicture(String comment) throws InterruptedException {
        WebElement commentElement;
        try {
            commentElement = driver.findElement(By.xpath(COMMENT_ELEMENT_XPATH));
        } catch (Exception e) {
            System.out.println("Comments are disabled");
            return;
        }

        //  driver.findElement(By.cssSelector(COMMENT_ICON_CSS)).click();

        // TODO - fix comment issue and remove this code
        // BAD CODE
        try {
            commentElement.sendKeys("!");
        } catch (Exception e) {
            commentElement = driver.findElement(By.xpath(COMMENT_ELEMENT_XPATH));
        }

        Thread.sleep(WAIT_TIME_MILLISECONDS);
        commentElement.sendKeys(comment);
        Thread.sleep((int) (7000 + Math.random() * 10000));
        commentElement.submit(); //Post
        Thread.sleep(WAIT_TIME_MILLISECONDS);
    }

    private void closePicture() throws InterruptedException {
        driver.findElement(By.cssSelector(CLOSE_PICTURE_BUTTON_CSS)).click();
        Thread.sleep(WAIT_TIME_MILLISECONDS);
    }

    private boolean nextPicture() throws InterruptedException {
        try {
            driver.findElement(By.cssSelector(RIGHT_ARROW_BUTTON)).click();
            Thread.sleep(WAIT_TIME_MILLISECONDS);
            return false;
        } catch (Exception e) {
            System.out.println("Next picture button is absent. Finishing.");
            return true;
        }
    }

    public void finish() {
        driver.quit();
    }
}
