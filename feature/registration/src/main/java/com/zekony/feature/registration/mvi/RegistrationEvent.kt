package com.zekony.feature.registration.mvi

import com.zekony.feature.registration.ui.auth_logic.SignInResult

sealed interface RegistrationEvent {
    data object OnRegistration : RegistrationEvent
    data object CheckRegistration : RegistrationEvent

    data object OnGoogleButtonClick : RegistrationEvent
    class OnGoogleRegistrationIntent(val result: SignInResult) : RegistrationEvent

    class OnNameInput(val name: String) : RegistrationEvent
    class OnEmailInput(val email: String) : RegistrationEvent
    class OnPasswordInput(val password: String) : RegistrationEvent
    data object AgreeWithTerms : RegistrationEvent
    data object ChangePasswordVisibility : RegistrationEvent

    data object ToSigningScreen : RegistrationEvent
    data object ToRegistrationScreen : RegistrationEvent
    data object NavigateHomeScreen : RegistrationEvent
}
