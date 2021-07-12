package ercanduman.csvparsingdemo

import ercanduman.csvparsingdemo.data.source.LocalDataSourceTest
import ercanduman.csvparsingdemo.ui.MainViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Test suite to run all local unit test classes with a single click.
 *
 * Unit tests which don't need any Android device to execute on.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 *
 * @author ercanduman
 * @since  12.07.2021
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(LocalDataSourceTest::class, MainViewModelTest::class)
class UnitTestCasesSuite