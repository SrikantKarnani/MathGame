package com.simple.mathgame.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LevelDao {
    @Query("SELECT * FROM Level where operator = :operator")
    fun getLevelOfOperator(operator: Int): Level?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLevel(level : Level)
}