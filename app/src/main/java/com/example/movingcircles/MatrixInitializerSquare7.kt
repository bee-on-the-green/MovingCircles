package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare7 {
    val MatrixLengthS7: Int = 47
    val MatrixHeightS7: Int = MatrixLengthS7 * 68/ 100
    val resolution7: Int = MatrixLengthS7 * MatrixHeightS7
    private val squarePoolOfChars: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor: Color = Color.White

    suspend fun initializeMatrix7(): Pair<Array<CharArray>, Array<Array<Color>>> {
        return withContext(Dispatchers.Default) {
            val charMatrix = Array(MatrixHeightS7) {
                CharArray(MatrixLengthS7) { squarePoolOfChars.random() }
            }
            val colorMatrix = Array(MatrixHeightS7) {
                Array(MatrixLengthS7) { defaultColor }
            }
            Pair(charMatrix, colorMatrix)
        }
    }
}