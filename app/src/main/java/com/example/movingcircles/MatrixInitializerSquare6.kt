package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare6 {
    val MatrixLengthS6: Int = 42
    val MatrixHeightS6: Int = MatrixLengthS6 * 72 / 100
    val resolution6: Int = MatrixLengthS6 * MatrixHeightS6
    private val squarePoolOfChars: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor: Color = Color.White

    suspend fun initializeMatrix6(): Pair<Array<CharArray>, Array<Array<Color>>> {
        return withContext(Dispatchers.Default) {
            val charMatrix = Array(MatrixHeightS6) {
                CharArray(MatrixLengthS6) { squarePoolOfChars.random() }
            }
            val colorMatrix = Array(MatrixHeightS6) {
                Array(MatrixLengthS6) { defaultColor }
            }
            Pair(charMatrix, colorMatrix)
        }
    }
}