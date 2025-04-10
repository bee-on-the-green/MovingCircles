package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquareSmall(
    var matrix: Array<CharArray>, // Matrix is now mutable
    val sleepTime: Long = 10,
    val diameterToUse: Int = 11,
    val breakPoint: Int = 30,

    val poolOfChar: Array<Char> = arrayOf('{', '}'),
    val poolOfChar2: Array<Char> = arrayOf('.', '·')
) {
    private var isRunning = false

    suspend fun startUpdating(onMatrixUpdated: (Array<CharArray>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage(matrixCopy, poolOfChar) // Calculate SwitchValue
                onMatrixUpdated(matrixCopy, switchValue) // Pass both the matrix and SwitchValue
                Thread.sleep(sleepTime)
            }
        }
    }

    fun stopUpdating() {
        isRunning = false
    }

    private fun updateMatrix() {
        val (myRandomX, myRandomY) = selectRandomCoordinate()
        drawCircle(matrix, myRandomX, myRandomY, diameterToUse, poolOfChar)
        val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
        if (mainCharPercentageAtCurrentTime > breakPoint) {
            drawCircle(matrix, myRandomX, myRandomY, diameterToUse, poolOfChar2)
        }
    }

    private fun selectRandomCoordinate(): Pair<Int, Int> {
        val myRandomCoordinateY = Random.nextInt(0, matrix.size)
        val myRandomCoordinateX = Random.nextInt(0, if (matrix.isNotEmpty()) matrix[0].size else 0)
        return Pair(myRandomCoordinateX, myRandomCoordinateY)
    }

    private fun drawCircle(
        matrix: Array<CharArray>, centerX: Int, centerY: Int,
        diameter: Int, poolOfChar: Array<Char>
    ) {
        val radius = diameter / 2
        val yStart = maxOf(centerY - radius, 0)
        val yEnd = minOf(centerY + radius, matrix.size - 1)
        val xStart = maxOf(centerX - radius, 0)
        val xEnd = minOf(centerX + radius, if (matrix.isNotEmpty() && matrix[0].isNotEmpty()) matrix[0].size - 1 else 0)

        for (y in yStart..yEnd) {
            for (x in xStart..xEnd) {
                val aspectRatio = 2.0
                val dx = x - centerX
                val dy = (y - centerY) * aspectRatio
                if (dx * dx + dy * dy <= radius * radius) {
                    val randomChar = poolOfChar[Random.nextInt(poolOfChar.size)]
                    matrix[y][x] = randomChar
                }
            }
        }
    }

    private fun calculateCharacterPercentage(
        matrix: Array<CharArray>, poolOfChar: Array<Char>
    ): Double {
        var count = 0
        val totalCells = matrix.size * if (matrix.isNotEmpty() && matrix[0].isNotEmpty()) matrix[0].size else 0
        matrix.forEach { row ->
            row.forEach { char ->
                if (char in poolOfChar) {
                    count++
                }
            }
        }
        return (count.toDouble() / totalCells) * 100
    }
}