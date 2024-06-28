package com.zekony.feature.settings.mvi

import com.finto.domain.registration.GoogleAuthUiClient
import com.finto.utility.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authenticationRepository: GoogleAuthUiClient
) : MviViewModel<SettingsState, SettingsSideEffect, SettingsEvent>(SettingsState()) {

    init {
        dispatch(SettingsEvent.Initialize)
    }

    override fun dispatch(event: SettingsEvent) {
        when (event) {

            SettingsEvent.NavigateHomeScreen -> navigateHomeScreen()
            SettingsEvent.Initialize -> initialize()
            SettingsEvent.OnSignOut -> onSignOut()

            SettingsEvent.ChangeName -> changeName()
        }
    }

    private fun changeName() = intent {

    }

    private fun initialize() = intent {
        val currentUser = authenticationRepository.getCurrentUser().firstOrNull()
        reduce { state.copy(currentUser = currentUser) }
    }

    private fun onSignOut() = intent {
        authenticationRepository.signOut()
        postSideEffect(SettingsSideEffect.OnSignOut)
    }

    private fun navigateHomeScreen() = intent {
        postSideEffect(SettingsSideEffect.NavigateHomeScreen)
    }
}