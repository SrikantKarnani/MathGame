package com.simple.mathgame

import kotlin.math.abs
import kotlin.random.Random

fun Random.nextIntInRange(a: Int, b: Int): Int {
    return Random.nextInt(minOf(a, b), maxOf(a, b))
}

fun Double.sign(): Double {
    return if (this >= 0.0) return 1.0 else -1.0
}

fun absMaxOf(a: Double, b: Double): Double {
    return if (abs(a) > abs(b)) a else b
}