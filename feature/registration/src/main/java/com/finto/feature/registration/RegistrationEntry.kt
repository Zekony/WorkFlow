package com.finto.feature.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.finto.feature.registration.mvi.InputError.EmailShouldContain
import com.finto.feature.registration.mvi.InputError.NameLength
import com.finto.feature.registration.mvi.InputError.PasswordLength
import com.finto.feature.registration.mvi.InputError.PasswordShouldContainSymbols
import com.finto.feature.registration.mvi.RegistrationSideEffect
import com.finto.feature.registration.mvi.RegistrationViewModel
import com.finto.feature.registration.mvi.UserRegistrationState
import com.finto.feature.registration.ui.LoadingScreen
import com.finto.feature.registration.ui.RegistrationScreen
import com.finto.feature.registration.ui.SigningScreen
import com.finto.resources.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

const val REGISTRATION_ROUTE = "registration"

fun NavGraphBuilder.registrationEntry(
    navigateHome: () -> Unit
) {

    composable(REGISTRATION_ROUTE) {
        val viewModel: RegistrationViewModel = hiltViewModel()
        val state by viewModel.collectAsState()
        val onEvent = viewModel::dispatch
        val snackbarHost = remember { SnackbarHostState() }
        val context = LocalContext.current

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHost) }
        ) {
            Column(modifier = Modifier.padding(it)) {
                when (state.isRegistered) {
                    UserRegistrationState.HaveAnAccount -> SigningScreen(state, onEvent)
                    UserRegistrationState.NotRegistered -> RegistrationScreen(state, onEvent)
                    UserRegistrationState.MakingRequest -> LoadingScreen()
                }
            }
        }


        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                RegistrationSideEffect.NavigateHomeScreen -> navigateHome()

                RegistrationSideEffect.PostInputErrorMessage -> {
                    if (state.inputError != null)
                        snackbarHost.showSnackbar(
                            message = when (state.inputError) {
                                PasswordLength -> context.getString(R.string.password_is_too_short)
                                PasswordShouldContainSymbols -> context.getString(R.string.password_special_symbols_error)
                                EmailShouldContain -> context.getString(R.string.invalid_email_error)
                                NameLength -> context.getString(R.string.name_is_too_short_error)
                                null -> "" // Never reachable but still should be here
                            },
                        )
                }

                is RegistrationSideEffect.PostSigningErrorMessage -> {
                    snackbarHost.showSnackbar(sideEffect.message ?: context.getString(R.string.error_message))
                }
            }
        }
    }
}