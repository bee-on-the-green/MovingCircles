package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare2(
    var matrix: Array<Array<MatrixCell2>>,
    private val MatrixLength: Int = MatrixInitializerSquare2().MatrixLength,  // Reference from Initializer
    private val MatrixHeight: Int = MatrixInitializerSquare2().MatrixHeight,  // Reference from Initializer
    val sleepTime: Long = 6,
    val breakPoint: Int = 80,
    val poolOfChar: Array<Char> = arrayOf('Ͽ', 'Ͼ'),
    val poolOfChar2: Array<Char> = arrayOf('0', '1')
) {
    private var isRunning = false
    private var updateCount: Int = 0

    // Colors (kept as-is)
    private val MatrixRed5 = Color(0xFFFF1744)
    private val MatrixGreen1 = Color(0xFF00FF00)
    private val Orange700 = Color(0xFFF57C00)
    private val PureBlack = Color(0xFF000000)

    suspend fun startUpdating(onMatrixUpdated: (Array<Array<MatrixCell2>>, Double) -> Unit) {
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
        if (calculateCharacterPercentage(matrix, poolOfChar) < breakPoint) {
            if (updateCount % 2 == 0) {
                val (randomX, randomY) = selectRandomCoordinate()
                drawRectangle(randomX, randomY, 20, 1, poolOfChar, Orange700)  // Vertical
                drawRectangle(randomX, randomY, 1, 20, poolOfChar, MatrixRed5)  // Horizontal
            }
        } else {
            if (updateCount % 2 != 0) {
                val (randomX, randomY) = selectRandomCoordinate()
                drawRectangle(randomX, randomY, 1, 2, poolOfChar2, PureBlack)
                drawRectangle(randomX, randomY, 2, 1, poolOfChar2, PureBlack)
            }
        }
        updateCount++
    }

    private fun selectRandomCoordinate(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLength),
            Random.nextInt(0, MatrixHeight)
        )
    }

    private fun drawRectangle(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2
        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeight - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLength - 1)) {
                matrix[y][x] = MatrixCell2(poolOfChar.random(), color)
            }
        }
    }

    private fun calculateCharacterPercentage(
        matrix: Array<Array<MatrixCell2>>,
        poolOfChar: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { cell -> if (cell.char in poolOfChar) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}