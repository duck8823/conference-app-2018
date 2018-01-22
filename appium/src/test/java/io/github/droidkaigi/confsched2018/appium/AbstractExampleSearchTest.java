package io.github.droidkaigi.confsched2018.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeMoreThan;

/**
 * Created by duck8823 on 2018/01/22.
 */

public abstract class AbstractExampleSearchTest {

    abstract AndroidDriver driver();
    abstract WebDriverWait aWait();

    @Test
    public void search() {
        aWait().until(numberOfElementsToBeMoreThan(byId("title"), 1));

        By navSearch = byId("navigation_search");
        aWait().until(elementToBeClickable(navSearch));

        driver().findElement(byId("navigation_search")).click();

        driver().findElement(byId("search_button")).click();

        driver().findElement(byId("search_src_text")).sendKeys("duck");

        aWait().until(elementToBeClickable(byId("search_content")));

        driver().findElement(byId("search_content")).click();

        String text = driver().findElement(byId("title")).getText();
        assertEquals("Androidで利用できるデバイスファームのメリット・デメリットの紹介",text);
    }


    private By byId(String resourceId) {
        return By.id("io.github.droidkaigi.confsched2018.debug:id/" + resourceId);
    }
}
