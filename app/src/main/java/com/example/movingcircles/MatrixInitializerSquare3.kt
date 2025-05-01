package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare3 {
    // Made internal for access from MatrixUpdaterSquare3
    internal val MatrixLength: Int = 130
    internal val MatrixHeight: Int = MatrixLength * 33 / 100
    private val squarePoolOfChars: Array<Char> = arrayOf(' ', ' ')

    suspend fun initializeMatrix3(
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