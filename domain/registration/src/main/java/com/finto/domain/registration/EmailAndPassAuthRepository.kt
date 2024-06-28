package com.finto.domain.registration

import com.finto.utility.classes.ResultState
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface EmailAndPassAuthRepository {

    fun createNewUser(email: String, password: String, userName: String): Flow<ResultState<Boolean>>

    fun login(email: String, password: String): Flow<ResultState<AuthResult>>
}