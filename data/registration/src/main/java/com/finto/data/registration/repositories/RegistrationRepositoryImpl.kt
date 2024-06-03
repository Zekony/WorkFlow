package com.finto.data.registration.repositories

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.finto.domain.registration.RegistrationRepository
import com.finto.utility.classes.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : RegistrationRepository {

    override fun createNewUser(email: String, password: String): Flow<ResultState<AuthResult>> =
        flow {
            emit(ResultState.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).result
            if (result != null) emit(ResultState.Success(result)) else emit(ResultState.Error(result))
        }.catch {
            emit(ResultState.Error(it.message))
        }

    override fun login(email: String, password: String): Flow<ResultState<AuthResult>> = flow {
        emit(ResultState.Loading())
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).result
        if (result != null) emit(ResultState.Success(result)) else emit(ResultState.Error(result))
    }.catch {
        emit(ResultState.Error(it.message))
    }
}