package ercanduman.csvparsingdemo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import ercanduman.csvparsingdemo.data.model.Issue
import ercanduman.csvparsingdemo.data.source.LocalDataSource
import ercanduman.csvparsingdemo.util.MainDispatcherRule
import ercanduman.csvparsingdemo.util.Resource
import ercanduman.csvparsingdemo.util.getOrAwaitValueTest
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

/**
 * Stores all unit test cases for [MainViewModel] class.
 *
 * @author ercanduman
 * @since  09.07.2021
 */
class MainViewModelTest {
    /*
     * Swaps the background executor used by the architecture components which executes each task synchronously.
     */
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    /*
     * Provides coroutine rule for suspend executions.
     */
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val dataSource: LocalDataSource = mockk()
    private val viewModel = MainViewModel(dataSource)

    @Test
    fun test_getData_function_forEmptyList() = runBlockingTest {
        coEvery { dataSource.readFile() } returns flowOf(Resource.Success(emptyList()))

        viewModel.getData()
        verify { dataSource.readFile() }

        val result: Resource = viewModel.resourceLiveData.getOrAwaitValueTest()
        assertThat(result is Resource.Success)
        assertThat((result as Resource.Success).issues).isEmpty()
    }

    @Test
    fun test_getData_function_forNonEmptyList() = runBlockingTest {
        val providedList = listOf(Issue(), Issue())
        coEvery { dataSource.readFile() } returns flowOf(Resource.Success(providedList))

        viewModel.getData()
        verify { dataSource.readFile() }

        val result: Resource = viewModel.resourceLiveData.getOrAwaitValueTest()
        assertThat(result is Resource.Success)
        assertThat((result as Resource.Success).issues?.size).isEqualTo(providedList.size)
    }

    @Test
    fun test_getData_function_forErrorCase() = runBlockingTest {
        val errorMessage = "Sample error message"
        coEvery { dataSource.readFile() } returns flowOf(Resource.Error(errorMessage))

        viewModel.getData()
        verify { dataSource.readFile() }

        val result: Resource = viewModel.resourceLiveData.getOrAwaitValueTest()
        assertThat(result is Resource.Error)
        assertThat((result as Resource.Error).message).isEqualTo(errorMessage)
    }

    @Test
    fun test_getData_function_forLoadingCase() = runBlockingTest {
        coEvery { dataSource.readFile() } returns flowOf(Resource.Loading)

        viewModel.getData()
        verify { dataSource.readFile() }

        val result: Resource = viewModel.resourceLiveData.getOrAwaitValueTest()
        assertThat(result is Resource.Loading)
    }
}