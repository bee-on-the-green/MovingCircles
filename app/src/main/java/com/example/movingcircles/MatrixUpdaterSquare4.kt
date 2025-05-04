package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare4(
    var matrix: Array<CharArray>,
    private val MatrixLengthS4: Int = MatrixInitializerSquare4().MatrixLengthS4,
    private val MatrixHeightS4: Int = MatrixInitializerSquare4().MatrixHeightS4,
    val sleepTime: Long = 5,

    val breakPoint: Int = 80,
    val poolOfChar2: Array<Char> = arrayOf('°', '²', ',', ',', '•'),
    val poolOfChar: Array<Char> = arrayOf('·', '.', '\'', '·', '.', '.', '\''),  // ˆ • ¸
) {
    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating4(onMatrixUpdated: (Array<CharArray>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix4()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage4(matrixCopy, poolOfChar)
                onMatrixUpdated(matrixCopy, switchValue)
                Thread.sleep(sleepTime)
            }
        }
    }

    fun stopUpdating4() {
        isRunning = false
    }

    private fun updateMatrix4() {
        val (randomX, randomY) = selectRandomCoordinate4()
        val currentLength = 3
        val currentWidth = 5

        drawRectangle4(randomX, randomY, currentLength, currentWidth, poolOfChar)

        if (calculateCharacterPercentage4(matrix, poolOfChar) > breakPoint) {
            drawRectangle4(randomX, randomY, currentWidth, currentLength, poolOfChar2)
        }
        updateCount++
    }

    private fun selectRandomCoordinate4(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLengthS4),
            Random.nextInt(0, MatrixHeightS4)
        )
    }

    private fun drawRectangle4(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS4 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS4 - 1)) {
                matrix[y][x] = poolOfChar.random()
            }
        }
    }

    private fun calculateCharacterPercentage4(
        matrix: Array<CharArray>,
        poolOfChar: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { char -> if (char in poolOfChar) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}