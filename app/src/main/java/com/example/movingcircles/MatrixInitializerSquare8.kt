package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare8 {
    val MatrixLengthS8: Int = 72
    val MatrixHeightS8: Int = MatrixLengthS8 * 72/ 100
    val resolution8: Int = MatrixLengthS8 * MatrixHeightS8
    private val squarePoolOfChars: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor: Color = Color.White

    suspend fun initializeMatrix8(): Pair<Array<CharArray>, Array<Array<Color>>> {
        return withContext(Dispatchers.Default) {
            val charMatrix = Array(MatrixHeightS8) {
                CharArray(MatrixLengthS8) { squarePoolOfChars.random() }
            }
            val colorMatrix = Array(MatrixHeightS8) {
                Array(MatrixLengthS8) { defaultColor }
            }
            Pair(charMatrix, colorMatrix)
        }
    }
}