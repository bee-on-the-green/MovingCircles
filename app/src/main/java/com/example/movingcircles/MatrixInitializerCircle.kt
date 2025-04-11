package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MatrixInitializerCircle {
    private val MatrixLength: Int = 20  // Unique to squareSmalls
    private val MatrixHeight: Int = MatrixLength * 42 / 100  // 55% aspect ratio
    private val poolOfChars: Array<Char> = arrayOf('.', '·')  // Unique character pool
// private val squareSmallPoolOfChars: Array<Char> = arrayOf('.', '·')  // Unique character pool



    suspend fun initializeMatrix(
        rows: Int = MatrixHeight,
        cols: Int = MatrixLength
    ): MutableList<MutableList<Char>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { poolOfChars.random() }
            }
        }
    }
}



/*

class MatrixInitializerSquareSmall {
    private val squareSmallMatrixLength: Int = 20  // Unique to squareSmalls
    private val squareSmallMatrixHeight: Int = squareSmallMatrixLength * 42 / 100  // 55% aspect ratio
    private val poolOfChars: Array<Char> = arrayOf('.', '·')  // Unique character pool
// private val squareSmallPoolOfChars: Array<Char> = arrayOf('.', '·')  // Unique character pool



    suspend fun initializeMatrix(
        rows: Int = squareSmallMatrixHeight,
        cols: Int = squareSmallMatrixLength
    ): MutableList<MutableList<Char>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) { poolOfChars.random() }
            }
        }
    }
}
*/
