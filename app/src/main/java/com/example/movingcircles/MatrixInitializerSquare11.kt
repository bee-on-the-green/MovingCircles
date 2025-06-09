package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare11 {
    val MatrixLengthS11: Int = 47
    val MatrixHeightS11: Int = MatrixLengthS11 * 55/ 100
    val resolution11: Int = MatrixLengthS11 * MatrixHeightS11
    private val squarePoolOfChars: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor: Color = Color.White

    suspend fun initializeMatrix11(): Pair<Array<CharArray>, Array<Array<Color>>> {
        return withContext(Dispatchers.Default) {
            val charMatrix = Array(MatrixHeightS11) {
                CharArray(MatrixLengthS11) { squarePoolOfChars.random() }
            }
            val colorMatrix = Array(MatrixHeightS11) {
                Array(MatrixLengthS11) { defaultColor }
            }
            Pair(charMatrix, colorMatrix)
        }
    }
}
