package com.finto.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.finto.feature.home.mvi.HomeSideEffect
import com.finto.feature.home.mvi.HomeState
import com.finto.feature.home.mvi.HomeViewModel
import com.finto.feature.home.ui.HomeScreen
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeEntry(
    navigateToProjectDetails: (String) -> Unit
) {
    composable(HOME_ROUTE) {
        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.collectAsState()
        Scaffold(
            topBar = { HomeTopBar(state) }
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
                HomeSideEffect.NavigateToSettings -> {}
            }
        }
    }
}

@Composable
fun HomeTopBar(state: HomeState) {
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
            Text(text = state.user.name, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
