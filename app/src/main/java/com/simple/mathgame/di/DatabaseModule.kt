package com.simple.mathgame.di

import android.content.Context
import androidx.room.Room
import com.simple.mathgame.data.AppDatabase
import com.simple.mathgame.data.LevelDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): LevelDao {
        return appDatabase.levelDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "MathGameDb"
        ).allowMainThreadQueries().build()
    }
}