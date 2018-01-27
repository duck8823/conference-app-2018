package io.github.droidkaigi.confsched2018.appium;


import com.microsoft.appcenter.appium.EnhancedAndroidDriver;
import com.microsoft.appcenter.appium.Factory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeMoreThan;

public class DetailTest {

    @Rule
    public TestWatcher watcher = Factory.createWatcher();

    private static WebDriverWait wait;
    private static EnhancedAndroidDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("platformVersion", "7.1");
        capabilities.setCapability("deviceName","Android Emulator");

        String app = new File("../app/build/outputs/apk/debug/app-debug.apk").getAbsolutePath();
        capabilities.setCapability("app", app);

        driver = Factory.createAndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 30);
    }

    @After
    public void tearDown() {
        driver.label("Stopping App");
        driver.quit();
    }

    @Test
    public void showDetail() {
        wait.until(numberOfElementsToBeMoreThan(byId("title"), 1));

        WebElement recyclerView = driver.findElement(byId("sessions_view_pager"))
                                          .findElement(byId("sessions_recycler"));
        assertTrue(recyclerView.isDisplayed());

        recyclerView.findElements(By.xpath("//android.view.ViewGroup")).get(3).click();

        assertEquals("Kotlinアンチパターン", driver.findElement(byId("session_title")).getText());
    }

    private By byId(String resourceId) {
        return By.id("io.github.droidkaigi.confsched2018.debug:id/" + resourceId);
    }

}
