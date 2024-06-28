package com.finto.data.registration.di

import android.content.Context
import com.finto.data.registration.repositories.EmailAndPassAuthRepositoryImpl
import com.finto.data.registration.repositories.GoogleAuthUiClientImpl
import com.finto.domain.registration.EmailAndPassAuthRepository
import com.finto.domain.registration.GoogleAuthUiClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RegistrationModule {

    @Provides
    fun provideEmailAndPassAuthRepository(firebaseAuth: FirebaseAuth): EmailAndPassAuthRepository {
        return EmailAndPassAuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    fun provideGoogleAuthUiClient(
        @ApplicationContext context: Context,
        firebaseAuth: FirebaseAuth
    ): GoogleAuthUiClient {
        return GoogleAuthUiClientImpl(context, firebaseAuth)
    }
}




