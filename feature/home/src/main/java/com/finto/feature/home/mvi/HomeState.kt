package com.finto.feature.home.mvi

import com.finto.domain.home.entities.Project
import com.finto.domain.home.entities.User

data class HomeState(
    val user: User = User(name = "Alyssa Chen"),
    val searchInput: String = "",
    val allProjectsList: List<Project> = emptyList(),
    val completedProjectsList: List<Project> = emptyList(),
    val uncompletedProjectsList: List<Project> = emptyList(),
    val searchedProjectsList: List<Project> = emptyList(),
)