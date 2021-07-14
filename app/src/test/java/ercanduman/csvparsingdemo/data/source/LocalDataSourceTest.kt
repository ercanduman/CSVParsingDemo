package ercanduman.csvparsingdemo.data.source

import com.google.common.truth.Truth.assertThat
import ercanduman.csvparsingdemo.data.model.Issue
import ercanduman.csvparsingdemo.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

/**
 * Contains [LocalDataSource] class relates unit test cases.
 *
 * @author ercanduman
 * @since  09.07.2021
 */
class LocalDataSourceTest {

    private val localDataSource: LocalDataSource = mockk()

    @Test
    fun test_readFile_function_success_for_emptyList() = runBlockingTest {
        coEvery { localDataSource.readFile() } returns flowOf(Resource.Success(emptyList()))

        val result: Resource = localDataSource.readFile().toList()[0]
        assertThat(result is Resource.Success)
    }

    @Test
    fun test_readFile_function_success_for_nonEmptyList() = runBlockingTest {
        coEvery { localDataSource.readFile() } returns flowOf(Resource.Success(listOf(Issue())))

        localDataSource.readFile().collect { result ->
            assertThat(result is Resource.Success)
            assertThat((result as Resource.Success).issues).isNotEmpty()
        }
    }

    @Test
    fun test_readFile_function_success_for_listSize2() = runBlockingTest {
        coEvery { localDataSource.readFile() } returns flowOf(Resource.Success(listOf(Issue(), Issue())))

        localDataSource.readFile().collect { result ->
            assertThat(result is Resource.Success)
            assertThat((result as Resource.Success).issues?.size).isEqualTo(2)
        }
    }

    @Test
    fun test_readFile_function_loading() = runBlockingTest {
        coEvery { localDataSource.readFile() } returns flowOf(Resource.Loading)

        localDataSource.readFile().collect {
            assertThat(it).isEqualTo(Resource.Loading)
            verify { localDataSource.readFile() }
        }
    }

    @Test
    fun test_readFile_function_error() = runBlockingTest {
        val message = "Unknown Error"
        coEvery { localDataSource.readFile() } returns flowOf(Resource.Error(message))

        localDataSource.readFile().collect { result ->
            assertThat(result is Resource.Error)
            assertThat((result as Resource.Error).message).isEqualTo(message)
        }
    }
}