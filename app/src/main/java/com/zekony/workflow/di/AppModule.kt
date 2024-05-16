package com.zekony.workflow.di

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Provides
import dagger.Module
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
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth
}