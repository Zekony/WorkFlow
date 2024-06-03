package com.finto.data.home.repositories

import android.util.Log
import com.finto.domain.home.entities.Project
import com.finto.domain.home.repositories.ProjectsRepository
import com.finto.utility.classes.ResultState
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
    private val database: DatabaseReference,
    private val coroutineScope: CoroutineScope
) : ProjectsRepository {


    override fun getAllProjectsByUser(userId: Int): Flow<ResultState<Project>> = channelFlow {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                coroutineScope.launch {
                    if (snapshot.exists()) {
                        snapshot.children.forEach {
                            send(
                                ResultState.Success(
                                    it.getValue(Project::class.java) ?: return@launch
                                )
                            )
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                coroutineScope.launch {
                    send(ResultState.Error(error.toException().message))
                }
            }
        })
        awaitClose()
    }

    override fun getProjectById(projectId: String): Flow<ResultState<Project>> = channelFlow {
        database.child(projectId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                coroutineScope.launch {
                    if (snapshot.exists()) {
                        send(
                            ResultState.Success(
                                snapshot.getValue(Project::class.java) ?: return@launch
                            )
                        )

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                coroutineScope.launch {
                    send(ResultState.Error(error.toException().message))
                }
            }
        })
        awaitClose()
    }

    override suspend fun addNewProjectToDatabase(project: Project) {
        val projectId = database.push().key ?: return
        database.child(projectId).setValue(project.copy(id = projectId))
    }

    override suspend fun updateProject(project: Project) {
        database.child(project.id).setValue(project)
    }
}