package com.finto.feature.registration.mvi

import android.util.Log
import com.finto.domain.registration.RegistrationRepository
import com.finto.utility.classes.ResultState
import com.finto.feature.registration.ui.auth_logic.SignInResult
import com.finto.feature.registration.util.containsAscii
import com.finto.feature.registration.util.containsNumbers
import com.finto.feature.registration.util.containsUpperCase
import com.finto.feature.registration.util.isEmailValid
import com.finto.utility.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
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
            RegistrationEvent.OnSigning -> signIn()
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
                isRegistered = UserRegistrationState.HaveAnAccount
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
        Log.d(
            TAG,
            "onSignInResult: result data ${result.data} message ${result.errorMessage}"
        ) // TODO() wait for sha key to available or change it
        reduce {
            state.copy(

            )
        }
    }

    private fun agreeWithTerms() = intent {
        reduce {
            state.copy(termsAgreed = !state.termsAgreed)
        }
    }


    private fun validateFields() = intent {
        val passwordIsLongEnough =
            state.passwordInput.isNotEmpty() && state.passwordInput.length > 6
        val passwordContainsSymbols = state.passwordInput.isNotEmpty() &&
                state.passwordInput.containsAscii() && state.passwordInput.containsNumbers() && state.passwordInput.containsUpperCase()

        val nameIsLongEnough = state.nameInput.isNotEmpty() && state.nameInput.length >= 3

        val emailContainsSymbols = state.emailInput.isNotEmpty() && state.emailInput.isEmailValid()


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

    @OptIn(OrbitExperimental::class)
    private fun onPasswordInput(password: String) = blockingIntent {
        reduce {
            state.copy(passwordInput = if (password.length > PASSWORD_MAX_LENGTH) state.passwordInput else password.trim())
        }
    }

    @OptIn(OrbitExperimental::class)
    private fun onNameInput(name: String) = blockingIntent {
        reduce {
            state.copy(nameInput = if (name.length > NAME_MAX_LENGTH) state.nameInput else name)
        }
    }

    @OptIn(OrbitExperimental::class)
    private fun onEmailInput(email: String) = blockingIntent {
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
        validateFields()
        if (state.inputError != null) {
            postSideEffect(RegistrationSideEffect.PostInputErrorMessage)
        } else {
            repository.createNewUser(
                email = state.emailInput.trim(),
                password = state.passwordInput.trim()
            ).collect { authResult ->
                Log.d("Zenais", "auth result: create new user $authResult")
                when (authResult) {
                    is ResultState.Error -> {
                        Log.d("Zenais", "auth result: error ${authResult.message}")
                        postSideEffect(RegistrationSideEffect.PostSigningErrorMessage(authResult.message))
                        reduce { state.copy(isRegistered = UserRegistrationState.NotRegistered) }

                    }

                    is ResultState.Loading -> {
                        Log.d("Zenais", "auth result: loading")
                        reduce { state.copy(isRegistered = UserRegistrationState.MakingRequest) }
                    }

                    is ResultState.Success -> {
                        Log.d("Zenais", "auth result: success ${authResult.data}")
                        postSideEffect(RegistrationSideEffect.NavigateHomeScreen)
                    }
                }
            }
        }
    }

    private fun signIn() = intent {

        repository.login(
            email = state.emailInput.trim(),
            password = state.passwordInput
        ).collect { signinResult ->
            Log.d("Zenais", "auth result: login user $signinResult")
            when (signinResult) {
                is ResultState.Error -> {
                    Log.d("Zenais", "auth result: login error ${signinResult.message}")
                    postSideEffect(RegistrationSideEffect.PostSigningErrorMessage(signinResult.message))
                    reduce { state.copy(isRegistered = UserRegistrationState.HaveAnAccount) }
                }

                is ResultState.Loading -> {
                    Log.d("Zenais", "auth result: login loading")
                    reduce { state.copy(isRegistered = UserRegistrationState.MakingRequest) }
                }

                is ResultState.Success -> {
                    Log.d("Zenais", "auth result: login success ${signinResult.data}")
                    postSideEffect(RegistrationSideEffect.NavigateHomeScreen)
                }
            }
        }
    }
}