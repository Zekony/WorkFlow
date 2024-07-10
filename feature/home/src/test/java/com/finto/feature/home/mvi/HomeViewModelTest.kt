package com.finto.feature.home.mvi

import com.finto.domain.home.entities.Project
import com.finto.domain.home.entities.Task
import com.finto.domain.home.entities.User
import com.finto.domain.home.repositories.ProjectsRepository
import com.finto.domain.home.repositories.UsersRepository
import com.finto.domain.registration.GoogleAuthUiClient
import com.finto.feature.home.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.orbitmvi.orbit.test.test

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private lateinit var vm: HomeViewModel
    private val googleClient = mockk<GoogleAuthUiClient>(relaxed = true)
    private val projectsRepository = mockk<ProjectsRepository>(relaxed = true)
    private val usersRepository = mockk<UsersRepository>(relaxed = true)

    private val testUser = User(id = "userId", userProjectsIds = listOf("1", "2", "3", "4"), name = "test")

    private val testProject = Project(
        id = "1",
        title = "TestTitle",
        projectTasks = listOf(Task(id = "", completed = true))
    )

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

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        vm = HomeViewModel(
            projectsRepository = projectsRepository,
            googleAuthUiClient = googleClient,
            usersRepository = usersRepository
        )
    }

    @After
    fun tearDown() {

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get user, get user's project ids, get projects with user`() = runTest {
        vm.test(this) {
            expectInitialState()

            coEvery { googleClient.getCurrentUser() } returns flow { emit(testUser) }
            every { usersRepository.getUsersProjectIds(testUser) } returns flow {
                emit(
                    Result.success(
                        testUser.userProjectsIds
                    )
                )
            }
            every { projectsRepository.getAllProjectsByUser(testUser.userProjectsIds) } returns flow {
                emit(
                    Result.success(
                        testProject
                    )
                )
            }
            val job = launch {
                vm.dispatch(HomeEvent.Initialize)
                runCurrent()
            }
            expectState {
                copy(
                    user = testUser
                )
            }
            expectState {
                copy(
                    downloadState = DownloadState.Done,
                    allProjectsList = listOf(testProject),
                    completedProjectsList = listOf(testProject)
                )
            }
            job.cancel()
        }
    }

    @Test
    fun `change search filter`() = runTest {
        vm.test(this) {
            expectInitialState()

            val job = launch {
                vm.dispatch(HomeEvent.ChangeSearchFilter(SearchFilter.ByCompleted))
            }
            expectState {
                copy(
                    searchFilter = SearchFilter.ByCompleted
                )
            }
            job.cancel()
        }
    }

    @Test
    fun `change filter, search by text, find filtered projects list`() = runTest {
        vm.test(
            this,
            initialState = HomeState(
                completedProjectsList = listOfCompletedProjects,
                uncompletedProjectsList = listOfUnCompletedProjects,
                allProjectsList = listOfCompletedProjects.toMutableList()
                    .apply { addAll(listOfUnCompletedProjects) }
            )
        ) {
            expectInitialState()

            launch {
                vm.dispatch(HomeEvent.ChangeSearchFilter(SearchFilter.ByCompleted))
            }.join()
            launch {
                vm.dispatch(HomeEvent.OnSearchInput("TestTitle"))
            }

            expectState {
                copy(
                    searchFilter = SearchFilter.ByCompleted,
                )
            }
            expectState {
                copy(
                    searchInput = "TestTitle",
                    searchedProjectsList = listOfCompletedProjects
                )
            }
        }
    }
}