package com.finto.feature.createproject.mvi

import com.finto.domain.home.repositories.ProjectsRepository
import com.finto.domain.home.repositories.UsersRepository
import com.finto.domain.registration.GoogleAuthUiClient
import com.finto.utility.MviViewModel
import com.finto.utility.functions.addToList
import com.finto.utility.functions.removeFromList
import com.finto.utility.functions.setTimeLogic
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

@HiltViewModel(assistedFactory = ProjectViewModel.ProjectViewModelFactory::class)
class ProjectViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val projectsRepository: ProjectsRepository,
    private val usersRepository: UsersRepository,
    private val googleAuthUiClient: GoogleAuthUiClient
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

    private fun initialize() = intent {
        getCurrentUser()
        fetchUsersList()
        if (id.isNotEmpty()) getProject()
    }

    private fun createProject() = intent {
        if (id.isNotEmpty()) {
            projectsRepository.updateProject(
                state.currentProject
            )
        } else {
            projectsRepository.addNewProjectToDatabase(
                state.currentProject.copy(
                    projectCreator = state.currentUser
                )
            )
        }
        resetState()
        postSideEffect(ProjectSideEffect.NavigateBack)
    }

    private fun resetState() = intent {
        reduce {
            state.copy(
                possibleUsersList = emptyList(),
                chosenUsersIdList = emptyList()
            )
        }
    }

    private fun getProject() = intent {
        projectsRepository.getProjectById(id).collect { result ->
            if (result.isSuccess) {
                result.getOrNull()?.let { project ->
                    reduce {
                        state.copy(
                            currentProject = project
                        )
                    }
                }
            } else {
                postSideEffect(ProjectSideEffect.ShowErrorMessage(result.exceptionOrNull()?.message ?: ""))
            }
        }
    }

    private fun fetchUsersList() = intent {
        usersRepository.getUsersList().collect { result ->
            if (result.isSuccess) {
                result.getOrNull()?.let { user ->
                    reduce {
                        state.copy(
                            usersList = state.usersList.addToList(
                                user,
                                condition = user.id != state.currentUser.id
                            ),
                            possibleUsersList = state.possibleUsersList.addToList(
                                user,
                                condition = !state.currentProject.projectMembers.any { it.id == user.id } && user.id != state.currentUser.id
                            )
                        )
                    }
                }
            }
        }
    }


    private fun getCurrentUser() = intent {
        val firebaseUser = googleAuthUiClient.getCurrentUser().firstOrNull()
        firebaseUser?.let { user ->
            reduce {
                state.copy(
                    currentUser = user
                )
            }
        }
    }

    private fun removeTeamMember(id: String) = intent {
        val user = state.currentProject.projectMembers.first { it.id == id }
        reduce {
            state.copy(
                currentProject = state.currentProject.copy(
                    projectMembers = state.currentProject.projectMembers.removeFromList(user),
                ),
                possibleUsersList = state.possibleUsersList.addToList(user).sortedBy { it.name }
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

    private fun pickMinutes(minutes: Int) = intent {
        reduce {
            state.copy(
                currentProject = state.currentProject.copy(
                    dueDateEpochSeconds = setTimeLogic(
                        state.currentProject.dueDateEpochSeconds,
                        minutes.toLong()
                    )
                ),
                showTimePickerDialog = false
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
                    ),
                    showDatePickerDialog = false
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

