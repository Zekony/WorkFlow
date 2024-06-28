package com.zekony.feature.settings.mvi

sealed interface SettingsSideEffect {
    data object NavigateHomeScreen: SettingsSideEffect
    data object OnSignOut: SettingsSideEffect

}
