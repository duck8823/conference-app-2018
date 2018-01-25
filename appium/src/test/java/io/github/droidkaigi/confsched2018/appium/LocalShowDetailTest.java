package io.github.droidkaigi.confsched2018.appium;


import org.junit.After;
import org.junit.Before;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.github.droidkaigi.confsched2018.categories.Local;

@Category(Local.class)
public class LocalShowDetailTest extends AbstractShowDetailTest {

    private static WebDriverWait wait;
    private static AndroidDriver driver;

    @Override
    WebDriverWait aWait() {
        return wait;
    }
    @Override
    AndroidDriver driver() {
        return driver;
    }

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("platformVersion", "7.1");
        capabilities.setCapability("deviceName","Android Emulator");

        String app = new File("../app/build/outputs/apk/debug/app-debug.apk").getAbsolutePath();
        capabilities.setCapability("app", app);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 30);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
