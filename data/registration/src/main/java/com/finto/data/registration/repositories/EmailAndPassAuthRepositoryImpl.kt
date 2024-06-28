package com.finto.data.registration.repositories

import com.finto.domain.registration.EmailAndPassAuthRepository
import com.finto.utility.classes.ResultState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EmailAndPassAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : EmailAndPassAuthRepository {

    override fun createNewUser(
        email: String,
        password: String,
        userName: String
    ): Flow<ResultState<Boolean>> =
        flow {
            emit(ResultState.Loading())
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseAuth.currentUser?.updateProfile(
                            UserProfileChangeRequest.Builder()
                                .setDisplayName(userName)
                                .build()
                        )
                    }
                }
            emit(ResultState.Success(true))
        }.catch {
            emit(ResultState.Error(it.message))
        }

    override fun login(email: String, password: String): Flow<ResultState<AuthResult>> = flow {
        firebaseAuth.signOut()
        emit(ResultState.Loading())
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        emit(ResultState.Success(result))
    }.catch {
        emit(ResultState.Error(it.message))
    }
}