package com.zekony.feature.registration.mvi

sealed interface RegistrationSideEffect {
    object OnNavigateBack : RegistrationSideEffect
}
