package ercanduman.csvparsingdemo.ui

import androidx.test.core.app.ActivityScenario
import ercanduman.csvparsingdemo.data.source.LocalDataSource
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

/**
 * Contains [MainActivity] related UI test cases.
 *
 * @author ercanduman
 * @since  12.07.2021
 */
class MainActivityTest {

    private val dataSource: LocalDataSource = mockk()
    private val viewModel: MainViewModel = MainViewModel(dataSource)

    @Before
    fun setUp() {
        val mainActivity = ActivityScenario.launch(MainActivity::class.java)
        mainActivity.onActivity { it.setViewModel(viewModel) }
    }

    @Test
    fun test_launch_MainActivity() {
        // ActivityScenario provides functionality to start an Activity for testing.
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun test_launchMainActivity_and_CheckTheViewsForNoData() {


    }
}