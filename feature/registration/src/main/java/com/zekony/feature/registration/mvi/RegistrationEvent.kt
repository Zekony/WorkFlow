package com.zekony.feature.registration.mvi

sealed interface RegistrationEvent {
    object OnRegistration : RegistrationEvent
    object CheckRegistration : RegistrationEvent

    class OnNameInput(val name: String) : RegistrationEvent
    class OnEmailInput(val email: String) : RegistrationEvent
    class OnPasswordInput(val password: String) : RegistrationEvent
    object AgreeWithTerms : RegistrationEvent
    object ChangePasswordVisibility : RegistrationEvent
}
