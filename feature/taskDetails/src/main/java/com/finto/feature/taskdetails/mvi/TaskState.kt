package com.finto.feature.taskdetails.mvi

import com.finto.domain.home.entities.Project

data class TaskState(
    val project: Project = Project(),
    val newTaskDialogIsOn: Boolean = false,
    val newTaskNameInput: String = "",
)