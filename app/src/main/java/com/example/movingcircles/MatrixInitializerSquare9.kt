package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare9 {
    val MatrixLengthS9: Int = 75
    val MatrixHeightS9: Int = MatrixLengthS9 * 57/ 100
    val resolution9: Int = MatrixLengthS9 * MatrixHeightS9
    private val squarePoolOfChars: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor: Color = Color.White

    suspend fun initializeMatrix9(): Pair<Array<CharArray>, Array<Array<Color>>> {
        return withContext(Dispatchers.Default) {
            val charMatrix = Array(MatrixHeightS9) {
                CharArray(MatrixLengthS9) { squarePoolOfChars.random() }
            }
            val colorMatrix = Array(MatrixHeightS9) {
                Array(MatrixLengthS9) { defaultColor }
            }
            Pair(charMatrix, colorMatrix)
        }
    }
}