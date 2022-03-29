package com.simple.mathgame.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.simple.mathgame.data.Level
import com.simple.mathgame.data.repository.LevelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs
import kotlin.random.Random

@HiltViewModel
class OperationViewModel @Inject constructor(private val levelRepository: LevelRepository) : ViewModel() {
    val PROGRESS_IN_EACH_LEVEL = 5
    private val lEquation = MutableLiveData<Equation>()
    val liveEquation : LiveData<Equation?> = lEquation

    fun getLevelForOperator(operator : String) : Level?{
        return levelRepository.getLevelForOperator(operator.toCharArray().get(0).code)
    }

    fun updateLevel(level: Level){
        levelRepository.insertLevel(level.apply {
            this.progress = minOf(PROGRESS_IN_EACH_LEVEL, this.progress + 1)
            if (this.progress == PROGRESS_IN_EACH_LEVEL && this.level < 10) {
                this.level++
                this.progress = 0
            }
        })
    }

    fun generateEquation(operation: String) {
        val level = getLevelForOperator(operation)?.level?:1
        return when (level) {
            1 -> {
                var o1 = Random.nextInt(1, 50)
                var o2 = Random.nextInt(1, 50)
                when (operation) {
                    "-" -> {
                        lEquation.postValue(Equation(intArrayOf(maxOf(o1, o2), minOf(o1, o2)), abs(o1 - o2), operation))
                    }
                    "/" -> {
                        o2 = o1 * Random.nextInt(10)
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 / o1, operation))
                    }
                    "*" -> {
                        o1 = Random.nextInt(1, 10)
                        o2 = Random.nextInt(1, 10)
                        lEquation.postValue(Equation(intArrayOf(o1, o2), o1 * o2, operation))
                    }
                    "+" -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                    else -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                }
            }
            2 -> {
                var o1 = Random.nextInt(1, 50)
                var o2 = Random.nextInt(50, 100)
                when (operation) {
                    "-" -> {
                        lEquation.postValue(Equation(intArrayOf(maxOf(o1, o2), minOf(o1, o2)), abs(o1 - o2), operation))
                    }
                    "/" -> {
                        o2 = o1 * Random.nextInt(10)
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 / o1, operation))
                    }
                    "*" -> {
                        o1 = Random.nextInt(2, 50)
                        o2 = Random.nextInt(2, 10)
                        lEquation.postValue(Equation(intArrayOf(o1, o2), o1 * o2, operation))
                    }
                    "+" -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                    else -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                }
            }
            3 -> {
                var o1 = Random.nextInt(100, 1000)
                var o2 = Random.nextInt(100, 1000)
                when (operation) {
                    "-" -> {
                        lEquation.postValue(Equation(intArrayOf(maxOf(o1, o2), minOf(o1, o2)), abs(o1 - o2), operation))
                    }
                    "/" -> {
                        o2 = o1 * Random.nextInt(50)
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 / o1, operation))
                    }
                    "*" -> {
                        o1 = Random.nextInt(5, 100)
                        o2 = Random.nextInt(5, 20)
                        lEquation.postValue(Equation(intArrayOf(o1, o2), o1 * o2, operation))
                    }
                    "+" -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                    else -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                }
            }
            4 -> {
                var o1 = Random.nextInt(100, 5000)
                var o2 = Random.nextInt(100, 5000)
                when (operation) {
                    "-" -> {
                        lEquation.postValue(Equation(intArrayOf(maxOf(o1, o2), minOf(o1, o2)), abs(o1 - o2), operation))
                    }
                    "/" -> {
                        o2 = o1 * Random.nextInt(50)
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 / o1, operation))
                    }
                    "*" -> {
                        o1 = Random.nextInt(5, 500)
                        o2 = Random.nextInt(5, 99)
                        lEquation.postValue(Equation(intArrayOf(o1, o2), o1 * o2, operation))
                    }
                    "+" -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                    else -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                }
            }
            5 -> {
                var o1 = Random.nextInt(100,10000)
                var o2 = Random.nextInt(100,10000)
                when (operation) {
                    "-" -> {
                        lEquation.postValue(Equation(intArrayOf(maxOf(o1, o2), minOf(o1, o2)), abs(o1 - o2), operation))
                    }
                    "/" -> {
                        o2 = o1 * Random.nextInt(100)
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 / o1, operation))
                    }
                    "*" -> {
                        o1 = Random.nextInt(10, 1000)
                        o2 = Random.nextInt(10, 500)
                        lEquation.postValue(Equation(intArrayOf(o1, o2), o1 * o2, operation))
                    }
                    "+" -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                    else -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                }
            }

            6 -> {
                var o1 = Random.nextInt(100000)
                var o2 = Random.nextInt(100000)
                when (operation) {
                    "-" -> {
                        lEquation.postValue(Equation(intArrayOf(maxOf(o1, o2), minOf(o1, o2)), abs(o1 - o2), operation))
                    }
                    "/" -> {
                        o2 = o1 * Random.nextInt(100)
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 / o1, operation))
                    }
                    "*" -> {
                        o1 = Random.nextInt(10, 1000)
                        o2 = Random.nextInt(10, 500)
                        lEquation.postValue(Equation(intArrayOf(o1, o2), o1 * o2, operation))
                    }
                    "+" -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                    else -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                }
            }

            7 -> {
                var o1 = Random.nextInt(-10000,10000)
                var o2 = Random.nextInt(-10000,10000)
                when (operation) {
                    "-" -> {
                        lEquation.postValue(Equation(intArrayOf(maxOf(o1, o2), minOf(o1, o2)), abs(o1 - o2), operation))
                    }
                    "/" -> {
                        o2 = o1 * Random.nextInt(500)
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 / o1, operation))
                    }
                    "*" -> {
                        o1 = Random.nextInt(100, 1000)
                        o2 = Random.nextInt(100, 500)
                        lEquation.postValue(Equation(intArrayOf(o1, o2), o1 * o2, operation))
                    }
                    "+" -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                    else -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                }
            }

            8 -> {
                var o1 = Random.nextInt(-100000,100000)
                var o2 = Random.nextInt(-10000,10000)
                when (operation) {
                    "-" -> {
                        lEquation.postValue(Equation(intArrayOf(maxOf(o1, o2), minOf(o1, o2)), abs(o1 - o2), operation))
                    }
                    "/" -> {
                        o2 = o1 * Random.nextInt(500)
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 / o1, operation))
                    }
                    "*" -> {
                        o1 = Random.nextInt(10000)
                        o2 = Random.nextInt(500, 1000)
                        lEquation.postValue(Equation(intArrayOf(o1, o2), o1 * o2, operation))
                    }
                    "+" -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                    else -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                }
            }

            9 -> {
                var o1 = Random.nextInt(-100000,100000)
                var o2 = Random.nextInt(-10000,10000)
                when (operation) {
                    "-" -> {
                        lEquation.postValue(Equation(intArrayOf(maxOf(o1, o2), minOf(o1, o2)), abs(o1 - o2), operation))
                    }
                    "/" -> {
                        o2 = o1 * Random.nextInt(1000)
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 / o1, operation))
                    }
                    "*" -> {
                        o1 = Random.nextInt(10000)
                        o2 = Random.nextInt(1000)
                        lEquation.postValue(Equation(intArrayOf(o1, o2), o1 * o2, operation))
                    }
                    "+" -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                    else -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                }
            }

            10 -> {
                var o1 = Random.nextInt(-100000,100000)
                var o2 = Random.nextInt(-10000,10000)
                when (operation) {
                    "-" -> {
                        lEquation.postValue(Equation(intArrayOf(maxOf(o1, o2), minOf(o1, o2)), abs(o1 - o2), operation))
                    }
                    "/" -> {
                        o2 = o1 * Random.nextInt(5000)
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 / o1, operation))
                    }
                    "*" -> {
                        o1 = Random.nextInt(10000)
                        o2 = Random.nextInt(10000)
                        lEquation.postValue(Equation(intArrayOf(o1, o2), o1 * o2, operation))
                    }
                    "+" -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                    else -> {
                        lEquation.postValue(Equation(intArrayOf(o2, o1), o2 + o1, operation))
                    }
                }
            }

            else -> lEquation.postValue(Equation(intArrayOf(1, 2), 2 + 1, operation))
        }
    }
}