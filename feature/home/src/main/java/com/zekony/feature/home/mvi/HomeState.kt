package com.zekony.feature.home.mvi

import com.zekony.domain.home.Project
import com.zekony.domain.home.Task
import com.zekony.domain.home.User

val projectsSampleList = listOf(
    Project(
        name = "Real Estate Website Design",
        dueDateEpochSeconds = 1715864794L,
        description = "", projectMembers = emptyList(),
        projectTasks = listOf(
            Task(
                name = "User Interviews",
                isCompleted = true
            ),
            Task(
                name = "Wireframes",
                isCompleted = true
            ),
            Task(
                name = "Design System",
                isCompleted = true
            ),
            Task(
                name = "Icons",
                isCompleted = true
            ),
            Task(
                name = "Final Mockups",
                isCompleted = true
            ),
        )
    ),
    Project(
        name = "Dashboard & App Design",
        dueDateEpochSeconds = 1715864794L,
        description = "", projectMembers = emptyList(),
        projectTasks = listOf(
            Task(
                name = "User Interviews",
                isCompleted = true
            ),
            Task(
                name = "Wireframes",
                isCompleted = true
            ),
            Task(
                name = "Design System",
                isCompleted = true
            ),
            Task(
                name = "Icons",
                isCompleted = true
            ),
            Task(
                name = "Final Mockups",
                isCompleted = true
            ),
        )
    ),
    Project(
        name = "Real Estate App Design",
        dueDateEpochSeconds = 1715864794L,
        description = "", projectMembers = emptyList(),
        projectTasks = listOf(
            Task(
                name = "User Interviews",
                isCompleted = true
            ),
            Task(
                name = "Wireframes",
                isCompleted = true
            ),
            Task(
                name = "Design System",
                isCompleted = true
            ),
            Task(
                name = "Icons",
                isCompleted = true
            ),
            Task(
                name = "Final Mockups",
                isCompleted = true
            ),
        )
    ),
    Project(
        name = "Wallet Mobile App Design",
        dueDateEpochSeconds = 1715864794L,
        description = "", projectMembers = emptyList(),
        projectTasks = listOf(
            Task(
                name = "User Interviews",
                isCompleted = true
            ),
            Task(
                name = "Wireframes",
                isCompleted = true
            ),
            Task(
                name = "Design System",
                isCompleted = false
            ),
            Task(
                name = "Icons",
                isCompleted = true
            ),
            Task(
                name = "Final Mockups",
                isCompleted = true
            ),
        )
    ),
    Project(
        name = "Finance Mobile App Design",
        dueDateEpochSeconds = 1715864794L,
        description = "", projectMembers = emptyList(),
        projectTasks = listOf(
            Task(
                name = "User Interviews",
                isCompleted = true
            ),
            Task(
                name = "Wireframes",
                isCompleted = false
            ),
            Task(
                name = "Design System",
                isCompleted = true
            ),
            Task(
                name = "Icons",
                isCompleted = true
            ),
            Task(
                name = "Final Mockups",
                isCompleted = false
            ),
        )
    ),
    Project(
        name = "Mobile App Wireframe",
        dueDateEpochSeconds = 1715864794L,
        description = "", projectMembers = emptyList(),
        projectTasks = listOf(
            Task(
                name = "User Interviews",
                isCompleted = false
            ),
            Task(
                name = "Wireframes",
                isCompleted = false
            ),
            Task(
                name = "Design System",
                isCompleted = false
            ),
            Task(
                name = "Icons",
                isCompleted = false
            ),
            Task(
                name = "Final Mockups",
                isCompleted = true
            ),
        )
    )
)

data class HomeState(
    val user: User = User(name = "Alyssa Chen"),
    val searchInput: String = "",
    val completedProjectsList: List<Project> = emptyList(),
    val uncompletedProjectsList: List<Project> = emptyList(),

    )

