package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare7(
    var matrix: Array<CharArray>,
    var colorMatrix: Array<Array<Color>>
) {
    private val MatrixLengthS7: Int = matrix[0].size
    private val MatrixHeightS7: Int = matrix.size
    val sleepTimeS7: Long = 50


    val breakPointS7: Int = 94


    val poolOfChar: Array<Char> = arrayOf('○', '°', '•', '○', '•', '•', ')', '(')
    val poolOfChar2: Array<Char> = arrayOf('○', '°', '•', '○', '°','•', ')', '(')  // ○', '°', '°', '○', ')', '(') // ('○', '°', '°', 'o', '°', '°', 'O')

    /*
        val poolOfChar: Array<Char> = arrayOf(
            '§', '¶', '¬', '¢', '£',
            '¥', '®', '©', 'ª', 'º',
            'É', 'Ê', 'Ë', 'Ì', 'Í',
            'K', 'L', 'M', 'N',
            'u', 'v', 'w', 'x', 'y', 'z',
            '!', '@', '#', '$', '%',
            '^', '&', '*', '(', ')',
            ']', '{',
            ';', ':', '\'', '"', ',',
            '.', '<', '>', '/', '?'
        )

    */
    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating7(onMatrixUpdated: (Array<CharArray>, Array<Array<Color>>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix7()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val colorMatrixCopy = colorMatrix.map { it.clone() }.toTypedArray()
                // Now we are calculating the percentage of a specific color, not characters from a pool
                val switchValue = calculateColorPercentage7(colorMatrixCopy, Color(0xFF1B5E20))
                onMatrixUpdated(matrixCopy, colorMatrixCopy, switchValue)
                Thread.sleep(sleepTimeS7)
            }
        }
    }


    fun stopUpdating7() {
        isRunning = false
    }


    val Violet400 = Color(0xFF7E57C2)    // Rich violet
    val Amethyst500 = Color(0xFF6D4B8C) // Stone-like
    val Magenta200 = Color(0xFFE040FB)  // Electric purple-pink
    val NeonPurple = Color(0xFFC158FF)  // Bright futuristic
    val RoyalPurple = Color(0xFF673AB7) // Classic regal tone
    private val Violet225_dark1 = Color(0xFFB57AC0)
    private val Violet225_dark2 = Color(0xFFA370AD)
    private val ComplementaryMagenta = Color(0xFF5E1B59)
    private val NeonBlastPink = Color(0xFFFF00F6)
    private val ElectricMagenta = Color(0xFFFF00FF)
    private val RadioactivePink = Color(0xFFFF00AA)
    private val CyberPink = Color(0xFFFF0099)
    private val ScreamingPink = Color(0xFFFF00CC)
    private val ElectricOrange = Color(0xFFFF4500)
    private val SunsetOrange = Color(0xFFFF5F15)
    private val TrafficOrange = Color(0xFFFF5500)
    private val PumpkinGlow = Color(0xFFFF6D00)
    private val TangerineBurst = Color(0xFFFF8C00)
    private val FlamingOrange = Color(0xFFFF7F33)
    private val CyberOrange = Color(0xFFFF4D00)



    private var counter7 = 0

    private fun updateMatrix7() {


        val (randomX, randomY) = selectRandomCoordinate7()

        val (currentLength, currentWidth) = if (counter7 % 2 == 0) {

            14 to 1
        } else {

            14 to 1
        }


        drawRectangle7(
            randomX,
            randomY,
            currentWidth,
            currentLength,
            poolOfChar,
            Color(0xFF5E1B59) // Drawing with the green color 0xFF5E1B59  // good violet 0xFF673AB7
        )

        // Now checking the percentage of the green color
        if (calculateColorPercentage7(colorMatrix, Color(0xFF5E1B59)) > breakPointS7) {
            drawRectangle7(
                randomX,
                randomY,
                currentWidth,
                currentLength,
                poolOfChar2,
                Color(0xFFFF5500) // Drawing with the orange-red color
            )
        }
        counter7++

    }


    private fun selectRandomCoordinate7(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLengthS7),
            Random.nextInt(0, MatrixHeightS7)
        )
    }

    private fun drawRectangle7(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS7 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS7 - 1)) {
                matrix[y][x] = poolOfChar.random()
                colorMatrix[y][x] = color
            }
        }
    }

    // New function to calculate percentage of a specific color
    private fun calculateColorPercentage7(
        colorMatrix: Array<Array<Color>>,
        targetColor: Color
    ): Double {
        var count = 0
        colorMatrix.forEach { row -> row.forEach { color -> if (color == targetColor) count++ } }
        return (count.toDouble() / (colorMatrix.size * colorMatrix[0].size)) * 100
    }
}