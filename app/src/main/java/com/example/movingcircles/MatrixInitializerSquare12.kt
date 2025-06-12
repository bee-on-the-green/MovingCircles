package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare12 {
    val MatrixLengthS12: Int = 26  // 137 is real pixel heigth
    val MatrixHeightS12: Int = MatrixLengthS12 * 530/ 100  //510 excellent
    val resolution12: Int = MatrixLengthS12 * MatrixHeightS12
    private val squarePoolOfChars: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor: Color = Color.White

    suspend fun initializeMatrix12(): Pair<Array<CharArray>, Array<Array<Color>>> {
        return withContext(Dispatchers.Default) {
            val charMatrix = Array(MatrixHeightS12) {
                CharArray(MatrixLengthS12) { squarePoolOfChars.random() }
            }
            val colorMatrix = Array(MatrixHeightS12) {
                Array(MatrixLengthS12) { defaultColor }
            }
            Pair(charMatrix, colorMatrix)
        }
    }
}
