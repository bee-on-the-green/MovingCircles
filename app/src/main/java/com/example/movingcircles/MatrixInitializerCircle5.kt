package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MatrixInitializerCircle5 {
    val MatrixLengthC5: Int = 110//
    val MatrixHeightC5: Int = MatrixLengthC5 * 82/ 100
    val resolution: Int = MatrixLengthC5 * MatrixHeightC5
    val diameterToUseC5: Int = 7  // 9 is fine

    val resolutionC: Int = MatrixLengthC5 * MatrixHeightC5
    private val poolOfCharsInit: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor = Color.White

    suspend fun initializeMatrix(
        rows: Int = MatrixHeightC5,
        cols: Int = MatrixLengthC5
    ): MutableList<MutableList<MatrixCell2>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { MatrixCell2(poolOfCharsInit.random(), defaultColor) }
            }
        }
    }
}
