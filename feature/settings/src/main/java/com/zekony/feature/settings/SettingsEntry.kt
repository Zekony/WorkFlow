package com.zekony.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.finto.resources.R
import com.finto.utility.sharedComposables.ScreenTitleTopBar
import com.zekony.feature.settings.mvi.SettingsEvent
import com.zekony.feature.settings.mvi.SettingsSideEffect
import com.zekony.feature.settings.mvi.SettingsViewModel
import com.zekony.feature.settings.ui.SettingsScreen
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

const val SETTINGS_ROUTE = "settingsEntry"

fun NavGraphBuilder.settingsEntry(
    navigateHomeScreen: () -> Unit,
    signOut: () -> Unit
) {
    composable(SETTINGS_ROUTE) {
        val viewModel: SettingsViewModel = hiltViewModel()
        val state by viewModel.collectAsState()
        val snackbarHost = remember { SnackbarHostState() }
        val onEvent = viewModel::dispatch

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHost) },
            topBar = {
                ScreenTitleTopBar(
                    title = stringResource(R.string.profile),
                    navigateBack = { onEvent(SettingsEvent.NavigateHomeScreen) }
                )
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                SettingsScreen(state, onEvent)
            }
        }

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is SettingsSideEffect.NavigateHomeScreen -> {
                    navigateHomeScreen()
                }

                SettingsSideEffect.OnSignOut -> {
                    signOut()
                }
            }
        }
    }
}
