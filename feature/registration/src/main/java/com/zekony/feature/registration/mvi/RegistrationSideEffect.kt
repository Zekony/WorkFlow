package com.zekony.feature.registration.mvi

sealed interface RegistrationSideEffect {
    data object NavigateHomeScreen: RegistrationSideEffect
    data object GoogleAuth: RegistrationSideEffect

    data object PostInputErrorMessage: RegistrationSideEffect
}
