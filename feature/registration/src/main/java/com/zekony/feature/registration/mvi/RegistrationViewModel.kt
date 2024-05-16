package com.zekony.feature.registration.mvi

import android.util.Log
import com.zekony.domain.registration.RegistrationRepository
import com.zekony.feature.registration.ui.auth_logic.SignInResult
import com.zekony.feature.registration.util.containsAscii
import com.zekony.feature.registration.util.containsNumbers
import com.zekony.feature.registration.util.containsUpperCase
import com.zekony.feature.registration.util.isEmailValid
import com.zekony.utility.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

const val TAG = "RegistrationViewModel"
const val PASSWORD_MAX_LENGTH = 16
const val NAME_MAX_LENGTH = 20
const val EMAIL_MAX_LENGTH = 20

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: RegistrationRepository
) : MviViewModel<RegistrationState, RegistrationSideEffect, RegistrationEvent>(RegistrationState()) {

    init {
        dispatch(RegistrationEvent.CheckRegistration)
    }

    override fun dispatch(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.AgreeWithTerms -> agreeWithTerms()

            is RegistrationEvent.OnNameInput -> onNameInput(event.name)
            is RegistrationEvent.OnEmailInput -> onEmailInput(event.email)
            is RegistrationEvent.OnPasswordInput -> onPasswordInput(event.password)
            RegistrationEvent.ChangePasswordVisibility -> changePasswordVisibility()

            RegistrationEvent.OnRegistration -> register()
            RegistrationEvent.CheckRegistration -> checkRegistration()

            RegistrationEvent.OnGoogleButtonClick -> onGoogleButtonRegistration()
            is RegistrationEvent.OnGoogleRegistrationIntent -> onGoogleSignInResult(event.result)

            RegistrationEvent.ToRegistrationScreen -> toRegistrationScreen()
            RegistrationEvent.ToSigningScreen -> toSigningScreen()

            RegistrationEvent.NavigateHomeScreen -> navigateHomeScreen()
        }
    }

    private fun toSigningScreen() = intent {
        reduce {
            state.copy(
                isRegistered = UserRegistrationState.IsRegistered
            )
        }
    }

    private fun toRegistrationScreen() = intent {
        reduce {
            state.copy(
                isRegistered = UserRegistrationState.NotRegistered
            )
        }
    }

    private fun onGoogleButtonRegistration() = intent {
        postSideEffect(RegistrationSideEffect.GoogleAuth)
    }

    private fun navigateHomeScreen() = intent {
        postSideEffect(RegistrationSideEffect.NavigateHomeScreen)
    }


    private fun onGoogleSignInResult(result: SignInResult) = intent {
        Log.d(TAG, "onSignInResult: result data ${result.data} message ${result.errorMessage}")
        reduce {
            state.copy(
                isRegistered = if (result.data != null) UserRegistrationState.IsRegistered else UserRegistrationState.Error,
                registrationError = result.errorMessage
            )
        }
    }

    private fun agreeWithTerms() = intent {
        reduce {
            state.copy(termsAgreed = !state.termsAgreed)
        }
    }

    private fun onPasswordInput(password: String) = intent {
        reduce {
            state.copy(passwordInput = if (password.length > PASSWORD_MAX_LENGTH) state.passwordInput else password)
        }
    }

    private fun validateFields() = intent {
        val passwordIsLongEnough =
            state.passwordInput.isNotEmpty() && state.passwordInput.length in 6..9
        val passwordContainsSymbols = state.passwordInput.isNotEmpty() &&
                state.passwordInput.containsAscii() && state.passwordInput.containsNumbers() && state.passwordInput.containsUpperCase()

        val nameIsLongEnough = state.nameInput.isNotEmpty() && state.nameInput.length >= 3

        val emailContainsSymbols = state.emailInput.isNotEmpty() && state.emailInput.isEmailValid()
        postSideEffect(RegistrationSideEffect.PostInputErrorMessage)

        reduce {
            state.copy(
                inputError = when {
                    !passwordIsLongEnough -> InputError.PasswordLength
                    !passwordContainsSymbols -> InputError.PasswordShouldContainSymbols
                    !nameIsLongEnough -> InputError.NameLength
                    !emailContainsSymbols -> InputError.EmailShouldContain
                    else -> null
                }
            )
        }
    }


    private fun onNameInput(name: String) = intent {
        reduce {
            state.copy(nameInput = if (name.length > NAME_MAX_LENGTH) state.nameInput else name)
        }
    }

    private fun onEmailInput(email: String) = intent {
        reduce {
            state.copy(emailInput = if (email.length > EMAIL_MAX_LENGTH) state.emailInput else email)
        }
    }

    private fun changePasswordVisibility() = intent {
        reduce {
            state.copy(passwordVisible = !state.passwordVisible)
        }
    }

    private fun checkRegistration() = intent {
        reduce {
            state.copy(isRegistered = UserRegistrationState.NotRegistered)
        }
    }

    private fun register() = intent {
        validateFields().join()
        if (state.inputError == null) {
            repository.createNewUser(
                email = state.emailInput.trim(),
                password = state.passwordInput.trim()
            ).collect {
                Log.d("Zenais", "auth result: create new user $it")
            }
        }
    }
}