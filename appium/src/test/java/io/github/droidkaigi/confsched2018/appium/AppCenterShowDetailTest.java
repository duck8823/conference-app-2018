package io.github.droidkaigi.confsched2018.appium;

import com.microsoft.appcenter.appium.EnhancedAndroidDriver;
import com.microsoft.appcenter.appium.Factory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;

public class AppCenterShowDetailTest extends AbstractShowDetailTest {

    private static EnhancedAndroidDriver driver;
    private static WebDriverWait wait;

    @Rule
    public TestWatcher watcher = Factory.createWatcher();

    @Override
    AndroidDriver driver() {
        return driver;
    }
    @Override
    WebDriverWait aWait() {
        return wait;
    }

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
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
}
