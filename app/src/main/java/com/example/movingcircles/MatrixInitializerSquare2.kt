package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare2 {
    val MatrixLengthS2: Int = 53
    val MatrixHeightS2: Int = MatrixLengthS2 * 94 / 100
    val resolution2: Int = MatrixLengthS2 * MatrixHeightS2
    private val poolOfCharInnit: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor: Color = Color.White

    suspend fun initializeMatrix2(): Pair<Array<CharArray>, Array<Array<Color>>> {
        return withContext(Dispatchers.Default) {
            val charMatrix = Array(MatrixHeightS2) {
                CharArray(MatrixLengthS2) { poolOfCharInnit.random() }
            }
            val colorMatrix = Array(MatrixHeightS2) {
                Array(MatrixLengthS2) { defaultColor }
            }
            Pair(charMatrix, colorMatrix)
        }
    }
}