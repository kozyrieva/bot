package com.instabot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristina Kozyrieva
 */
public class App {
    // mvn clean compile assembly:single
    public static void main(String[] args) throws InterruptedException {
        // put your username
        String name = "";
        // put your password
        String password = "";
        String tag = "#блогtoday ";
        String browserType = "chrome";
        String url = "https://www.instagram.com/";
        String username = "nashizakordonom";
        List<String> comments = new ArrayList<>();
        comments.add("Отличное фото!");
        comments.add("Класс!");
        comments.add("Прекрасный кадр!");
        comments.add("Красивое фото!");
        comments.add("Красиво!");
        comments.add("Хорошая фотография");

        followCommentLike(true, browserType, url, name, password, tag, username, comments);
    }

    private static void followCommentLike(boolean byHashTag, String browserType, String url, String name,
                                         String password, String tag, String username,
                                         List<String> comments) throws InterruptedException {
        InstaBot bot = new InstaBot(browserType);
        bot.openAndNavigateTo(url);
        bot.login(name, password);
        if (byHashTag) {
            bot.enterHashtagAndNavigate(tag);
        } else {
            bot.enterUserNameAndNavigate(url, username);
        }
        bot.followCommentLikePictures(comments);
        bot.finish();
    }
}
