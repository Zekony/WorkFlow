package com.finto.feature.createproject.mvi

sealed interface ProjectSideEffect {
    data object NavigateBack : ProjectSideEffect
    data class ShowErrorMessage(val message: String) : ProjectSideEffect
}