package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

data class MatrixCell2(val char: Char, val color: Color = Color.White)

class MatrixInitializerSquare2 {
    private val MatrixLength: Int = 102  // Unique to squares
    private val MatrixHeight: Int = MatrixLength * 42 / 100  // 55% aspect ratio
    private val poolOfCharInnit: Array<Char> = arrayOf(' ', ' ')  // Unique character pool
    private val defaultColor = Color.White

    suspend fun initializeMatrix(
        rows: Int = MatrixHeight,
        cols: Int = MatrixLength
    ): MutableList<MutableList<MatrixCell2>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { MatrixCell2(poolOfCharInnit.random(), defaultColor) }
            }
        }
    }
}