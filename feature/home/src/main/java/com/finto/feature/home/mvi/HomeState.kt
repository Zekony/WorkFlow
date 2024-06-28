package com.finto.feature.home.mvi

import com.finto.domain.home.entities.Project
import com.finto.domain.home.entities.User

data class HomeState(
    val user: User = User(),
    val searchInput: String = "",
    val downloadState: DownloadState = DownloadState.Downloading,
    val allProjectsList: List<Project> = emptyList(),
    val completedProjectsList: List<Project> = emptyList(),
    val uncompletedProjectsList: List<Project> = emptyList(),
    val searchedProjectsList: List<Project> = emptyList(),
    val searchFilter: SearchFilter = SearchFilter.All
)

enum class DownloadState {
    Downloading, Done
}

enum class SearchFilter {
    ByCompleted, ByUncompleted, All
}