package com.finto.domain.home.repositories

import com.finto.domain.home.entities.Project
import kotlinx.coroutines.flow.Flow

interface ProjectsRepository {

    fun getAllProjectsByUser(userProjectIds: List<String>): Flow<Result<Project>>

    fun getProjectById(projectId: String): Flow<Result<Project>>

    suspend fun addNewProjectToDatabase(project:Project)

    suspend fun updateProject(project: Project)
}