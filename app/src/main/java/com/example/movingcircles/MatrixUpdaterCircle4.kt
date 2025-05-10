package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterCircle4(
    var matrix: Array<Array<MatrixCell2>>,
    val sleepTime: Long = 20,
    val diameterToUseC4: Int,
    val breakPoint: Int = 32,

    //'°', '²', ',', ',', '•'),
    //('·', '.', '\'', '·', '.', '.', '\''),  // ˆ • ¸

    val poolOfChar: Array<Char> = arrayOf( '°', '²', ',', ',', '•', ',', 'º', ','),  // ০ᐤ൦৹॰˚੦ⵙ◯೦〇ဝᲿഠ០௦᠐  // '°', '²', ',', ',', '•'
    val poolOfChar2: Array<Char> = arrayOf('·', '.', '\'', '·', '.', '.', '\'')  // '.', '·'
) {
    private val Violet200 = Color(0xFFCE93D8)
    private val Violet300 = Color(0xFFBA68C8)
    private val Orange800 = Color(0xFFEF6C00)
    private val Red900 = Color(0xFFB71C1C)
    private val White = Color(0xFFFFFFFF)       // Pure white
    private val Gray50 = Color(0xFFFAFAFA)      // Very light gray
    private val Gray100 = Color(0xFFF5F5F5)     //
    private val Gray200 = Color(0xFFEEEEEE)     //
    private val Gray300 = Color(0xFFE0E0E0)     //
    private val Gray350 = Color(0xFFD6D6D6)     // Between 300 and 400
    private val Gray400 = Color(0xFFBDBDBD)     //
    private val Gray500 = Color(0xFF9E9E9E)     // Medium gray
    private val Gray600 = Color(0xFF757575)     //
    private val Gray700 = Color(0xFF616161)     //
    private val Gray800 = Color(0xFF424242)     //
    private val Gray900 = Color(0xFF212121)     // Almost black

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
            drawCircle(matrix, myRandomX, myRandomY, diameterToUseC4, poolOfChar, Gray200)  // was 300
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUseC4, poolOfChar2, Gray400)  // was 400
            }
        } else {
            drawCircle(matrix, myRandomX, myRandomY, diameterToUseC4, poolOfChar, Gray200) // was 300
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUseC4, poolOfChar2, Gray350)  // was 400
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
                val aspectRatio = 2.4
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