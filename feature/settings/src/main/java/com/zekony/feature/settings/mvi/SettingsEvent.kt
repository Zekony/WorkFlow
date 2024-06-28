package com.zekony.feature.settings.mvi

sealed interface SettingsEvent {
    data object NavigateHomeScreen : SettingsEvent

    data object Initialize : SettingsEvent

    data object OnSignOut : SettingsEvent

    data object ChangeName : SettingsEvent
}