package com.shuangwei.application;


import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shuangwei.application.ui.activity.WelcomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.goldze.mvvmhabit", appContext.getPackageName());
    }

    @Rule
    public final ActivityTestRule activityTestRule =
            new ActivityTestRule<>(WelcomeActivity.class, false, false);

    @Test
    public void blockingTest() throws Exception {
        Intent intent = new Intent();
        // Add your own intent extras here if applicable.
        activityTestRule.launchActivity(intent);
        CountDownLatch countdown = new CountDownLatch(1);
        countdown.await();
    }
}
