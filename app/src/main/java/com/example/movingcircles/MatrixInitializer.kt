package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random



// Declare the array at the top of the file // *****// test


class MatrixInitializer {

    suspend fun initializeMatrix(
        rows: Int = heightOfMatrix,  // Use heightOfMatrix
        cols: Int = lengthOfMatrix // Use lengthOfMatrix
    ): MutableList<MutableList<Char>> {
        return withContext(Dispatchers.Default) {
            MutableList(rows) {
                MutableList(cols) {
                    poolOfCharInitial.random() // Use a random character from the array
                }
            }
        }
    }
}