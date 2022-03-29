package com.simple.mathgame.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Level::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun levelDao(): LevelDao
}