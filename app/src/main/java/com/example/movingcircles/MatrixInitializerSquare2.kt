package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixInitializerSquare2 {
    private val square2MatrixLength: Int = 102  // Unique to squares
    private val square2MatrixHeight: Int = square2MatrixLength * 42 / 100  // 55% aspect ratio
    private val square2PoolOfChars: Array<Char> = arrayOf('.', '·')  // Unique character pool

    suspend fun initializeMatrix(
        rows: Int = square2MatrixHeight,
        cols: Int = square2MatrixLength
    ): MutableList<MutableList<Char>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { square2PoolOfChars.random() }
            }
        }
    }
}