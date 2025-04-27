package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare3(
    var matrix: Array<CharArray>,
    private val squareMatrixLength: Int = 80,
    private val squareMatrixHeight: Int = squareMatrixLength * 42 / 100,
    val sleepTime: Long = 4,
    val lengthRectangle: Int = 15,
    val widthRectangles: Int = 1,
    val breakPoint: Int = 19,
    val poolOfChar: Array<Char> = arrayOf('0', '1', '0'),  // Simplified primary chars
    val poolOfChar2: Array<Char> = arrayOf('·', '.', '²', '·', '.', '·', '.', '\'')  // Simplified secondary chars
) {
    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating3(onMatrixUpdated: (Array<CharArray>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix3()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage3(matrixCopy, poolOfChar)
                onMatrixUpdated(matrixCopy, switchValue)
                Thread.sleep(sleepTime)
            }
        }
    }

    fun stopUpdating3() {
        isRunning = false
    }

    private fun updateMatrix3() {
        val (randomX, randomY) = selectRandomCoordinate3()
        val currentLength = if (updateCount % 2 == 0) 21 else 1
        val currentWidth = if (updateCount % 2 == 0) 1 else 21

        drawRectangle3(randomX, randomY, currentLength, currentWidth, poolOfChar)
        if (calculateCharacterPercentage3(matrix, poolOfChar) > breakPoint) {
            drawRectangle3(randomX, randomY, currentLength, currentWidth, poolOfChar2)
        }
        updateCount++
    }

    private fun selectRandomCoordinate3(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, squareMatrixLength),
            Random.nextInt(0, squareMatrixHeight)
        )
    }

    private fun drawRectangle3(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, squareMatrixHeight - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, squareMatrixLength - 1)) {
                matrix[y][x] = poolOfChar.random()
            }
        }
    }

    private fun calculateCharacterPercentage3(
        matrix: Array<CharArray>,
        poolOfChar: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { char -> if (char in poolOfChar) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}