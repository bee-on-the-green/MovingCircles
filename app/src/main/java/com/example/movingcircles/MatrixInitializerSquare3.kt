package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare3 {
    private val squareMatrixLength: Int = 80  // Unique to squares
    private val squareMatrixHeight: Int = squareMatrixLength * 42 / 100  // 55% aspect ratio
    private val squarePoolOfChars: Array<Char> = arrayOf('.', '·')  // Unique character pool

    suspend fun initializeMatrix3(
        rows: Int = squareMatrixHeight,
        cols: Int = squareMatrixLength
    ): MutableList<MutableList<Char>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { squarePoolOfChars.random() }
            }
        }
    }
}