package com.zekony.data.registration.di

import com.zekony.data.registration.repositories.RegistrationRepositoryImpl
import com.zekony.domain.registration.RegistrationRepository
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