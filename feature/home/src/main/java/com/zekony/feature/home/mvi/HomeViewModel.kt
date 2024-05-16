package com.zekony.feature.home.mvi

import com.zekony.utility.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

): MviViewModel<HomeState, HomeSideEffect, HomeEvent>(initialState = HomeState()){


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
        reduce {
            state.copy(
                completedProjectsList = projectsSampleList.filterNot { project -> project.projectTasks.any { !it.isCompleted } },
                uncompletedProjectsList = projectsSampleList.filter { project -> project.projectTasks.any { !it.isCompleted } },
            )
        }
    }

    private fun onSearchInput(text: String) = intent {
        reduce {
            state.copy(
                searchInput = text
            )
        }
    }

    private fun onProjectClick(projId: Int) {

    }

    private fun navigateToSettings() = intent {
        postSideEffect(HomeSideEffect.NavigateToSettings)
    }
}