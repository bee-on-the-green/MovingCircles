package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare4 {
    val MatrixLengthS4: Int = 102
    val MatrixHeightS4: Int = MatrixLengthS4 * 53 / 100
    val resolution4: Int = MatrixLengthS4 * MatrixHeightS4
    private val squarePoolOfChars: Array<Char> = arrayOf(' ', ' ')

    suspend fun initializeMatrix4(
        rows: Int = MatrixHeightS4,
        cols: Int = MatrixLengthS4
    ): MutableList<MutableList<Char>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { squarePoolOfChars.random() }
            }
        }
    }
}