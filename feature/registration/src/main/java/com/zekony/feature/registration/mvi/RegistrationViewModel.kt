package com.zekony.feature.registration.mvi

import android.util.Log
import com.zekony.feature.registration.ui.auth_logic.SignInResult
import com.zekony.utility.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject
const val TAG = "RegistrationViewModel"
@HiltViewModel
class RegistrationViewModel @Inject constructor(

) : MviViewModel<RegistrationState, RegistrationSideEffect, RegistrationEvent>(RegistrationState()) {

    init {
        dispatch(RegistrationEvent.CheckRegistration)
    }

    override fun dispatch(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.OnRegistration -> register()
            RegistrationEvent.CheckRegistration -> checkRegistration()
            RegistrationEvent.AgreeWithTerms -> agreeWithTerms()
            RegistrationEvent.ChangePasswordVisibility -> changePasswordVisibility()
            is RegistrationEvent.OnEmailInput -> onEmailInput(event.email)
            is RegistrationEvent.OnNameInput -> onNameInput(event.name)
            is RegistrationEvent.OnPasswordInput -> onPasswordInput(event.password)

            RegistrationEvent.OnGoogleButtonClick -> onGoogleButtonRegistration()
            is RegistrationEvent.OnGoogleRegistrationIntent -> onSignInResult(event.result)

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


    private fun onSignInResult(result: SignInResult) = intent {
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
            state.copy(passwordInput = password)
        }
    }

    private fun onNameInput(name: String) = intent {
        reduce {
            state.copy(nameInput = name)
        }
    }

    private fun onEmailInput(email: String) = intent {
        reduce {
            state.copy(emailInput = email)
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

    private fun register() {

    }
}