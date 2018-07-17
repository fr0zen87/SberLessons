package com.example.lesson17;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onEditTextChanged() {
        Espresso.onView(ViewMatchers.withId(R.id.editText))
                .check(ViewAssertions.matches(ViewMatchers.withText("")));
        Espresso.onView(ViewMatchers.withId(R.id.editText))
                .perform(ViewActions.typeText("qwert"));
        Espresso.onView(ViewMatchers.withId(R.id.editText))
                .check(ViewAssertions.matches(ViewMatchers.withText("qwert")));
    }

    @Test
    public void onClick() {
        Espresso.onView(ViewMatchers.withId(R.id.editText))
                .perform(ViewActions.typeText("qwert"));
        Espresso.onView(ViewMatchers.withId(R.id.button))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.editText))
                .check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
                .perform(RecyclerViewActions.<ListAdapter.ViewHolder>actionOnItemAtPosition(0, ViewActions.click()));
        Espresso.onView(ViewMatchers.withId(R.id.details_text_view))
                .check(ViewAssertions.matches(ViewMatchers.withText("qwert")));
    }
}