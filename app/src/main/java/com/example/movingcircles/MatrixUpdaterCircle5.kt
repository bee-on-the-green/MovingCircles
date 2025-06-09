package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterCircle5(
    var matrix: Array<Array<MatrixCell2>>,
    val sleepTimeC5: Long = 28,
    val diameterToUseC5: Int,
    val breakPoint: Int = 86,  // was 86
    val poolOfChar: Array<Char> = arrayOf('·', '.', '\'', '·', '.', '.', '\''),
    val poolOfChar2: Array<Char> = arrayOf('°', '²', ',', ',', '•')
) {

    val Pink300 = Color(0xFFFF6B8B)  // Bright pink
    val Pink400 = Color(0xFFF75990)  // Vibrant pink
    val Pink500 = Color(0xFFE91E63)  // Classic Material pink (your reference)
    val Pink600 = Color(0xFFD81B60)  // Rich pink

    private val Gray40 = Color(0xFF666666)       // Medium-dark gray
    private val Gray30 = Color(0xFF4D4D4D)       // Dark gray

    private val Yellow1000 = Color(0xFFFF5722)  // Fiery orange-yellow. Changed to Yellow1000 instead of Yellow1000 for consistency


    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating(onMatrixUpdated: (Array<Array<MatrixCell2>>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage(matrixCopy, poolOfChar)
                onMatrixUpdated(matrixCopy, switchValue)
                Thread.sleep(sleepTimeC5)
            }
        }
    }

    fun stopUpdating() {
        isRunning = false
    }



    private fun updateMatrix() {
        val (myRandomX, myRandomY) = selectRandomCoordinate()

        if (updateCount % 2 == 0) {
            drawCircle(matrix, myRandomX, myRandomY, diameterToUseC5, poolOfChar, Gray30)
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUseC5, poolOfChar2, Pink300)
            }
        } else {
            drawCircle(matrix, myRandomX, myRandomY, diameterToUseC5, poolOfChar, Gray30)
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUseC5, poolOfChar2, Pink500)
            }
        }

        updateCount++
    }












    private fun selectRandomCoordinate(): Pair<Int, Int> {
        val myRandomCoordinateY = Random.nextInt(0, matrix.size)
        val myRandomCoordinateX = Random.nextInt(0, if (matrix.isNotEmpty()) matrix[0].size else 0)
        return Pair(myRandomCoordinateX, myRandomCoordinateY)
    }

    private fun drawCircle(
        matrix: Array<Array<MatrixCell2>>,
        centerX: Int,
        centerY: Int,
        diameter: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val radius = diameter / 2
        val yStart = maxOf(centerY - radius, 0)
        val yEnd = minOf(centerY + radius, matrix.size - 1)
        val xStart = maxOf(centerX - radius, 0)
        val xEnd = minOf(centerX + radius, if (matrix.isNotEmpty() && matrix[0].isNotEmpty()) matrix[0].size - 1 else 0)

        for (y in yStart..yEnd) {
            for (x in xStart..xEnd) {
                val aspectRatio = 1.9
                val dx = x - centerX
                val dy = (y - centerY) * aspectRatio
                if (dx * dx + dy * dy <= radius * radius) {
                    val randomChar = poolOfChar[Random.nextInt(poolOfChar.size)]
                    matrix[y][x] = MatrixCell2(randomChar, color)
                }
            }
        }
    }

    private fun calculateCharacterPercentage(
        matrix: Array<Array<MatrixCell2>>,
        poolOfChar: Array<Char>
    ): Double {
        var count = 0
        val totalCells = matrix.size * if (matrix.isNotEmpty() && matrix[0].isNotEmpty()) matrix[0].size else 0
        matrix.forEach { row ->
            row.forEach { cell ->
                if (cell.char in poolOfChar) {
                    count++
                }
            }
        }
        return (count.toDouble() / totalCells) * 100
    }
}
