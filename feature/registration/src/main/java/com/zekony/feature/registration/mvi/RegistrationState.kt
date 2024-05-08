package com.zekony.feature.registration.mvi

data class RegistrationState(
    val isRegistered: UserRegistrationState = UserRegistrationState.MakingRequest,
    val nameInput: String = "",
    val emailInput: String = "",
    val passwordInput: String = "",
    val termsAgreed: Boolean = false,
    val passwordVisible: Boolean = false,

)

enum class UserRegistrationState {
    IsRegistered, NotRegistered, MakingRequest, Error
}