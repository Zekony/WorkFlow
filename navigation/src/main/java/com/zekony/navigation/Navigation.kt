package com.zekony.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zekony.feature.home.HOME_ROUTE
import com.zekony.feature.home.homeEntry
import com.zekony.feature.preview.PREVIEW_ROUTE
import com.zekony.feature.preview.previewEntry
import com.zekony.feature.registration.REGISTRATION_ROUTE
import com.zekony.feature.registration.registrationEntry
import com.zekony.resources.theme.WorkFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val noBottomBarScreens = listOf(
        REGISTRATION_ROUTE, PREVIEW_ROUTE
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
                homeEntry()
            }
        }
    }
}