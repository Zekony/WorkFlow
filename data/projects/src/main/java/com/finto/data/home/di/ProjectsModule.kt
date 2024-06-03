package com.finto.data.home.di

import com.finto.data.home.repositories.ProjectsRepositoryImpl
import com.finto.domain.home.repositories.ProjectsRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object ProjectsModule {

    @Provides
    fun provideProjectsRepositoryImpl(
        databaseReference: DatabaseReference
    ): ProjectsRepository {
        return ProjectsRepositoryImpl(
            database = databaseReference,
            coroutineScope = CoroutineScope(Dispatchers.IO)
        )
    }

    @Provides
    fun provideFirebaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("projects")
    }
}