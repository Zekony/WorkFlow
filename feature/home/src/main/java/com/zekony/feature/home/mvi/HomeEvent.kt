package com.zekony.feature.home.mvi

sealed interface HomeEvent {

    data object Initialize : HomeEvent
    data class OnProjectClick(val projectId: Int): HomeEvent

    data class OnSearchInput(val text: String): HomeEvent

    data object NavigateToSettings : HomeEvent
}