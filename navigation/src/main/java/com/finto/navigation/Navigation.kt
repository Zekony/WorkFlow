package com.finto.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.finto.feature.createproject.CREATE_PROJECT_ROUTE
import com.finto.feature.createproject.createProjectEntry
import com.finto.feature.home.HOME_ROUTE
import com.finto.feature.home.homeEntry
import com.finto.feature.preview.PREVIEW_ROUTE
import com.finto.feature.preview.previewEntry
import com.finto.feature.registration.REGISTRATION_ROUTE
import com.finto.feature.registration.registrationEntry
import com.finto.feature.taskdetails.TASK_ROUTE
import com.finto.feature.taskdetails.taskEntry
import com.finto.resources.theme.WorkFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val noBottomBarScreens = listOf(
        REGISTRATION_ROUTE, PREVIEW_ROUTE, "${TASK_ROUTE}/{id}", "$CREATE_PROJECT_ROUTE/{id}"
    )
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    WorkFlowTheme {
        Scaffold(
            bottomBar = {
                if (!noBottomBarScreens.contains(currentDestination)) AppBottomBar(navController)
            }
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = PREVIEW_ROUTE,
                modifier = Modifier.padding(innerPadding)
            ) {
                previewEntry(
                    navigate = {
                        navController.navigate(REGISTRATION_ROUTE) {
                            popUpTo(PREVIEW_ROUTE) { inclusive = true }
                        }
                    }
                )
                registrationEntry(
                    navigateHome = {
                        navController.navigate(HOME_ROUTE) {
                            popUpTo(REGISTRATION_ROUTE) { inclusive = true }
                        }
                    }
                )
                homeEntry(
                    navigateToProjectDetails = { navController.navigate("${TASK_ROUTE}/$it") }
                )
                taskEntry(
                    navigateBack = { navController.popBackStack() },
                    navigateChangeProject = { navController.navigate("${CREATE_PROJECT_ROUTE}/$it") }
                )
                createProjectEntry(
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}