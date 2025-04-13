package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import com.example.movingcircles.ui.theme.Pink800
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterCircles2(
    var matrix: Array<Array<MatrixCell2>>,
    val sleepTime: Long = 10,
    val diameterToUse: Int = 9, // was 11
    val breakPoint: Int = 30,
    val poolOfChar: Array<Char> = arrayOf('{', '}'),
    val poolOfChar2: Array<Char> = arrayOf('.', '·')
) {

    private val Orange900 = Color(0xFFE65100)
    private val Orange850 = Color(0xFFD84300)  // Custom (between 800-900)
    private val Orange700 = Color(0xFFF57C00)  // Material Orange 700 (different hue)
    private val Orange750 = Color(0xFFEF6C00)  // Custom (between 700-800)
    private val Orange800 = Color(0xFFEF6C00)  // Burnt orange


    private val Pink500 = Color(0xFFE91E63)
    private val Pink600 = Color(0xFFD81B60)
    private val Pink700 = Color(0xFFC2185B)
    private val Pink800 = Color(0xFFAD1457)
    private val Pink900 = Color(0xFF880E4F)  // Deep magenta


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
                Thread.sleep(sleepTime)
            }
        }
    }

    fun stopUpdating() {
        isRunning = false
    }

    private fun updateMatrix() {
        val (myRandomX, myRandomY) = selectRandomCoordinate()

        if (updateCount % 2 == 0) {
            // Even count - use original colors
            drawCircle(matrix, myRandomX, myRandomY, diameterToUse, poolOfChar, Orange800)
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUse, poolOfChar2, Pink600)
            }
        } else {
            // Odd count - use alternate colors
            drawCircle(matrix, myRandomX, myRandomY, diameterToUse, poolOfChar, Orange900)
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUse, poolOfChar2, Pink900)
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
                val aspectRatio = 1.9 // was 2.0
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