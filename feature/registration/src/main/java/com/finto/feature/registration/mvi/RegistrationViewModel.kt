package com.finto.feature.registration.mvi

import android.content.Context
import android.util.Log
import com.finto.domain.registration.EmailAndPassAuthRepository
import com.finto.domain.registration.GoogleAuthUiClient
import com.finto.feature.registration.util.containsAscii
import com.finto.feature.registration.util.containsNumbers
import com.finto.feature.registration.util.containsUpperCase
import com.finto.feature.registration.util.isEmailValid
import com.finto.utility.MviViewModel
import com.finto.utility.classes.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

const val PASSWORD_MAX_LENGTH = 16
const val NAME_MAX_LENGTH = 20
const val EMAIL_MAX_LENGTH = 20

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val emailAndPassAuthRepository: EmailAndPassAuthRepository,
    private val googleAuthUiClient: GoogleAuthUiClient
) : MviViewModel<RegistrationState, RegistrationSideEffect, RegistrationEvent>(RegistrationState()) {

    init {
        dispatch(RegistrationEvent.Initialize)
    }

    override fun dispatch(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.Initialize -> initialize()

            is RegistrationEvent.OnNameInput -> onNameInput(event.name)
            is RegistrationEvent.OnEmailInput -> onEmailInput(event.email)
            is RegistrationEvent.OnPasswordInput -> onPasswordInput(event.password)
            RegistrationEvent.ChangePasswordVisibility -> changePasswordVisibility()

            RegistrationEvent.OnRegistration -> register()
            RegistrationEvent.OnSigning -> signIn()

            is RegistrationEvent.OnGoogleSignIn -> onGoogleSignIn(event.context)

            RegistrationEvent.AgreeWithTerms -> agreeWithTerms()

            RegistrationEvent.ToRegistrationScreen -> toRegistrationScreen()
            RegistrationEvent.ToSigningScreen -> toSigningScreen()

            RegistrationEvent.NavigateHomeScreen -> navigateHomeScreen()
        }
    }

    private fun initialize() {
        checkRegistration()
    }

    private fun onGoogleSignIn(context: Context) = intent {
        val result = googleAuthUiClient.signIn(context)
        if (result.isFailure) {
            postSideEffect(RegistrationSideEffect.PostSigningErrorMessage(result.exceptionOrNull()?.message))
        } else if (result.isSuccess) {
            postSideEffect(RegistrationSideEffect.NavigateHomeScreen)
        }
    }

    private fun toSigningScreen() = intent {
        reduce {
            state.copy(
                isRegistered = UserRegistrationState.HaveAnAccount
            )
        }
    }


    private fun checkRegistration() = intent {
        val currentUser = googleAuthUiClient.getCurrentUser().firstOrNull()
        Log.d("Zenais", "RegistrationViewModel: currentUser $currentUser")
        if (currentUser == null) {
            reduce {
                state.copy(isRegistered = UserRegistrationState.NotRegistered)
            }
        } else {
            postSideEffect(RegistrationSideEffect.NavigateHomeScreen)
        }
    }

    private fun register() = intent {
        validateFields()
        if (state.inputError != null) {
            postSideEffect(RegistrationSideEffect.PostInputErrorMessage)
        } else {
            emailAndPassAuthRepository.createNewUser(
                email = state.emailInput,
                password = state.passwordInput,
                userName = state.nameInput.trim()
            ).collect { authResult ->
                when (authResult) {
                    is ResultState.Error -> {
                        postSideEffect(RegistrationSideEffect.PostSigningErrorMessage(authResult.message))
                        reduce { state.copy(isRegistered = UserRegistrationState.NotRegistered) }
                    }

                    is ResultState.Loading -> {
                        reduce { state.copy(isRegistered = UserRegistrationState.MakingRequest) }
                    }

                    is ResultState.Success -> {
                        postSideEffect(RegistrationSideEffect.NavigateHomeScreen)
                    }
                }
            }
        }
    }

    private fun signIn() = intent {
        emailAndPassAuthRepository.login(
            email = state.emailInput,
            password = state.passwordInput
        ).collect { signInResult ->
            when (signInResult) {
                is ResultState.Error -> {
                    postSideEffect(RegistrationSideEffect.PostSigningErrorMessage(signInResult.message))
                    reduce { state.copy(isRegistered = UserRegistrationState.HaveAnAccount) }
                }

                is ResultState.Loading -> {
                    reduce { state.copy(isRegistered = UserRegistrationState.MakingRequest) }
                }

                is ResultState.Success -> {
                    postSideEffect(RegistrationSideEffect.NavigateHomeScreen)
                }
            }
        }
    }
    private fun toRegistrationScreen() = intent {
        reduce {
            state.copy(
                isRegistered = UserRegistrationState.NotRegistered
            )
        }
    }


    private fun navigateHomeScreen() = intent {
        postSideEffect(RegistrationSideEffect.NavigateHomeScreen)
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
            state.copy(emailInput = if (email.length > EMAIL_MAX_LENGTH) state.emailInput else email.trim())
        }
    }

    private fun changePasswordVisibility() = intent {
        reduce {
            state.copy(passwordVisible = !state.passwordVisible)
        }
    }
}