package io.github.droidkaigi.confsched2018.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeMoreThan;

/**
 * Created by duck8823 on 2018/01/22.
 */

public abstract class AbstractShowDetailTest {

    abstract AndroidDriver driver();
    abstract WebDriverWait aWait();

    @Test
    public void showDetail() {
        aWait().until(numberOfElementsToBeMoreThan(byId("title"), 1));

        WebElement recyclerView = driver().findElement(byId("sessions_view_pager"))
                                          .findElement(byId("sessions_recycler"));
        assertTrue(recyclerView.isDisplayed());

        recyclerView.findElements(By.xpath("//android.view.ViewGroup")).get(3).click();

        assertEquals("Kotlinアンチパターン", driver().findElement(byId("title")).getText());
    }


    private By byId(String resourceId) {
        return By.id("io.github.droidkaigi.confsched2018.debug:id/" + resourceId);
    }
}
