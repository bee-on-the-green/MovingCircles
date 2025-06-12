package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare13 {
    val MatrixLengthS13: Int = 40
    val MatrixHeightS13: Int = MatrixLengthS13 * 500/ 100// was 90
    val resolution13: Int = MatrixLengthS13 * MatrixHeightS13
    private val squarePoolOfChars: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor: Color = Color.White

    suspend fun initializeMatrix13(): Pair<Array<CharArray>, Array<Array<Color>>> {
        return withContext(Dispatchers.Default) {
            val charMatrix = Array(MatrixHeightS13) {
                CharArray(MatrixLengthS13) { squarePoolOfChars.random() }
            }
            val colorMatrix = Array(MatrixHeightS13) {
                Array(MatrixLengthS13) { defaultColor }
            }
            Pair(charMatrix, colorMatrix)
        }
    }
}
