package com.finto.feature.home.mvi

sealed interface HomeEvent {

    data object Initialize : HomeEvent
    data class OnProjectClick(val projectId: String): HomeEvent

    data class OnSearchInput(val text: String): HomeEvent

    data object NavigateToSettings : HomeEvent
}