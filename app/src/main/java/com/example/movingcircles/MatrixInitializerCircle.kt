package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


// Add this to any of your existing files or create a new file
data class MatrixCell(val char: Char, val color: Color = Color.White)

class MatrixInitializerCircle {
    private val MatrixLength: Int = 100
    private val MatrixHeight: Int = MatrixLength * 42 / 100
    private val poolOfCharsInit: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor = Color.White

    suspend fun initializeMatrix(
        rows: Int = MatrixHeight,
        cols: Int = MatrixLength
    ): MutableList<MutableList<MatrixCell>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { MatrixCell(poolOfCharsInit.random(), defaultColor) }
            }
        }
    }
}