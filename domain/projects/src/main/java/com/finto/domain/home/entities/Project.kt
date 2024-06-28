package com.finto.domain.home.entities


data class Project(
    val id: String = "",
    val title: String = "",
    val projectCreator: User = User(),
    val dueDateEpochSeconds: Long = 1717412725L,
    val description: String = "",
    val projectMembers: List<User> = emptyList(),
    val projectTasks: List<Task> = emptyList()
)
