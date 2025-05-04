package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

data class MatrixCell2(val char: Char, val color: Color = Color.White)

class MatrixInitializerSquare2 {
    // Hardcoded dimensions (now internal for access)
    val MatrixLengthS2: Int = 90
    val MatrixHeightS2: Int = MatrixLengthS2 * 58 / 100
    val resolution2: Int = MatrixLengthS2 * MatrixHeightS2
    private val poolOfCharInnit: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor = Color.White

    suspend fun initializeMatrix(
        rows: Int = MatrixHeightS2,
        cols: Int = MatrixLengthS2
    ): MutableList<MutableList<MatrixCell2>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { MatrixCell2(poolOfCharInnit.random(), defaultColor) }
            }
        }
    }
}