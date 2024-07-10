package com.finto.feature.home.mvi

import androidx.lifecycle.viewModelScope
import com.finto.domain.home.entities.Project
import com.finto.domain.home.entities.User
import com.finto.domain.home.repositories.ProjectsRepository
import com.finto.domain.home.repositories.UsersRepository
import com.finto.domain.registration.GoogleAuthUiClient
import com.finto.utility.MviViewModel
import com.finto.utility.functions.addToList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    private val usersRepository: UsersRepository,
    private val googleAuthUiClient: GoogleAuthUiClient
) : MviViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {

    init {
        dispatch(HomeEvent.Initialize)
    }

    override fun dispatch(event: HomeEvent) {
        when (event) {
            HomeEvent.Initialize -> initialize()

            is HomeEvent.OnProjectClick -> onProjectClick(event.projectId)
            is HomeEvent.OnSearchInput -> onSearchInput(event.text)
            is HomeEvent.ChangeSearchFilter -> changeSearchFilter(event.filter)

            HomeEvent.NavigateToSettings -> navigateToSettings()
        }
    }

    private fun changeSearchFilter(filter: SearchFilter) = intent {
        reduce { state.copy(searchFilter = filter) }
    }

    private fun initialize() = intent {
        getCurrentUser()
    }

    private fun getCurrentUser() = intent {
        val firebaseUser = googleAuthUiClient.getCurrentUser().firstOrNull()
        firebaseUser?.let { user ->
            reduce {
                state.copy(
                    user = user
                )
            }
            val ids = getUsersProjectIds(user).await()
            if (ids.isNotEmpty()) {
                getAllProjects(ids)
            } else {
                reduce { state.copy(downloadState = DownloadState.Done) }
            }
        }
    }

    private suspend fun getUsersProjectIds(user: User): Deferred<List<String>> =
        viewModelScope.async {
            usersRepository.getUsersProjectIds(user).first().getOrNull() ?: emptyList()
        }

    private suspend fun SimpleSyntax<HomeState, HomeSideEffect>.getAllProjects(userIds: List<String>) {
        projectsRepository.getAllProjectsByUser(userIds).collect { result ->
            if (result.isSuccess) {
                result.getOrNull()?.let { notNullResult ->
                    val completedProject = mutableMapOf<Boolean, Project>().apply {
                        put(
                            notNullResult.projectTasks.isNotEmpty() && notNullResult.projectTasks.all { task -> task.completed },
                            notNullResult
                        )
                    }
                    reduce {
                        state.copy(
                            user = state.user.copy(userProjectsIds = userIds),
                            downloadState = DownloadState.Done,
                            allProjectsList = state.allProjectsList.addToList(notNullResult),
                            completedProjectsList = state.completedProjectsList.addToList(
                                completedProject[true]
                            ),
                            uncompletedProjectsList = state.uncompletedProjectsList.addToList(
                                completedProject[false]
                            )
                        )
                    }
                }
            } else {
                reduce { state.copy(downloadState = DownloadState.Done) }
            }
        }
    }

    @OptIn(OrbitExperimental::class)
    private fun onSearchInput(text: String) = blockingIntent {
        val listToSearch = when (state.searchFilter) {
            SearchFilter.ByCompleted -> state.completedProjectsList
            SearchFilter.ByUncompleted -> state.uncompletedProjectsList
            SearchFilter.All -> state.allProjectsList
        }
        reduce {
            state.copy(
                searchInput = if (text.length < 20) text.trimStart() else state.searchInput,
                searchedProjectsList = if (text.isEmpty()) emptyList() else
                    listToSearch.filter { it.title.contains(text, ignoreCase = true) }
            )
        }

    }

    private fun onProjectClick(projId: String) = intent {
        resetState()
        postSideEffect(HomeSideEffect.NavigateToProject(projId))
    }

    private fun navigateToSettings() = intent {
        postSideEffect(HomeSideEffect.NavigateToSettings)
    }

    private fun resetState() = intent {
        reduce {
            state.copy(
                allProjectsList = emptyList(),
                uncompletedProjectsList = emptyList(),
                completedProjectsList = emptyList()
            )
        }
    }
}

