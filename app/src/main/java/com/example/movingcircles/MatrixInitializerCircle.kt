package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


// Add this to any of your existing files or create a new file
data class MatrixCell(val char: Char, val color: Color = Color.White)

class MatrixInitializerCircle {
    val MatrixLengthC: Int = 102 // it is 100
    val MatrixHeightC: Int = MatrixLengthC * 44 / 100  // it is 41
    val resolutionC: Int = MatrixLengthC * MatrixHeightC


    private val poolOfCharsInit: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor = Color.White

    suspend fun initializeMatrix(
        rows: Int = MatrixHeightC,
        cols: Int = MatrixLengthC
    ): MutableList<MutableList<MatrixCell>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { MatrixCell(poolOfCharsInit.random(), defaultColor) }
            }
        }
    }
}