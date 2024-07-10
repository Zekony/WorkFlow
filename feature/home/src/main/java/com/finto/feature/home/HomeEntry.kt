package com.finto.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.finto.feature.home.mvi.HomeSideEffect
import com.finto.feature.home.mvi.HomeViewModel
import com.finto.feature.home.ui.HomeScreen
import com.finto.resources.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeEntry(
    navigateToProjectDetails: (String) -> Unit,
    navigateToSettings: () -> Unit
) {
    composable(HOME_ROUTE) {
        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.collectAsState()
        val snackbarHost = remember { SnackbarHostState() }
        val context = LocalContext.current

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHost) },
            topBar = { HomeTopBar(state.user.name) }
        ) {
            Column(modifier = Modifier.padding(it)) {
                HomeScreen(state, viewModel::dispatch)
            }
        }

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.NavigateToProject -> {
                    navigateToProjectDetails(sideEffect.id)
                }

                HomeSideEffect.NavigateToSettings -> {
                    navigateToSettings()
                }
                is HomeSideEffect.PostErrorMessage -> {
                    snackbarHost.showSnackbar(
                        sideEffect.message ?: context.getString(R.string.error_message)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeTopBar(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = "Welcome back!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = userName, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
