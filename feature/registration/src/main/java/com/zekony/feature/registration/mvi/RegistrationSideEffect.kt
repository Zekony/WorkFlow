package com.zekony.feature.registration.mvi

sealed interface RegistrationSideEffect {
    object NavigateBack : RegistrationSideEffect

    object NavigateHomeScreen: RegistrationSideEffect
    object GoogleAuth: RegistrationSideEffect
}
