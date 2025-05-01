package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare {
    // Made internal (or remove modifier entirely for default visibility)
    internal val MatrixLength: Int = 80
    internal val MatrixHeight: Int = MatrixLength * 42 / 100
    private val squarePoolOfChars: Array<Char> = arrayOf('.', '·')

    suspend fun initializeMatrix(
        rows: Int = MatrixHeight,
        cols: Int = MatrixLength
    ): MutableList<MutableList<Char>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { squarePoolOfChars.random() }
            }
        }
    }
}