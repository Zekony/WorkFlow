package com.zekony.feature.home.mvi

sealed interface HomeSideEffect {
    data class NavigateToProject(val id: Int): HomeSideEffect
    data object NavigateToSettings : HomeSideEffect
}
