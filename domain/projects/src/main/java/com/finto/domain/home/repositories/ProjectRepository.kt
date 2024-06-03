package com.finto.domain.home.repositories

import com.finto.domain.home.entities.Project
import kotlinx.coroutines.flow.Flow
import com.finto.utility.classes.ResultState

interface ProjectsRepository {

    fun getAllProjectsByUser(userId: Int): Flow<ResultState<Project>>

    fun getProjectById(projectId: String): Flow<ResultState<Project>>

    suspend fun addNewProjectToDatabase(project:Project)

    suspend fun updateProject(project: Project)
}