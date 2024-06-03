package com.finto.feature.registration.mvi

sealed interface RegistrationSideEffect {
    data object NavigateHomeScreen: RegistrationSideEffect
    data object GoogleAuth: RegistrationSideEffect

    data object PostInputErrorMessage: RegistrationSideEffect
    data class PostSigningErrorMessage(val message: String?): RegistrationSideEffect
}
