package io.github.droidkaigi.confsched2018.appium;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeMoreThan;

public class ExampleSearchTest {

    private static AndroidDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    public static void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("platformVersion", "7.1");
        capabilities.setCapability("deviceName","Android Emulator");

        String app = new File("../app/build/outputs/apk/debug/app-debug.apk").getAbsolutePath();
        capabilities.setCapability("app", app);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 30);
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void search() {
        wait.until(numberOfElementsToBeMoreThan(byId("title"), 1));

        By navSearch = byId("navigation_search");
        wait.until(elementToBeClickable(navSearch));

        driver.findElement(byId("navigation_search")).click();

        driver.findElement(byId("search_button")).click();

        driver.findElement(byId("search_src_text")).sendKeys("duck");

        wait.until(elementToBeClickable(byId("search_content")));

        driver.findElement(byId("search_content")).click();

        String text = driver.findElement(byId("title")).getText();
        assertEquals("Androidで利用できるデバイスファームのメリット・デメリットの紹介",text);
    }


    private By byId(String resourceId) {
        return By.id("io.github.droidkaigi.confsched2018.debug:id/" + resourceId);
    }
}
