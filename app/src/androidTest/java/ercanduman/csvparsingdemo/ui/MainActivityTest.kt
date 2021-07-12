package ercanduman.csvparsingdemo.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import ercanduman.csvparsingdemo.R
import org.junit.Rule
import org.junit.Test

/**
 * Contains [MainActivity] related UI test cases.
 *
 * @author ercanduman
 * @since  12.07.2021
 */
class MainActivityTest {

    /*
     Creating activity rule will launch the activity before each test cases.
     So no need to write below code snippet for each test case
        ActivityScenario.launch(MainActivity::class.java)
     */
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_launch_MainActivity() {
        // ActivityScenario provides functionality to start an Activity for testing.
        // [activityRule] will launch the MainActivity, so no need to run below code
        // ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun test_launchMainActivity_and_CheckIfTitleDisplayedOnToolbar() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()))
    }

    @Test
    fun test_launchMainActivity_and_checkIfChildViewsAreAvailableAndDisplayedProperly() {
        onView(withId(R.id.main_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.main_progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.main_error_message)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
}