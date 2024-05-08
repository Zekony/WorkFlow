package com.zekony.feature.registration

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zekony.feature.registration.mvi.RegistrationViewModel
import com.zekony.feature.registration.mvi.UserRegistrationState
import com.zekony.feature.registration.mvi.UserRegistrationState.*
import com.zekony.feature.registration.ui.SigningScreen
import com.zekony.feature.registration.ui.RegistrationScreen
import com.zekony.feature.registration.ui.LoadingScreen
import com.zekony.feature.registration.ui.ErrorScreen
import org.orbitmvi.orbit.compose.collectAsState

const val REGISTRATION_ROUTE = "registration"
fun NavGraphBuilder.registrationEntry() {

    composable(REGISTRATION_ROUTE) {
        val viewModel: RegistrationViewModel = hiltViewModel()
        val state by viewModel.collectAsState()

        when(state.isRegistered) {
            IsRegistered -> SigningScreen(state, viewModel::dispatch)
            NotRegistered -> RegistrationScreen(state, viewModel::dispatch)
            MakingRequest ->  LoadingScreen()
            Error -> ErrorScreen(state, viewModel::dispatch)
        }
    }
}