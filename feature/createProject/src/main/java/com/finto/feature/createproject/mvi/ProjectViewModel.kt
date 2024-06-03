package com.finto.feature.createproject.mvi

import com.finto.domain.home.entities.User
import com.finto.domain.home.repositories.ProjectsRepository
import com.finto.utility.MviViewModel
import com.finto.utility.classes.ResultState
import com.finto.utility.functions.setTimeLogic
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

@HiltViewModel(assistedFactory = ProjectViewModel.ProjectViewModelFactory::class)
class ProjectViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val projectsRepository: ProjectsRepository
) : MviViewModel<ProjectState, ProjectSideEffect, ProjectEvent>(ProjectState()) {

    @AssistedFactory
    interface ProjectViewModelFactory {
        fun create(id: String): ProjectViewModel
    }

    init {
        dispatch(ProjectEvent.Initialize)
    }

    override fun dispatch(event: ProjectEvent) {
        when (event) {
            ProjectEvent.Initialize -> initialize()
            ProjectEvent.OnNavigateBack -> onNavigateBack()

            is ProjectEvent.OnTitleInput -> onTitleInput(event.text)
            is ProjectEvent.OnDescriptionInput -> onDescriptionInput(event.text)
            ProjectEvent.OpenAddUserDialog -> openAddUserDialog()
            is ProjectEvent.AddUserToChosen -> addUserToChosen(event.id)
            ProjectEvent.SaveChosenUsers -> saveChosenUsers()
            is ProjectEvent.RemoveTeamMember -> removeTeamMember(event.id)

            ProjectEvent.ShowTimePickerDialog -> showTimePickerDialog()
            ProjectEvent.ShowDatePickerDialog -> showDatePickerDialog()

            is ProjectEvent.PickerTime -> pickMinutes(event.time)
            is ProjectEvent.PickerDate -> pickDate(event.date)

            ProjectEvent.CreateProject -> createProject()
        }
    }

    private fun createProject() = intent {
        if (id.isNotEmpty()) {
            projectsRepository.updateProject(
                state.currentProject
            )
        } else {
            projectsRepository.addNewProjectToDatabase(
                state.currentProject

            )
        }
        resetState()
        postSideEffect(ProjectSideEffect.NavigateBack)
    }

    private fun resetState() = intent {
        reduce {
            with(ProjectState()) {
                state.copy(
                    possibleUsersList = emptyList(),
                    chosenUsersIdList = emptyList()
                )
            }
        }
    }

    private fun pickMinutes(minutes: Int) = intent {
        reduce {
            state.copy(
                currentProject = state.currentProject.copy(
                    dueDateEpochSeconds = setTimeLogic(
                        state.currentProject.dueDateEpochSeconds,
                        minutes.toLong()
                    )
                )
            )
        }
    }

    private fun pickDate(date: Long) = intent {
        if (date != 0L) {
            reduce {
                state.copy(
                    currentProject = state.currentProject.copy(
                        dueDateEpochSeconds = setTimeLogic(
                            state.currentProject.dueDateEpochSeconds,
                            date / 1000
                        )
                    )
                )
            }
        }
    }

    private fun showDatePickerDialog() = intent {
        reduce { state.copy(showDatePickerDialog = !state.showDatePickerDialog) }
    }

    private fun showTimePickerDialog() = intent {
        reduce { state.copy(showTimePickerDialog = !state.showTimePickerDialog) }
    }

    private fun initialize() = intent {
        val allUsers = listOf(            // TODO() initialize possible users list
            User(name = "Stephanie"),
            User(name = "Pixel"),
            User(name = "Stingy"),
            User(name = "Robbie"),
            User(name = "Sportacus")
        ).sortedBy { it.name }
        if (id.isNotEmpty()) {
            projectsRepository.getProjectById(id).collect { result ->
                when (result) {
                    is ResultState.Error -> TODO()
                    is ResultState.Loading -> TODO()
                    is ResultState.Success -> {
                        result.data?.let { project ->
                            reduce {
                                state.copy(
                                    currentProject = project,
                                    usersList = allUsers,
                                    possibleUsersList = allUsers.filter {
                                        project.projectMembers.contains(it)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        } else {
            reduce {
                state.copy(
                    usersList = allUsers,
                    possibleUsersList = allUsers,
                )
            }
        }
    }

    private fun removeTeamMember(id: String) = intent {
        val user = state.currentProject.projectMembers.first { it.id == id }
        reduce {
            state.copy(
                currentProject = state.currentProject.copy(
                    projectMembers = state.currentProject.projectMembers.toMutableList()
                        .apply { remove(user) },
                ),
                possibleUsersList = state.possibleUsersList.toMutableList().apply { add(user) }
                    .sortedBy { it.name }
            )
        }
    }

    private fun saveChosenUsers() = intent {
        reduce {
            state.copy(
                currentProject = state.currentProject.copy(
                    projectMembers = state.currentProject.projectMembers.toMutableList().apply {
                        addAll(state.usersList.filter { state.chosenUsersIdList.contains(it.id) })
                    }
                ),
                possibleUsersList = state.possibleUsersList.filterNot {
                    state.chosenUsersIdList.contains(it.id)
                },
                showAddUserDialog = false,
                chosenUsersIdList = emptyList()
            )
        }
    }

    private fun addUserToChosen(id: String) = intent {
        reduce {
            state.copy(
                chosenUsersIdList = state.chosenUsersIdList.toMutableList()
                    .apply { if (state.chosenUsersIdList.contains(id)) remove(id) else add(id) })
        }
    }

    private fun openAddUserDialog() = intent {
        reduce { state.copy(showAddUserDialog = !state.showAddUserDialog) }
    }

    @OptIn(OrbitExperimental::class)
    private fun onDescriptionInput(text: String) = blockingIntent {
        reduce { state.copy(currentProject = state.currentProject.copy(description = text.trimStart())) }
    }

    @OptIn(OrbitExperimental::class)
    private fun onTitleInput(text: String) = blockingIntent {
        reduce { state.copy(currentProject = state.currentProject.copy(title = if (text.length < 35) text.trimStart() else state.currentProject.title)) }
    }

    private fun onNavigateBack() = intent {
        postSideEffect(ProjectSideEffect.NavigateBack)
    }
}