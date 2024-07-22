package com.finto.feature.home.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.finto.feature.home.mvi.HomeEvent
import com.finto.feature.home.mvi.HomeState
import com.finto.feature.home.mvi.SearchFilter
import com.finto.resources.R


@Composable
fun HomeScreenContent(state: HomeState, onEvent: (HomeEvent) -> Unit) {

    SearchRow(searchInput = state.searchInput, onEvent = onEvent)

    when (state.searchFilter) {
        SearchFilter.ByCompleted -> {
            ProjectsTitle(
                titleText = stringResource(R.string.completed_tasks),
                onSeeAllClick = { onEvent(HomeEvent.ChangeSearchFilter(SearchFilter.All)) },
                buttonText = stringResource(R.string.back_button)
            )
            ProjectsColumn(
                if (state.searchInput.isEmpty()) state.completedProjectsList else state.searchedProjectsList,
                onEvent
            )
        }

        SearchFilter.ByUncompleted -> {
            ProjectsTitle(
                titleText = stringResource(R.string.ongoing_projects),
                onSeeAllClick = { onEvent(HomeEvent.ChangeSearchFilter(SearchFilter.All)) },
                buttonText = stringResource(R.string.back_button)
            )
            ProjectsColumn(
                if (state.searchInput.isEmpty()) state.uncompletedProjectsList else state.searchedProjectsList,
                onEvent
            )
        }

        SearchFilter.All -> {
            if (state.searchInput.isEmpty()) {
                ProjectsTitle(
                    titleText = stringResource(R.string.completed_tasks),
                    onSeeAllClick = { onEvent(HomeEvent.ChangeSearchFilter(SearchFilter.ByCompleted)) }
                )
                ProjectsLazyRow(state, onEvent)
                ProjectsTitle(
                    titleText = stringResource(R.string.ongoing_projects),
                    onSeeAllClick = { onEvent(HomeEvent.ChangeSearchFilter(SearchFilter.ByUncompleted)) }
                )
                ProjectsColumn(state.uncompletedProjectsList, onEvent)
            } else {
                ProjectsColumn(state.searchedProjectsList, onEvent)
            }
        }
    }
}