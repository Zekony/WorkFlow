package com.finto.feature.taskdetails.mvi

import android.util.Log
import com.finto.domain.home.entities.Task
import com.finto.domain.home.repositories.ProjectsRepository
import com.finto.utility.MviViewModel
import com.finto.utility.classes.ResultState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce


@HiltViewModel(assistedFactory = TaskViewModel.TaskViewModelFactory::class)
class TaskViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val projectsRepository: ProjectsRepository
) : MviViewModel<TaskState, TaskSideEffect, TaskEvent>(TaskState()) {

    @AssistedFactory
    interface TaskViewModelFactory {
        fun create(id: String): TaskViewModel
    }

    init {
        dispatch(TaskEvent.Initialize)
    }


    override fun dispatch(event: TaskEvent) {
        when (event) {
            TaskEvent.Initialize -> initialize()
            TaskEvent.OnNavigateBack -> navigateBack()
            TaskEvent.OpenAddTaskDialog -> openAddTaskDialog()
            TaskEvent.SaveNewTask -> saveTask()
            is TaskEvent.OnNewTaskNameInput -> onNewTaskNameInput(event.text)
            is TaskEvent.CheckTaskIsDone -> checkTaskIsDone(event.taskId)
            is TaskEvent.ChangeProject -> changeProject()
        }
    }

    private fun changeProject() = intent {
        postSideEffect(TaskSideEffect.OnNavigateToChangeProject(id))
    }

    private fun checkTaskIsDone(taskId: String) = intent {
        val taskInQuestion = state.project.projectTasks.first { it.id == taskId }
        val taskIsDone =
            state.project.copy(projectTasks = state.project.projectTasks.toMutableList().apply {
                remove(taskInQuestion)
                add(taskInQuestion.copy(completed = !taskInQuestion.completed))
            })
        projectsRepository.updateProject(taskIsDone)
    }

    @OptIn(OrbitExperimental::class)
    private fun onNewTaskNameInput(text: String) = blockingIntent {
        reduce { state.copy(newTaskNameInput = if (text.length < 40) text else state.newTaskNameInput) }
    }

    private fun initialize() = intent {
        getProjectById()
    }

    private fun getProjectById() = intent {
        projectsRepository.getProjectById(id).collect { result ->
            when (result) {
                is ResultState.Error -> {
                    Log.d(
                        "Zenais",
                        "TaskViewModel: getProjectById error project message ${result.message}"
                    )
                }

                is ResultState.Loading -> {

                }

                is ResultState.Success -> {
                    result.data?.let {
                        Log.d("Zenais", "TaskViewModel: getProjectById: project $it")
                        reduce {
                            state.copy(project = it)
                        }
                    }
                }
            }
        }
    }

    private fun saveTask() = intent {
        reduce {
            state.copy(
                project = state.project.copy(
                    projectTasks = state.project.projectTasks.toMutableList().apply {
                        add(Task(name = state.newTaskNameInput))
                    }
                )
            )
        }
        projectsRepository.updateProject(state.project)
        dispatch(TaskEvent.OpenAddTaskDialog)
    }


    private fun openAddTaskDialog() = intent {
        reduce {
            state.copy(
                newTaskDialogIsOn = !state.newTaskDialogIsOn,
                newTaskNameInput = ""
            )
        }
    }

    private fun navigateBack() = intent {
        postSideEffect(TaskSideEffect.OnNavigateBack)
    }
}