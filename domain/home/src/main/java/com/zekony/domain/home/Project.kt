package com.zekony.domain.home


data class Project(
    val id: Int = 0,
    val name: String = "",
    val dueDateEpochSeconds: Long,
    val description: String = "",
    val projectMembers: List<User> = emptyList(),
    val projectTasks: List<Task> = emptyList()
)
