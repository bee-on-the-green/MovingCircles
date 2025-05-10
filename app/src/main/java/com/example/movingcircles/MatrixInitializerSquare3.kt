package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare3 {
    val MatrixLengthS3: Int = 130
    val MatrixHeightS3: Int = MatrixLengthS3 * 33 / 100
    val resolution3: Int = MatrixLengthS3 * MatrixHeightS3
    private val squarePoolOfChars: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor: Color = Color.White

    suspend fun initializeMatrix3(): Pair<Array<CharArray>, Array<Array<Color>>> {
        return withContext(Dispatchers.Default) {
            val charMatrix = Array(MatrixHeightS3) {
                CharArray(MatrixLengthS3) { squarePoolOfChars.random() }
            }
            val colorMatrix = Array(MatrixHeightS3) {
                Array(MatrixLengthS3) { defaultColor }
            }
            Pair(charMatrix, colorMatrix)
        }
    }
}