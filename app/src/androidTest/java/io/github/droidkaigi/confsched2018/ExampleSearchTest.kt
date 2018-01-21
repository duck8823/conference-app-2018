package io.github.droidkaigi.confsched2018

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.Until
import io.github.droidkaigi.confsched2018.presentation.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UIテストのサンプル
 * Created by duck8823 on 2018/01/14.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleSearchTest {

    @JvmField
    @Rule
    val mActivityRule = ActivityTestRule(MainActivity::class.java)

    private val appContext = InstrumentationRegistry.getTargetContext()!!

    private var uiDevice: UiDevice? = null

    @Before
    fun setUp() {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    }

    @Test
    fun search() {
        uiDevice!!.wait(Until.hasObject(By.res(appContext.packageName, "text")), 2000)

        onView(withId(R.id.navigation_search)).perform(click())

        onView(withId(R.id.search_button)).perform(click())

        onView(withId(R.id.search_src_text)).perform(typeText("duck"))

        uiDevice!!.wait(Until.hasObject(By.res(appContext.packageName, "search_content")), 2000)

        onView(withId(R.id.search_content)).perform(click())

        onView(withId(R.id.title)).check(matches(withText("Androidで利用できるデバイスファームのメリット・デメリットの紹介")))
    }
}
