package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare4 {
    val MatrixLengthS4: Int = 42
    val MatrixHeightS4: Int = MatrixLengthS4 * 72 / 100
    val resolution4: Int = MatrixLengthS4 * MatrixHeightS4
    private val squarePoolOfChars: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor: Color = Color.White

    suspend fun initializeMatrix4(): Pair<Array<CharArray>, Array<Array<Color>>> {
        return withContext(Dispatchers.Default) {
            val charMatrix = Array(MatrixHeightS4) {
                CharArray(MatrixLengthS4) { squarePoolOfChars.random() }
            }
            val colorMatrix = Array(MatrixHeightS4) {
                Array(MatrixLengthS4) { defaultColor }
            }
            Pair(charMatrix, colorMatrix)
        }
    }
}