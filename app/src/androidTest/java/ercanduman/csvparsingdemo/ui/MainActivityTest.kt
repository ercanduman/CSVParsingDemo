package ercanduman.csvparsingdemo.ui

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.common.truth.Truth.assertThat
import ercanduman.csvparsingdemo.R
import ercanduman.csvparsingdemo.data.source.LocalDataSource
import ercanduman.csvparsingdemo.util.Resource
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.not
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
    fun test_launchMainActivity_and_checkIfChildViewsAreAvailableAndDisplayedProperly(): Unit =
        runBlocking {
            val localDataSource = LocalDataSource(ApplicationProvider.getApplicationContext())
            val resourceList = localDataSource.readFile().toList()

            // The first emitted value is always Resource.Loading.
            assertThat(resourceList[0] is Resource.Loading)

            // Second emitted value can be Resource.Success or Resource.Error
            // To collect second emitted flow value, toList()[1] should be used.
            when (val resource = resourceList[1]) {
                is Resource.Error -> checkErrorViews()
                is Resource.Loading -> checkLoadingViews()
                is Resource.Success -> {
                    if (resource.issues.isNotEmpty()) {
                        checkSuccessViews()
                    } else {
                        checkErrorViews()
                    }
                }
            }
        }

    @Test
    fun test_launchMainActivity_and_checkIfNoDataFoundMessageDisplayedForEmptyFile(): Unit =
        runBlocking {
            val localDataSource = LocalDataSource(ApplicationProvider.getApplicationContext())
            val resourceList = localDataSource.readFile().toList()

            // The first emitted value is Resource.Loading
            assertThat(resourceList[0] is Resource.Loading)

            // Second emitted value can be Resource.Error or Resource.Success based on reading file execution
            when (val resource: Resource = resourceList[1]) {
                is Resource.Error -> checkErrorViews()
                is Resource.Loading -> checkLoadingViews()
                is Resource.Success -> {
                    if (resource.issues.isEmpty()) {
                        checkErrorViews()

                        // Check correct error message
                        onView(withText(R.string.no_data_found)).check(matches(isDisplayed()))
                    } else {
                        checkSuccessViews()
                    }
                }
            }
        }

    private fun checkSuccessViews() {
        onView(withId(R.id.main_progress_bar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.main_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.main_error_message)).check(matches(not(isDisplayed())))
    }

    private fun checkLoadingViews() {
        onView(withId(R.id.main_progress_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.main_recycler_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.main_error_message)).check(matches(not(isDisplayed())))
    }

    private fun checkErrorViews() {
        onView(withId(R.id.main_progress_bar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.main_recycler_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.main_error_message)).check(matches(isDisplayed()))
    }
}