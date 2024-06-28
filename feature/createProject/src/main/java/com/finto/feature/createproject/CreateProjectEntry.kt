package com.finto.feature.createproject

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
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.finto.resources.R
import com.finto.feature.createproject.mvi.ProjectEvent
import com.finto.feature.createproject.mvi.ProjectSideEffect
import com.finto.feature.createproject.mvi.ProjectViewModel
import com.finto.feature.createproject.ui.NewProjectScreen
import com.finto.utility.sharedComposables.ScreenTitleTopBar
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

const val CREATE_PROJECT_ROUTE = "createProject"

fun NavGraphBuilder.createProjectEntry(
    navigateBack: () -> Unit
) {
    composable(
        route = "${CREATE_PROJECT_ROUTE}/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )) { entry ->
        val id = entry.arguments?.getString("id")?.let {
            if (it == "{id}") "" else it
        } ?: ""
        val viewModel: ProjectViewModel =
            hiltViewModel<ProjectViewModel, ProjectViewModel.ProjectViewModelFactory> { factory ->
                factory.create(id)
            }

        val state by viewModel.collectAsState()
        val onEvent = viewModel::dispatch
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            topBar = {
                ScreenTitleTopBar(
                    title = if (id.isEmpty()) stringResource(R.string.create_new_project)
                    else stringResource(R.string.change_project),
                    navigateBack = { onEvent(ProjectEvent.OnNavigateBack) }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) {
            Column(modifier = Modifier.padding(it)) {
                NewProjectScreen(state, viewModel::dispatch)
            }
        }

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                ProjectSideEffect.NavigateBack -> navigateBack()
                is ProjectSideEffect.ShowErrorMessage -> {
                    if (sideEffect.message.isNotEmpty()) snackbarHostState.showSnackbar(sideEffect.message)
                }
            }
        }
    }
}


