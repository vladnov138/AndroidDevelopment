package com.example.lab312

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun `check_1_plus_1_eq_2`() {
        val firstNumberEdit = onView(withId(R.id.firstNumber))
        firstNumberEdit.check(matches(isDisplayed()))

        val secondNumberEdit = onView(withId(R.id.secondNumber))
        secondNumberEdit.check(matches(isDisplayed()))

        val button = onView(withId(R.id.calculateButton))
        button.check(matches(isDisplayed()))

        val result = onView(withId(R.id.resultNumber))
        result.check(matches(isDisplayed()))

        firstNumberEdit.perform(typeText("1"))
        secondNumberEdit.perform(typeText("1"))
        button.perform(click())
        result.check(matches(withText("2")))
    }
}