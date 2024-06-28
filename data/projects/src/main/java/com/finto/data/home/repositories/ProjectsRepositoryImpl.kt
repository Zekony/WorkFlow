package com.finto.data.home.repositories

import com.finto.domain.home.entities.Project
import com.finto.domain.home.repositories.ProjectsRepository
import com.finto.domain.home.repositories.UsersRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class ProjectsRepositoryImpl(
    private val projectsDatabase: DatabaseReference,
    private val usersRepository: UsersRepository,
    private val coroutineScope: CoroutineScope
) : ProjectsRepository {

    override fun getAllProjectsByUser(userProjectIds: List<String>): Flow<Result<Project>> =
        channelFlow {
            projectsDatabase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    coroutineScope.launch {
                        if (snapshot.exists()) {
                            userProjectIds.forEach { projectId ->
                                send(
                                    Result.success(
                                        snapshot.child(projectId).getValue(Project::class.java)
                                            ?: return@launch
                                    )
                                )
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    coroutineScope.launch {
                        send(Result.failure(error.toException()))
                    }
                }
            })
            awaitClose()
        }

    override fun getProjectById(projectId: String): Flow<Result<Project>> =
        channelFlow {
            projectsDatabase.child(projectId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    coroutineScope.launch {
                        if (snapshot.exists()) {
                            send(
                                Result.success(snapshot.getValue(Project::class.java) ?: return@launch)
                            )
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    coroutineScope.launch {
                        send(Result.failure(error.toException()))
                    }
                }
            })
            awaitClose()
        }

    override suspend fun addNewProjectToDatabase(project: Project) {
        val projectId = projectsDatabase.push().key ?: return
        projectsDatabase.child(projectId).setValue(project.copy(id = projectId))
        val listOfUsers = mutableListOf(project.projectCreator).apply { addAll(project.projectMembers) }
        listOfUsers.forEach {
           usersRepository.updateUserProjectsIds(projectId, it)
        }
    }

    override suspend fun updateProject(project: Project) {
        projectsDatabase.child(project.id).setValue(project)

        project.projectMembers.forEach {
            usersRepository.updateUserProjectsIds(project.id, it)
        }
    }
}