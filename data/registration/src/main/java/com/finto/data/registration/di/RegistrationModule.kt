package com.finto.data.registration.di

import com.finto.data.registration.repositories.RegistrationRepositoryImpl
import com.finto.domain.registration.RegistrationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RegistrationModule {

    @Binds
    abstract fun provideRegistrationRepository(repo: RegistrationRepositoryImpl): RegistrationRepository
}