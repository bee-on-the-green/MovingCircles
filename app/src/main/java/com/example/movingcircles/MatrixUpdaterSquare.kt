package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare(
    var matrix: Array<CharArray>,
    private val MatrixLength: Int = MatrixInitializerSquare().MatrixLength,  // Reference from MatrixInitializerSquare
    private val MatrixHeigth: Int = MatrixInitializerSquare().MatrixHeight,  // Reference from MatrixInitializerSquare
    val sleepTime: Long = 4,
    val breakPoint: Int = 19,
    val poolOfChar: Array<Char> = arrayOf('0', '1', '0'),
    val poolOfChar2: Array<Char> = arrayOf('·', '.', '\'', '·', '.', '\'', '.', '\'')
) {
    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating(onMatrixUpdated: (Array<CharArray>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage(matrixCopy, poolOfChar)
                onMatrixUpdated(matrixCopy, switchValue)
                Thread.sleep(sleepTime)
            }
        }
    }

    fun stopUpdating() {
        isRunning = false
    }

    private fun updateMatrix() {
        val (randomX, randomY) = selectRandomCoordinate()
        val currentLength = if (updateCount % 2 == 0) 21 else 1
        val currentWidth = if (updateCount % 2 == 0) 1 else 21

        drawRectangle(randomX, randomY, currentLength, currentWidth, poolOfChar)
        if (calculateCharacterPercentage(matrix, poolOfChar) > breakPoint) {
            drawRectangle(randomX, randomY, currentLength, currentWidth, poolOfChar2)
        }
        updateCount++
    }

    private fun selectRandomCoordinate(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLength),
            Random.nextInt(0, MatrixHeigth)
        )
    }

    private fun drawRectangle(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeigth - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLength - 1)) {
                matrix[y][x] = poolOfChar.random()
            }
        }
    }

    private fun calculateCharacterPercentage(
        matrix: Array<CharArray>,
        poolOfChar: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { char -> if (char in poolOfChar) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}