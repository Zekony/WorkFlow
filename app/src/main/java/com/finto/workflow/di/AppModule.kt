package com.finto.workflow.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideFirebaseInstance(@ApplicationContext context: Context): FirebaseApp? {

        return FirebaseApp.initializeApp(context)
    }

    @Provides
    fun provideFirebaseAuth(firebaseApp: FirebaseApp?): FirebaseAuth {
        return if (firebaseApp != null) FirebaseAuth.getInstance(firebaseApp) else FirebaseAuth.getInstance()
    }
}