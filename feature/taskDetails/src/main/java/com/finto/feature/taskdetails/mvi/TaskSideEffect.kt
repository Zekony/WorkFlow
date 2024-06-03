package com.finto.feature.taskdetails.mvi

sealed interface TaskSideEffect {
    data object OnNavigateBack : TaskSideEffect
    data class OnNavigateToChangeProject(val id: String) : TaskSideEffect
}