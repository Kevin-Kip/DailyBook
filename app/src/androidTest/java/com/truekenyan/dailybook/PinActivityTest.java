package com.truekenyan.dailybook;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.truekenyan.dailybook.activities.MainActivity;
import com.truekenyan.dailybook.activities.NewPinActivity;
import com.truekenyan.dailybook.activities.PinActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by password
 * on 6/29/18.
 */

@RunWith(AndroidJUnit4.class)
public class PinActivityTest {
    @Rule
    public ActivityTestRule<PinActivity> pinActivityActivityTestRule = new ActivityTestRule<>(PinActivity.class);

    @Test
    public void inputPin(){
        onView(withId(R.id.pin_input)).check(matches(isDisplayed()));

        onView(withId(R.id.confirm_button))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));

        onView(withId(R.id.reset_button))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));
    }

    @Test
    public void inputError(){
        onView(withId(R.id.pin_input))
                .perform(clearText(), typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.confirm_button))
                .perform(click());
        onView(withId(R.id.pin_input))
                .check(matches(hasErrorText("Please Input at least 4 Characters")));
    }

    @Test
    public void inputAndSubmitPin(){
        onView(withId(R.id.pin_input))
                .perform(clearText(), typeText("34567"), closeSoftKeyboard());
        onView(withId(R.id.confirm_button))
                .perform(click());
        Intents.init();
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void onResetButtonCLicked(){
        onView(withId(R.id.reset_button))
                .perform(click());
        Intents.init();
        intended(hasComponent(NewPinActivity.class.getName()));
    }
}
