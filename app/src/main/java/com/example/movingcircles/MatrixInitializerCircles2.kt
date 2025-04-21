package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MatrixInitializerCircles2 {
    private val MatrixLength: Int = 100
    private val MatrixHeight: Int = MatrixLength * 42 / 100
    private val poolOfCharsInit: Array<Char> = arrayOf(' ', ' ')
    private val defaultColor = Color.White

    suspend fun initializeMatrix(
        rows: Int = MatrixHeight,
        cols: Int = MatrixLength
    ): MutableList<MutableList<MatrixCell2>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { MatrixCell2(poolOfCharsInit.random(), defaultColor) }
            }
        }
    }
}