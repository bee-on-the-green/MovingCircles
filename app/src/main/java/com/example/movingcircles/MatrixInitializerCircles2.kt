package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MatrixInitializerCircles2 {
    val MatrixLengthC2: Int = 54
    val MatrixHeightC2: Int = MatrixLengthC2 * 66 / 100  // was 42
    val resolution: Int = MatrixLengthC2 * MatrixHeightC2
    val diameterToUseC2: Int = 4

    val resolutionC: Int = MatrixLengthC2 * MatrixHeightC2
    private val poolOfCharsInit: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor = Color.White

    suspend fun initializeMatrix(
        rows: Int = MatrixHeightC2,
        cols: Int = MatrixLengthC2
    ): MutableList<MutableList<MatrixCell2>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { MatrixCell2(poolOfCharsInit.random(), defaultColor) }
            }
        }
    }
}