package com.finto.feature.registration.mvi

import android.content.Context

sealed interface RegistrationEvent {
    data object OnRegistration : RegistrationEvent
    data object OnSigning : RegistrationEvent
    data object Initialize : RegistrationEvent
    class OnGoogleSignIn(val context: Context) : RegistrationEvent
    class OnNameInput(val name: String) : RegistrationEvent
    class OnEmailInput(val email: String) : RegistrationEvent
    class OnPasswordInput(val password: String) : RegistrationEvent
    data object AgreeWithTerms : RegistrationEvent
    data object ChangePasswordVisibility : RegistrationEvent
    data object ToSigningScreen : RegistrationEvent
    data object ToRegistrationScreen : RegistrationEvent
    data object NavigateHomeScreen : RegistrationEvent
}