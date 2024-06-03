package com.finto.feature.createproject.mvi

import com.finto.domain.home.entities.Project
import com.finto.domain.home.entities.User

data class ProjectState(
    val currentProject: Project = Project(),
    val usersList: List<User> = emptyList(), // list of all users
    val possibleUsersList: List<User> = emptyList(), // list of all users who are not added to project
    val showAddUserDialog: Boolean = false,
    val chosenUsersIdList: List<String> = emptyList(), // list of users' ids who are chosen in a dialog to add to project
    val showTimePickerDialog: Boolean = false,
    val showDatePickerDialog: Boolean = false
)