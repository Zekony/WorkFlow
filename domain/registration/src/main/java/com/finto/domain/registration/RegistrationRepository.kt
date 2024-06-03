package com.finto.domain.registration

import com.finto.utility.classes.ResultState
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {

    fun createNewUser(email: String, password: String): Flow<ResultState<AuthResult>>

    fun login(email: String, password: String): Flow<ResultState<AuthResult>>
}