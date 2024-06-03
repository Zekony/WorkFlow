package com.finto.feature.home.mvi

import android.util.Log
import com.finto.domain.home.repositories.ProjectsRepository
import com.finto.utility.MviViewModel
import com.finto.utility.classes.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository
) : MviViewModel<HomeState, HomeSideEffect, HomeEvent>(initialState = HomeState()) {

    init {
        dispatch(HomeEvent.Initialize)
    }

    override fun dispatch(event: HomeEvent) {
        when (event) {
            HomeEvent.Initialize -> initialize()

            is HomeEvent.OnProjectClick -> onProjectClick(event.projectId)
            is HomeEvent.OnSearchInput -> onSearchInput(event.text)
            HomeEvent.NavigateToSettings -> navigateToSettings()
        }
    }

    private fun initialize() = intent {
        projectsRepository.getAllProjectsByUser(0).collect { result ->
            when (result) {
                is ResultState.Error -> {
                    Log.d(
                        "Zenais",
                        "HomeViewModel: getAllProjectsByUser error message ${result.message}"
                    )
                    //TODO() add smth
                }

                is ResultState.Loading -> {
                    //TODO() add smth
                }

                is ResultState.Success -> {
                    result.data?.let { notNullResult ->
                        Log.d("Zenais", "notNullResult: $notNullResult")
                        val noCopiesList = state.allProjectsList.toMutableList().apply {
                            removeIf {
                                Log.d("Zenais", "noCopiesList removeIf: project id ${it.id} result id ${notNullResult.id}")
                                it.id == notNullResult.id
                            }
                            add(notNullResult)
                        }
                        Log.d("Zenais", "noCopiesList: $noCopiesList")
                        val filteredList =
                            noCopiesList.groupBy { it.projectTasks.any { task -> !task.completed } }
                        Log.d("Zenais", "filteredList: $filteredList")

                        reduce {
                            state.copy(
                                allProjectsList = noCopiesList,
                                completedProjectsList = filteredList[false] ?: emptyList(),
                                uncompletedProjectsList = filteredList[true] ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }


    @OptIn(OrbitExperimental::class)
    private fun onSearchInput(text: String) = blockingIntent {
        reduce {
            state.copy(
                searchInput = if (text.length < 20) text.trimStart() else state.searchInput,
                searchedProjectsList = if (text.isEmpty()) emptyList() else
                    state.allProjectsList.filter { it.title.contains(text, ignoreCase = true) }
            )
        }

    }

    private fun onProjectClick(projId: String) = intent {
        postSideEffect(HomeSideEffect.NavigateToProject(projId))
    }

    private fun navigateToSettings() = intent {
        postSideEffect(HomeSideEffect.NavigateToSettings)
    }
}