package com.finto.feature.createproject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
        val id = entry.arguments?.getString("id") ?: ""
        val viewModel: ProjectViewModel =
            hiltViewModel<ProjectViewModel, ProjectViewModel.ProjectViewModelFactory> { factory ->
                factory.create(id)
            }

        val state by viewModel.collectAsState()
        val onEvent = viewModel::dispatch
        Scaffold(
            topBar = {
                ScreenTitleTopBar(
                    title = if (id.isNotEmpty()) "Change Project" else "Create New Project",
                    navigateBack = { onEvent(ProjectEvent.OnNavigateBack) }
                )
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                NewProjectScreen(state, viewModel::dispatch)
            }
        }

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                ProjectSideEffect.NavigateBack -> navigateBack()
            }
        }
    }
}


