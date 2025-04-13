package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare2 {
    private val MatrixLength: Int = 102  // Unique to squares
    private val MatrixHeight: Int = MatrixLength * 42 / 100  // 55% aspect ratio
    private val poolOfCharInnit: Array<Char> = arrayOf('.', '·')  // Unique character pool


    suspend fun initializeMatrix(
        rows: Int = MatrixHeight,
        cols: Int = MatrixLength
    ): MutableList<MutableList<Char>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { poolOfCharInnit.random() }
            }
        }
    }
}

