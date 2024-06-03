package com.finto.feature.taskdetails

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
import com.finto.feature.taskdetails.mvi.TaskEvent
import com.finto.feature.taskdetails.mvi.TaskSideEffect
import com.finto.feature.taskdetails.mvi.TaskViewModel
import com.finto.feature.taskdetails.ui.TaskBottomBar
import com.finto.feature.taskdetails.ui.TaskScreen
import com.finto.resources.R.*
import com.finto.utility.sharedComposables.ScreenTitleTopBar
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

const val TASK_ROUTE = "taskEntry"

fun NavGraphBuilder.taskEntry(
    navigateBack: () -> Unit,
    navigateChangeProject: (String) -> Unit
) {
    composable(
        route = "${TASK_ROUTE}/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )
    ) { entry ->
        val id = entry.arguments?.getString("id") ?: ""
        val viewModel =
            hiltViewModel<TaskViewModel, TaskViewModel.TaskViewModelFactory> { factory ->
                factory.create(id)
            }
        val state by viewModel.collectAsState()
        val onEvent = viewModel::dispatch
        Scaffold(
            topBar = {
                ScreenTitleTopBar(
                    title = "Task Details",
                    navigateBack = { onEvent(TaskEvent.OnNavigateBack) },
                    icon = drawable.add_new_icon,
                    action = { onEvent(TaskEvent.ChangeProject) }
                )
            },
            bottomBar = { TaskBottomBar(onEvent) }
        ) {
            Column(modifier = Modifier.padding(it)) {
                TaskScreen(state, onEvent)
            }
        }

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                TaskSideEffect.OnNavigateBack -> {
                    navigateBack()
                }

                is TaskSideEffect.OnNavigateToChangeProject -> {
                    navigateChangeProject(sideEffect.id)
                }
            }
        }
    }
}