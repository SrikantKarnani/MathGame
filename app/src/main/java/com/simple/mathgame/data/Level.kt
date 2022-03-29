package com.simple.mathgame.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Keep
data class Level(@PrimaryKey val operator: Int, var level: Int, var progress : Int)