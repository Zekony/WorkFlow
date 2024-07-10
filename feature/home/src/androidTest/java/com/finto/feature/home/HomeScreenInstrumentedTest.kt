package com.finto.feature.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.finto.domain.home.entities.Project
import com.finto.domain.home.entities.Task
import com.finto.domain.home.entities.User
import com.finto.feature.home.mvi.DownloadState
import com.finto.feature.home.mvi.HomeState
import com.finto.feature.home.ui.HomeScreen
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenInstrumentedTest {

    @get:Rule
    val composeRule = createComposeRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val testUser =
        User(id = "userId", userProjectsIds = listOf("1", "2", "3", "4"), name = "test")

    private val listOfCompletedProjects = listOf(
        Project(
            id = "1",
            title = "TestTitle 1",
            projectTasks = listOf(Task(id = "", completed = true))
        ),
        Project(
            id = "2",
            title = "TestTitle 2",
            projectTasks = listOf(Task(id = "", completed = true))
        ),
        Project(
            id = "3",
            title = "TestTitle 3",
            projectTasks = listOf(Task(id = "", completed = true))
        )
    )
    private val listOfUnCompletedProjects = listOf(
        Project(
            id = "4",
            title = "TestTitle 4",
            projectTasks = listOf(Task(id = ""))
        ),
        Project(
            id = "5",
            title = "TestTitle 5",
            projectTasks = listOf(Task(id = ""))
        ),
        Project(
            id = "6",
            title = "TestTitle 6",
            projectTasks = listOf(Task(id = ""))
        )
    )

    @Before
    fun setUp() {
//        vm = HomeViewModel(
//            projectsRepository = projectsRepository,
//            googleAuthUiClient = googleClient,
//            usersRepository = usersRepository
//        )

    }

    @Test
    fun onEmptyUserShowLoadingBar() {
        composeRule.setContent {
            HomeScreen(HomeState()) {}
        }

        composeRule.onNodeWithTag("loading user").assertIsDisplayed()
    }

    @Test
    fun onEmptyProjectsListShowText() {
        val testState = HomeState(
            user = testUser,
            allProjectsList = emptyList(),
            downloadState = DownloadState.Done
        )
        composeRule.setContent {
            HomeScreen(testState) {}
        }

        composeRule.onNodeWithTag("no projects").assertIsDisplayed()
    }

    @Test
    fun onNotEmptyProjectsListShowText() {
        val testState = HomeState(
            user = testUser,
            allProjectsList = listOfCompletedProjects,
            uncompletedProjectsList = listOfUnCompletedProjects,
            downloadState = DownloadState.Done
        )
        composeRule.setContent {
            HomeScreen(testState) {}
        }
        composeRule.onNodeWithText(listOfCompletedProjects.first().title).assertIsDisplayed()
    }
}