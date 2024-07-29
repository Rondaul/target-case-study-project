package com.target.targetcasestudy.daggermodules

import com.target.targetcasestudy.repo.DealRepository
import com.target.targetcasestudy.repo.DealRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsDealRepository(dealRepositoryImpl: DealRepositoryImpl): DealRepository
}