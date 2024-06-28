package com.finto.feature.home.mvi

sealed interface HomeSideEffect {
    data class NavigateToProject(val id: String): HomeSideEffect
    data object NavigateToSettings : HomeSideEffect

    data class PostErrorMessage(val message: String?) : HomeSideEffect
}
