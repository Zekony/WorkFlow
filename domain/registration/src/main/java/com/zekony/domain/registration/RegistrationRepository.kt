package com.zekony.domain.registration

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {

    fun createNewUser(email: String, password: String): Flow<Result<AuthResult>>

    fun login(email: String, password: String): Flow<Result<AuthResult>>
}