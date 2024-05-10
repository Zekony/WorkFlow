package com.zekony.feature.registration.mvi

import com.zekony.utility.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

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

    private fun checkRegistration() = intent{
        reduce {
            state.copy(isRegistered = UserRegistrationState.NotRegistered)
        }
    }

    private fun register() {

    }
}