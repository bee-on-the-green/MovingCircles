package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare5 {
    val MatrixLengthS5: Int = 42
    val MatrixHeightS5: Int = MatrixLengthS5 * 72 / 100
    val resolution5: Int = MatrixLengthS5 * MatrixHeightS5
    private val squarePoolOfChars: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor: Color = Color.White

    suspend fun initializeMatrix5(): Pair<Array<CharArray>, Array<Array<Color>>> {
        return withContext(Dispatchers.Default) {
            val charMatrix = Array(MatrixHeightS5) {
                CharArray(MatrixLengthS5) { squarePoolOfChars.random() }
            }
            val colorMatrix = Array(MatrixHeightS5) {
                Array(MatrixLengthS5) { defaultColor }
            }
            Pair(charMatrix, colorMatrix)
        }
    }
}