package com.zekony.data.registration.repositories

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.zekony.domain.registration.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): RegistrationRepository {

    override fun createNewUser(email: String, password: String): Flow<Result<AuthResult>> = flow {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        emit(Result.success(result))
    }.catch {
        emit(Result.failure(it))
    }

    override fun login(email: String, password: String): Flow<Result<AuthResult>> = flow {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        emit(Result.success(result))
    }.catch {
        emit(Result.failure(it))
    }
}