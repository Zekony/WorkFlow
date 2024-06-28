package com.finto.feature.registration.mvi

data class RegistrationState(
    val isRegistered: UserRegistrationState = UserRegistrationState.MakingRequest,
    val nameInput: String = "",
    val emailInput: String = "whatever@gmail.com",
    val passwordInput: String = "123456@A",
    val termsAgreed: Boolean = false,
    val passwordVisible: Boolean = false,
    val inputError: InputError? = null
)

enum class UserRegistrationState {
    HaveAnAccount, NotRegistered, MakingRequest
}

enum class InputError {
    PasswordLength, PasswordShouldContainSymbols, EmailShouldContain, NameLength
}
