package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

data class MatrixCell2(val char: Char, val color: Color = Color.White)

class MatrixInitializerSquare2 {
    // Hardcoded dimensions (now internal for access)
    internal val MatrixLength: Int = 90
    internal val MatrixHeight: Int = MatrixLength * 58 / 100
    private val poolOfCharInnit: Array<Char> = arrayOf(' ', ' ')
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