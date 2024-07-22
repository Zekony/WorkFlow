package com.finto.data.registration.repositories

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.finto.domain.home.entities.User
import com.finto.domain.registration.GoogleAuthUiClient
import com.finto.resources.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class GoogleAuthUiClientImpl @Inject constructor(
    context: Context,
    private val firebaseAuth: FirebaseAuth
) : GoogleAuthUiClient {


    private val credentialManager = CredentialManager.create(context)

    override suspend fun signIn(activityContext: Context): Result<Boolean> {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.SERVER_CLIENT_ID)
            .setNonce(UUID.randomUUID().toString())
            .setAutoSelectEnabled(true)
            .build()

        val request = GetCredentialRequest(credentialOptions = listOf(googleIdOption))

        return try {
            val result = credentialManager.getCredential(
                context = activityContext,
                request = request
            )
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
            val firebaseCredential =
                GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)

            firebaseAuth.signInWithCredential(firebaseCredential)

            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): Flow<User?> = flow {
        var fbUser = firebaseAuth.currentUser
        var retriesToGetUser = 0
        while (fbUser == null && retriesToGetUser < 3) {
            delay(300)
            retriesToGetUser += 1
            fbUser = firebaseAuth.currentUser
        }

        if (fbUser == null) {
            emit(null)
        } else {
            emit(
                User(
                    id = fbUser.uid,
                    name = fbUser.displayName ?: ""
                )
            )
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
    }
}