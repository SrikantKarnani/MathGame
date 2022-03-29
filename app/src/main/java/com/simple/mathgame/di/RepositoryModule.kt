package com.simple.mathgame.di

import com.simple.mathgame.data.repository.LevelRepository
import com.simple.mathgame.operations.repository.LevelRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindLevelRepository(levelRepository: LevelRepositoryImpl): LevelRepository
}