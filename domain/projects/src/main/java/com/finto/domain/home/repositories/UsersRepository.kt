package com.finto.domain.home.repositories

import com.finto.domain.home.entities.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun addNewUserToDB(user: User)
     fun getUsersProjectIds(user: User): Flow<Result<List<String>>>

     fun getUsersList(): Flow<Result<User>>

    fun updateUserProjectsIds(projectId: String, user: User)

}