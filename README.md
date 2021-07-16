# CSVParsingDemo
Sample MVVM architecture applied android application to display Issue item list in recyclerView. Content that will be parsed and read is provided from issues.csv file in **/assets** folder.

## Requirement
The goal of this task is to implement a sample project that can read & parse the CSV file and generate Kotlin class based on provided data.

In the attachments you can find a CSV file (issues.csv), this file contains all the necessary data to complete this assignment. Parse the CSV file and use it for the visualization of the list.

## Solution
A native android application will be created from scratch. **Kotlin** will be used as the main development language. The application will contain android architecture components such as ViewModel, LiveData, Flow, etc.

The application will read CSV content from issues.csv file in assets folder and content will be converted into Kotlin data class. (Issue.kt)

LocalDataSource.kt class is used which has responsibility to retrieve data from file and provide this data to ViewModel. Coroutines and suspend functions will be used for running background operations (Dispatcher.IO) in order to not block the UI thread during parsing.

### Issue List
When application started for first time Loading screen will be displayed if there huge data to load.

<img src="https://github.com/ercanduman/CSVParsingDemo/blob/master/attachments/ercanduman_csv_parsing_demo_loading_screen.png" width="25%" title="Issue List">

MainActivity.kt is the main UI activity where all user interactions will be handled and also the issue list will be displayed in RecyclerView.

<img src="https://github.com/ercanduman/CSVParsingDemo/blob/master/attachments/ercanduman_csv_parsing_demo_loaded_data_screen.png" width="25%" title="Issue List">

#### Build
This application uses the Gradle build system. To build this project use "Import Project" in Android Studio and use the following command. > gradlew build

#### Test
Test suite class (UnitTestCasesSuite.class) created to run all unit test cases with single click.

MainActivityTest.kt class created to store all UI related (Instrumented) test cases.

Happy coding! 👍 🥇

## License

[![License](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://github.com/ercanduman/CSVParsingDemo/blob/master/LICENSE)

ENB Creative, Copyright (C) 2021
