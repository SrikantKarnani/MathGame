package com.simple.mathgame.data.repository

import com.simple.mathgame.data.Level

interface LevelRepository {
    fun getLevelForOperator(operator: Int) : Level?

    fun insertLevel(level : Level)
}