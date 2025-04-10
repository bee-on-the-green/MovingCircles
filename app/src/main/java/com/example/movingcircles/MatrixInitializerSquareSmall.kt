package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquareSmall {
    private val squareSmallMatrixLength: Int = 102  // Unique to squareSmalls
    private val squareSmallMatrixHeight: Int = squareSmallMatrixLength * 42 / 100  // 55% aspect ratio
    private val squareSmallPoolOfChars: Array<Char> = arrayOf('.', '·')  // Unique character pool

    suspend fun initializeMatrix(
        rows: Int = squareSmallMatrixHeight,
        cols: Int = squareSmallMatrixLength
    ): MutableList<MutableList<Char>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { squareSmallPoolOfChars.random() }
            }
        }
    }
}