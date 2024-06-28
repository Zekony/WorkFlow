package com.finto.domain.registration

import android.content.Context
import com.finto.domain.home.entities.User
import kotlinx.coroutines.flow.Flow

interface GoogleAuthUiClient {

    suspend fun signIn(activityContext: Context): Result<Boolean>

    suspend fun signOut()

    suspend fun getCurrentUser(): Flow<User?>
}