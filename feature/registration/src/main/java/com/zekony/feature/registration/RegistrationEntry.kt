package com.zekony.feature.registration

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.android.gms.auth.api.identity.Identity
import com.zekony.feature.registration.mvi.RegistrationEvent
import com.zekony.feature.registration.mvi.RegistrationSideEffect
import com.zekony.feature.registration.mvi.RegistrationViewModel
import com.zekony.feature.registration.mvi.UserRegistrationState
import com.zekony.feature.registration.ui.ErrorScreen
import com.zekony.feature.registration.ui.LoadingScreen
import com.zekony.feature.registration.ui.RegistrationScreen
import com.zekony.feature.registration.ui.SigningScreen
import com.zekony.feature.registration.ui.auth_logic.GoogleAuthUiClient
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

val TAG = "registrationEntry"
const val REGISTRATION_ROUTE = "registration"
fun NavGraphBuilder.registrationEntry(
    navigateHome: () -> Unit
) {


    composable(REGISTRATION_ROUTE) {
        val viewModel: RegistrationViewModel = hiltViewModel()
        val state by viewModel.collectAsState()
        val onEvent = viewModel::dispatch

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

        LaunchedEffect(key1 = state.isRegistered) {
            if (state.isRegistered == UserRegistrationState.IsRegistered) {
                Toast.makeText(
                    context,
                    "Sign in successful",
                    Toast.LENGTH_LONG
                ).show()

                //viewModel.resetState()
            }
        }
        Log.d(TAG, "registrationEntry: registration state: ${state.isRegistered}")
        when (state.isRegistered) {
            UserRegistrationState.IsRegistered -> SigningScreen(state, onEvent)
            UserRegistrationState.NotRegistered -> RegistrationScreen(state, onEvent)
            UserRegistrationState.MakingRequest -> LoadingScreen()
            UserRegistrationState.Error -> ErrorScreen(state, onEvent)
        }


        viewModel.collectSideEffect {
            when (it) {
                RegistrationSideEffect.NavigateBack -> TODO()
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
            }
        }
    }
}