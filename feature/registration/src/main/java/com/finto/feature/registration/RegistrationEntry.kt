package com.finto.feature.registration

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.android.gms.auth.api.identity.Identity
import com.finto.feature.registration.mvi.InputError.*
import com.finto.feature.registration.mvi.RegistrationEvent
import com.finto.feature.registration.mvi.RegistrationSideEffect
import com.finto.feature.registration.mvi.RegistrationViewModel
import com.finto.feature.registration.mvi.UserRegistrationState
import com.finto.feature.registration.ui.LoadingScreen
import com.finto.feature.registration.ui.RegistrationScreen
import com.finto.feature.registration.ui.SigningScreen
import com.finto.feature.registration.ui.auth_logic.GoogleAuthUiClient
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

internal const val TAG = "registrationEntry"
const val REGISTRATION_ROUTE = "registration"

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.registrationEntry(
    navigateHome: () -> Unit
) {

    composable(REGISTRATION_ROUTE) {
        val viewModel: RegistrationViewModel = hiltViewModel()
        val state by viewModel.collectAsState()
        val onEvent = viewModel::dispatch
        val snackbarHost = remember { SnackbarHostState() }

        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        val googleAuthUiClient by lazy {
            GoogleAuthUiClient(
                context = context,
                oneTapClient = Identity.getSignInClient(context)
            )
        }

        LaunchedEffect(key1 = Unit) {
            if (googleAuthUiClient.getSignedInUser() != null) {
                onEvent(RegistrationEvent.NavigateHomeScreen)
            }
        }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { result ->
                Log.d(TAG, "registrationEntry: activity intent result $result")
                if (result.resultCode == Activity.RESULT_OK) {
                    scope.launch {
                        val signInResult = googleAuthUiClient.signInWithIntent(
                            intent = result.data ?: return@launch
                        )
                        onEvent(RegistrationEvent.OnGoogleRegistrationIntent(signInResult))
                    }
                }
            }
        )

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


        viewModel.collectSideEffect {
            when (it) {
                RegistrationSideEffect.NavigateHomeScreen -> navigateHome()
                RegistrationSideEffect.GoogleAuth -> {
                    scope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }

                RegistrationSideEffect.PostInputErrorMessage -> {
                    if (state.inputError != null)
                        snackbarHost.showSnackbar(
                            message = when (state.inputError) {
                                PasswordLength -> "Password is too short"
                                PasswordShouldContainSymbols -> "Password should contain 1 uppercase letter, 1 number and 1 special symbol"
                                EmailShouldContain -> "Invalid email"
                                NameLength -> "Name is too short"
                                null -> "Never reachable but still should be here"
                            },
                        )
                }

                is RegistrationSideEffect.PostSigningErrorMessage -> {
                    snackbarHost.showSnackbar(it.message ?: "Error happened")
                }
            }
        }
    }
}