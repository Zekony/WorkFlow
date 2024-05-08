package com.zekony.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.zekony.feature.preview.PREVIEW_ROUTE
import com.zekony.feature.preview.previewEntry
import com.zekony.resources.theme.WorkFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    WorkFlowTheme {
        Scaffold(
            bottomBar = {}
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = PREVIEW_ROUTE,
                modifier = Modifier.padding(innerPadding)
            ) {
                previewEntry(
                    navigate = {}
                )

            }
        }
    }
}