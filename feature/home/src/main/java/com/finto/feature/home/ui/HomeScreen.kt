package com.finto.feature.home.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.finto.resources.R
import com.finto.feature.home.mvi.DownloadState
import com.finto.feature.home.mvi.HomeEvent
import com.finto.feature.home.mvi.HomeState
import com.finto.feature.home.ui.composables.HomeScreenContent
import com.finto.feature.home.ui.composables.NoProjectsScreen

@Composable
fun HomeScreen(state: HomeState, onEvent: (HomeEvent) -> Unit) {

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Log.d("Zenais", "TEST: HomeScreen: user: ${state.user},list empty and download is done ${state.allProjectsList.isEmpty() && state.downloadState == DownloadState.Done} ")
        when {
            state.user.id.isEmpty() -> {
                Log.d("Zenais", "TEST: HomeScreen: 1")
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.testTag(stringResource(R.string.loading_user_test_tag))
                    )
                }
            }

            state.allProjectsList.isEmpty() && state.downloadState == DownloadState.Done -> {
                Log.d("Zenais", "TEST: HomeScreen: 2")
                NoProjectsScreen(onEvent)
            }

            else -> {
                Log.d("Zenais", "TEST: HomeScreen: 3")
                HomeScreenContent(state, onEvent)
            }
        }
    }
}