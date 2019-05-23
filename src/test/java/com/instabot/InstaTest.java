package com.instabot;

import junit.framework.AssertionFailedError;
import org.junit.*;
import org.openqa.selenium.WebElement;

import static com.instabot.InstaBot.FIND_INSTAGRAM_ELEMENT_CSS;
import static com.instabot.InstaBot.INSTAGRAM_LABEL_CSS;

public class InstaTest {
    private static String BROWSER_TYPE = "chrome";
    private static String NAME = "";
    private static String PASSWORD = "";
    private static String URL = "https://www.instagram.com/";
    private static InstaBot bot;


    @BeforeClass
    public static void initBot(){
        bot = new InstaBot(BROWSER_TYPE);
    }

   // @Ignore
    @Test
    public void openURLTest() {
        bot.openAndNavigateTo(URL);
        WebElement actual = bot.findElementByCSS(FIND_INSTAGRAM_ELEMENT_CSS);
        Assert.assertNotNull(actual);
        Assert.assertEquals("Instagram", actual.getText());
    }
   // @Ignore
    @Test
    public void loginPageTest() {
        bot.openAndNavigateTo(URL);
        try {
        bot.login(NAME, PASSWORD);}
        catch (Exception e){
            System.out.println("Cannot Log In " + e.getMessage());
            throw new AssertionFailedError("Cannot Log In " + e.getMessage());
        }
        WebElement actual = bot.findElementByCSS(INSTAGRAM_LABEL_CSS);
        Assert.assertNotNull(actual);
        Assert.assertEquals("Instagram", actual.getAttribute("aria-label"));

    }

    @Ignore
    @Test
    public void loginPageNegativeTest() {
        bot.openAndNavigateTo(URL);
        try {
            bot.login(NAME, "9sdfsdf");}
        catch (Exception e){
            System.out.println("Cannot Log In " + e.getMessage());
            throw new AssertionFailedError("Cannot Log In " + e.getMessage());
        }
        WebElement actual = bot.findElementByID("slfErrorAlert");
        Assert.assertNotNull(actual);
        Assert.assertEquals("Sorry, your password was incorrect. Please double-check your password.", actual.getText());

    }

    @AfterClass
    public static void closeWindow (){
        bot.finish();
    }

    @Ignore
    @Test
    public void loginPositiveTest(){
        InstaBot bot = new InstaBot(BROWSER_TYPE);
        try {
            bot.login(NAME, PASSWORD);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
