package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare {
    // Made internal (or remove modifier entirely for default visibility)
    val MatrixLengthS: Int = 88  // was 48
    val MatrixHeightS: Int = MatrixLengthS * 62 / 100  // was 66
      val resolutionS = MatrixLengthS * MatrixHeightS
    private val squarePoolOfChars: Array<Char> = arrayOf('(', ')')

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