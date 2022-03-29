package com.simple.mathgame.operations.repository

import com.simple.mathgame.data.Level
import com.simple.mathgame.data.LevelDao
import com.simple.mathgame.data.repository.LevelRepository
import javax.inject.Inject

class LevelRepositoryImpl @Inject constructor(private val levelDao: LevelDao) : LevelRepository {
    override fun getLevelForOperator(operator: Int) : Level? {
       return levelDao.getLevelOfOperator(operator)
    }

    override fun insertLevel(level : Level) {
        levelDao.insertLevel(level)
    }
}