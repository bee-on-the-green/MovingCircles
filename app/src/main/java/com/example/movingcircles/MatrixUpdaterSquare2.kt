package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare2(
    var matrix: Array<CharArray>,
    private val square2MatrixLength: Int = 102,
    private val square2MatrixHeight: Int = square2MatrixLength * 42 / 100,
    val sleepTime: Long = 5,
    val lengthRectangle: Int = 15,
    val widthRectangles: Int = 1,
    val breakPoint: Int = 30,
    val poolOfChar: Array<Char> = arrayOf('0', '1'),  // Simplified primary chars
    val poolOfChar2: Array<Char> = arrayOf('·', '·')  // Simplified secondary chars
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
        val currentLength = if (updateCount % 2 == 0) 20 else 1
        val currentWidth = if (updateCount % 2 == 0) 1 else 20

        drawRectangle(randomX, randomY, currentLength, currentWidth, poolOfChar)
        if (calculateCharacterPercentage(matrix, poolOfChar) > breakPoint) {
            drawRectangle(randomX, randomY, currentLength, currentWidth, poolOfChar2)
        }
        updateCount++
    }

    private fun selectRandomCoordinate(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, square2MatrixLength),
            Random.nextInt(0, square2MatrixHeight)
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

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, square2MatrixHeight - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, square2MatrixLength - 1)) {
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