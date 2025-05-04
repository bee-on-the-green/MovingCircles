package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare {
    // Made internal (or remove modifier entirely for default visibility)
    val MatrixLengthS: Int = 80
    val MatrixHeightS: Int = MatrixLengthS * 42 / 100
    val resolutionS = MatrixLengthS * MatrixHeightS
    private val squarePoolOfChars: Array<Char> = arrayOf('.', '·')

    suspend fun initializeMatrix(
        rows: Int = MatrixHeightS,
        cols: Int = MatrixLengthS
    ): MutableList<MutableList<Char>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { squarePoolOfChars.random() }
            }
        }
    }
}