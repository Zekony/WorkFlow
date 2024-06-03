package com.finto.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finto.feature.home.mvi.HomeEvent
import com.finto.feature.home.mvi.HomeState
import com.finto.feature.home.ui.composables.ProjectsLazyRow
import com.finto.feature.home.ui.composables.ProjectsTitle
import com.finto.feature.home.ui.composables.SearchRow
import com.finto.feature.home.ui.composables.ProjectsColumn

@Composable
fun HomeScreen(state: HomeState, onEvent: (HomeEvent) -> Unit) {

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SearchRow(state, onEvent)
        if (state.searchInput.isEmpty()) {
            ProjectsTitle("Completed tasks") {} // TODO() add seeAll navigation
            ProjectsLazyRow(state, onEvent)
            ProjectsTitle("Ongoing projects") {}
            ProjectsColumn(state.uncompletedProjectsList, onEvent)
        } else {
            ProjectsColumn(state.searchedProjectsList, onEvent)
        }
    }
}




