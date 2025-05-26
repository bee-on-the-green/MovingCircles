package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random



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





class MatrixUpdaterSquare3(
    var matrix: Array<CharArray>,
    var colorMatrix: Array<Array<Color>>
) {
    private val MatrixLengthS3: Int = matrix[0].size
    private val MatrixHeightS3: Int = matrix.size
    val sleepTime: Long = 13

    val breakPoint: Int = 83  // ws 60
    val poolOfChar2: Array<Char> = arrayOf('°', '²', ',', ',', '•') //
    val poolOfChar: Array<Char> = arrayOf('·', '.', '\'', '·', '.', '.', '\'')

    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating3(onMatrixUpdated: (Array<CharArray>, Array<Array<Color>>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix3()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val colorMatrixCopy = colorMatrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage3(matrixCopy, poolOfChar)
                onMatrixUpdated(matrixCopy, colorMatrixCopy, switchValue)
                Thread.sleep(sleepTime)
            }
        }
    }

    fun stopUpdating3() {
        isRunning = false
    }

    private fun updateMatrix3() {
        val (randomX, randomY) = selectRandomCoordinate3()
        val currentLength3 = 3   // 5
        val currentWidth3 = 5  //  2

        drawRectangle3(randomX, randomY, currentWidth3, currentLength3 , poolOfChar, (Gray500))  // Gray400  // was Color(0xFFF57C00)) // 15

        if (calculateCharacterPercentage3(matrix, poolOfChar) > breakPoint) {
            drawRectangle3(randomX, randomY, currentWidth3, currentLength3, poolOfChar2, White)  // was
        }
        updateCount++
    }

    private fun selectRandomCoordinate3(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLengthS3),
            Random.nextInt(0, MatrixHeightS3)
        )
    }

    private fun drawRectangle3(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS3 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS3 - 1)) {
                matrix[y][x] = poolOfChar.random()
                colorMatrix[y][x] = color
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