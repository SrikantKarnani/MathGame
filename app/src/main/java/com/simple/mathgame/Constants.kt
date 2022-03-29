package com.simple.mathgame

import com.simple.mathgame.R

const val AdditionOperation = "Addition"
const val SubtractionOperation = "Subtraction"
const val MultiplicationOperation = "Multiplication"
const val DivisionOperation = "Division"

fun getSignFromOperation(operation: String): String {
    return when (operation) {
        AdditionOperation -> "+"
        SubtractionOperation -> "-"
        MultiplicationOperation -> "*"
        else -> "/"
    }
}

fun getImageRes(operation: String): Int {
    return when (operation) {
        "+" -> R.drawable.ic_addition
        "-" -> R.drawable.ic_subtract
        "*" -> R.drawable.ic_multiply
        else -> R.drawable.ic_divide
    }
}