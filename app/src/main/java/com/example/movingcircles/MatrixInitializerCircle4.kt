package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MatrixInitializerCircle4 {
    val MatrixLengthC4: Int = 72// 72
    val MatrixHeightC4: Int = MatrixLengthC4 * 49/ 100  // 50
    val resolution: Int = MatrixLengthC4 * MatrixHeightC4
    val diameterToUseC4: Int = 6  // was 7

    val resolutionC: Int = MatrixLengthC4 * MatrixHeightC4
    private val poolOfCharsInit: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor = Color.White

    suspend fun initializeMatrix(
        rows: Int = MatrixHeightC4,
        cols: Int = MatrixLengthC4
    ): MutableList<MutableList<MatrixCell2>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { MatrixCell2(poolOfCharsInit.random(), defaultColor) }
            }
        }
    }
}