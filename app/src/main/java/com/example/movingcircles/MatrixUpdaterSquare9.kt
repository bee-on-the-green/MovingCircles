package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare9(
    var matrix: Array<CharArray>,
    var colorMatrix: Array<Array<Color>>
) {
    private val MatrixLengthS9: Int = matrix[0].size
    private val MatrixHeightS9: Int = matrix.size
    val sleepTimeS9: Long = 10


    val breakPointS9: Int = 92


    val poolOfChar: Array<Char> = arrayOf('○', '•', '•', 'o', '0', '·', '·', '·')  // '○', '•', '•',
    val poolOfChar2: Array<Char> = arrayOf('○', '•', '•', 'o', '0', '·', '·', '·')  // ○', '°'   (·,  , '°', '○', ')', '(') // ('○', '°', '°', 'o', '°', '°', 'O')

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

    suspend fun startUpdating9(onMatrixUpdated: (Array<CharArray>, Array<Array<Color>>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix9()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val colorMatrixCopy = colorMatrix.map { it.clone() }.toTypedArray()
                // Now we are calculating the percentage of a specific color, not characters from a pool
                val switchValue = calculateColorPercentage9(colorMatrixCopy, Color(0xFF121212))  // was 0xFF1B5E20
                onMatrixUpdated(matrixCopy, colorMatrixCopy, switchValue)
                Thread.sleep(sleepTimeS9)
            }
        }
    }


    fun stopUpdating9() {
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
    private val DarkGreen900_Shade3 = Color(0xFF0C370F)



    private var counter9 = 0

    private fun updateMatrix9() {


        val (randomX, randomY) = selectRandomCoordinate9()

        val (currentLength, currentWidth) = if (counter9 % 2 == 0) {

            1 to 20
        } else {

            20 to 1  /// 20 is fine
        }


        drawRectangle9(
            randomX,
            randomY,
            currentWidth,
            currentLength,
            poolOfChar,
            Color(0xFF121212))


        // Now checking the percentage of the green color
        if (calculateColorPercentage9(colorMatrix, Color(0xFF121212)) > breakPointS9) {
            drawRectangle9(
                randomX,
                randomY,
                currentWidth,
                currentLength,
                poolOfChar2,
                Color(0xFFFF5500) // Drawing with the orange-red color
            )
        }
        counter9++

    }


    private fun selectRandomCoordinate9(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLengthS9),
            Random.nextInt(0, MatrixHeightS9)
        )
    }

    private fun drawRectangle9(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS9 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS9 - 1)) {
                matrix[y][x] = poolOfChar.random()
                colorMatrix[y][x] = color
            }
        }
    }

    // New function to calculate percentage of a specific color
    private fun calculateColorPercentage9(
        colorMatrix: Array<Array<Color>>,
        targetColor: Color
    ): Double {
        var count = 0
        colorMatrix.forEach { row -> row.forEach { color -> if (color == targetColor) count++ } }
        return (count.toDouble() / (colorMatrix.size * colorMatrix[0].size)) * 100
    }
}