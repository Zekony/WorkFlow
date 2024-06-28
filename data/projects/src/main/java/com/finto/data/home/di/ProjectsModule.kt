package com.finto.data.home.di

import com.finto.data.home.repositories.ProjectsRepositoryImpl
import com.finto.data.home.repositories.UsersRepositoryImpl
import com.finto.domain.home.repositories.ProjectsRepository
import com.finto.domain.home.repositories.UsersRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProjectsModule {

    @Provides
    @Singleton
    fun provideProjectsRepositoryImpl(
        @FirebaseDatabaseReference(FirebaseDatabaseReferences.Projects) projectsDatabaseReference: DatabaseReference,
        usersRepository: UsersRepository
    ): ProjectsRepository {
        return ProjectsRepositoryImpl(
            projectsDatabase = projectsDatabaseReference,
            usersRepository = usersRepository,
            coroutineScope = CoroutineScope(Dispatchers.IO)
        )
    }

    @Provides
    @Singleton
    fun provideUsersRepositoryImpl(
        @FirebaseDatabaseReference(FirebaseDatabaseReferences.Users) usersDatabaseReference: DatabaseReference,
    ): UsersRepository {
        return UsersRepositoryImpl(
            usersDatabase = usersDatabaseReference,
            coroutineScope = CoroutineScope(Dispatchers.IO)
        )
    }

    @Provides
    @Singleton
    @FirebaseDatabaseReference(FirebaseDatabaseReferences.Projects)
    fun provideFirebaseProjectsReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference(FirebaseDatabaseReferences.Projects.referenceName)
    }
    @Provides
    @Singleton
    @FirebaseDatabaseReference(FirebaseDatabaseReferences.Users)
    fun provideFirebaseUsersReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference(FirebaseDatabaseReferences.Users.referenceName)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FirebaseDatabaseReference(val value: FirebaseDatabaseReferences)

enum class FirebaseDatabaseReferences(val referenceName: String) {
    Users("users"),
    Projects("projects"),
}
