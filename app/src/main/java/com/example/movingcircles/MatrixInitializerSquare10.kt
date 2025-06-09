package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare10 {
    val MatrixLengthS10: Int = 90
    val MatrixHeightS10: Int = MatrixLengthS10 * 73/ 100
    val resolution10: Int = MatrixLengthS10 * MatrixHeightS10
    private val squarePoolOfChars: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor: Color = Color.White

    suspend fun initializeMatrix10(): Pair<Array<CharArray>, Array<Array<Color>>> {
        return withContext(Dispatchers.Default) {
            val charMatrix = Array(MatrixHeightS10) {
                CharArray(MatrixLengthS10) { squarePoolOfChars.random() }
            }
            val colorMatrix = Array(MatrixHeightS10) {
                Array(MatrixLengthS10) { defaultColor }
            }
            Pair(charMatrix, colorMatrix)
        }
    }
}