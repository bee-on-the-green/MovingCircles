package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MatrixInitializerCircle {
    private val MatrixLength: Int = 100  // Unique to squareSmalls
    private val MatrixHeight: Int = MatrixLength * 42 / 100  // 55% aspect ratio
    private val poolOfCharsInit: Array<Char> = arrayOf(' ', ' ')  // Unique character pool ·




    suspend fun initializeMatrix(
        rows: Int = MatrixHeight,
        cols: Int = MatrixLength
    ): MutableList<MutableList<Char>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { poolOfCharsInit.random() }
            }
        }
    }
}



