package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare2(
    var matrix: Array<CharArray>,
    var colorMatrix: Array<Array<Color>>
) {
    private val MatrixLengthS2: Int = matrix[0].size
    private val MatrixHeightS2: Int = matrix.size
    val sleepTimeS2: Long = 1060

    val breakPointK: Int = 39
    val poolOfChar: Array<Char> = arrayOf('Ͼ', 'Ͽ')
    val poolOfChar2: Array<Char> = arrayOf('0', '0')

    private var isRunning = false
    var updateCount: Int = 0  // Changed to var for pulsing effect


    private val JetBlack = Color(0xFF121212)

    private val ScarletPulse2 = Color(0xFFFF3C00)


    // Glow effect colors for ScarletPulse2
    private val ScarletPulseGlow = listOf(
        Color(0xFFFF3C00),  // Original
        Color(0xFFFF4C10),  // Slightly brighter
        Color(0xFFFF5C20),  // Even brighter
        Color(0xFFFF4C10)   // Back to medium
    )

    suspend fun startUpdating2(onMatrixUpdated: (Array<CharArray>, Array<Array<Color>>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix2()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val colorMatrixCopy = colorMatrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage2(matrixCopy, poolOfChar)
                onMatrixUpdated(matrixCopy, colorMatrixCopy, switchValue)
                Thread.sleep(sleepTimeS2)
            }
        }
    }

    fun stopUpdating2() {
        isRunning = false
    }




    private fun updateMatrix2() {
        val (randomX, randomY) = selectRandomCoordinate2()
        // Use ScarletPulse2 instead of ScarletOrange to see the glow effect
        drawRectangle2(randomX, randomY, 4, 4, poolOfChar, ScarletPulse2)  // was ScarletPulse2  // was 4

        val charPercentageAtCurrentTime = calculateCharacterPercentage2(matrix, poolOfChar)

        if (charPercentageAtCurrentTime > breakPointK) {
            //val (randomX, randomY) = selectRandomCoordinate2()
            drawRectangle2(randomX, randomY, 4, 4, poolOfChar2, JetBlack)  // was 4
        }
        updateCount++
    }







    private fun selectRandomCoordinate2(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLengthS2),
            Random.nextInt(0, MatrixHeightS2)
        )
    }

    private fun drawRectangle2(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2
        val pulseIndex = (updateCount / 2 % ScarletPulseGlow.size) // was 1
        val pulseColor = if (color == ScarletPulse2) ScarletPulseGlow[pulseIndex] else color

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS2 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS2 - 1)) {
                matrix[y][x] = poolOfChar.random()
                colorMatrix[y][x] = pulseColor
            }
        }
    }

    private fun calculateCharacterPercentage2(
        matrix: Array<CharArray>,
        poolOfChar: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { char -> if (char in poolOfChar) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}