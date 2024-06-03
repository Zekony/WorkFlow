package com.finto.feature.taskdetails.mvi

sealed interface TaskEvent {
    data object Initialize : TaskEvent
    data object OnNavigateBack : TaskEvent
    data object OpenAddTaskDialog : TaskEvent
    data object SaveNewTask : TaskEvent

    data object ChangeProject : TaskEvent
    data class CheckTaskIsDone(val taskId: String) : TaskEvent
    data class OnNewTaskNameInput(val text: String) : TaskEvent
}