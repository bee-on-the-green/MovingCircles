package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare4(
    var matrix: Array<CharArray>,
    var colorMatrix: Array<Array<Color>>
) {
    private val MatrixLengthS4: Int = matrix[0].size
    private val MatrixHeightS4: Int = matrix.size
    val sleepTime: Long = 70

    val breakPointS: Int = 80

    val poolOfChar: Array<Char> = arrayOf(')', '(')  //  ɷ  ʊ ʋ  ŏőŐ
    val poolOfChar2: Array<Char> = arrayOf('•', '°')


    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating4(onMatrixUpdated: (Array<CharArray>, Array<Array<Color>>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix4()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val colorMatrixCopy = colorMatrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage4(matrixCopy, poolOfChar)
                onMatrixUpdated(matrixCopy, colorMatrixCopy, switchValue)
                Thread.sleep(sleepTime)
            }
        }
    }



    private val Red900 = Color(0xFFB71C1C)
    private val Red800 = Color(0xFFC62828)   // Darker than B71C1C but still bright
    private val Red700 = Color(0xFFD32F2F)   // Slightly lighter
    private val Red600 = Color(0xFFE53935)   // Brighter red

    private val Orange950 = Color(0xFFBF360C)
    private val Violet200 = Color(0xFFCE93D8)
    private val Violet300 = Color(0xFFBA68C8)
    private val Black1000 = Color(0xFF010101)
    private val Black950 = Color(0xFF0A0A0A)
    private val Black900 = Color(0xFF121212)
    private val BrightRed100 = Color(0xFFFFCDD2)
    private val BrightRed200 = Color(0xFFEF9A9A)
    private val BrightRed300 = Color(0xFFE57373)
    private val BrightRed400 = Color(0xFFEF5350)
    private val BrightRed500 = Color(0xFFF44336)
    private val BrightRed600 = Color(0xFFE53935)
    private val BrightRed700 = Color(0xFFD32F2F)
    private val BrightRed800 = Color(0xFFC62828)
    private val Gray700 = Color(0xFF616161)
    private val Gray800 = Color(0xFF424242)
    private val VividRed = Color(0xFFFF0000)
    private val ScarletRed = Color(0xFFFF2400)
    private val CrimsonRed = Color(0xFFDC143C)
    private val RubyRed = Color(0xFFE0115F)
    private val FerrariRed = Color(0xFFFF2800)
    private val NeonBlastPink = Color(0xFFFF00F6)
    private val ElectricMagenta = Color(0xFFFF00FF)
    private val RadioactivePink = Color(0xFFFF00AA)
    private val CyberPink = Color(0xFFFF0099)
    private val ScreamingPink = Color(0xFFFF00CC)
    private val Green200 = Color(0xFFA5D6A7)
    private val Green100 = Color(0xFFC8E6C9)
    private val Green50 = Color(0xFFE8F5E9)
    private val Green850 = Color(0xFF2E7D32)
    private val Green900 = Color(0xFF1B5E20)
    private val NeonGreen = Color(0xFF00FF00)  // Pure neon green (very flashy!)
    private val ElectricGreen = Color(0xFF00FF7F)  // Bright teal-green
    private val LimeGreen = Color(0xFF32CD32)  // Classic lime green
    private val AcidGreen = Color(0xFFADFF2F)  // Highlighter-like green
    private val EmeraldGreen = Color(0xFF50C878)  // Rich emerald shine


    private val Orange50 = Color(0xFFFFF3E0)
    private val Orange100 = Color(0xFFFFE0B2)
    private val Orange200 = Color(0xFFFFCC80)
    private val Orange300 = Color(0xFFFFB74D)
    private val Orange400 = Color(0xFFFFA726)
    private val Orange500 = Color(0xFFFF9800)
    private val Orange600 = Color(0xFFFB8C00)
    private val Orange700 = Color(0xFFF57C00)
    private val Orange800 = Color(0xFFEF6C00)
    private val Orange850 = Color(0xFFE65100)  // Your existing color
    private val Orange900 = Color(0xFFE64A19)
    private val OrangeA100 = Color(0xFFFFD180)
    private val OrangeA200 = Color(0xFFFFAB40)
    private val OrangeA400 = Color(0xFFFF9100)
    private val OrangeA700 = Color(0xFFFF6D00)




    private val NeonOrange = Color(0xFFFF6600)  // Classic bright neon orange
    private val ElectricOrange = Color(0xFFFF4500)  // Intense, high-energy orange
    private val SunsetOrange = Color(0xFFFF5F15)  // Warm, glowing orange
    private val TrafficOrange = Color(0xFFFF5500)  // Eye-catching, traffic-cone orange
    private val PumpkinGlow = Color(0xFFFF6D00)  // Bright Halloween pumpkin
    private val TangerineBurst = Color(0xFFFF8C00)  // Juicy, saturated tangerine
    private val FlamingOrange = Color(0xFFFF7F33)  // Bright with a slight red tint
    private val CyberOrange = Color(0xFFFF4D00)  // Futuristic, high-contrast orange




    private val UltraOrange = Color(0xFFFF3D00)  // Almost glowing
    private val LaserOrange = Color(0xFFFF2A00)  // Red-orange with extreme brightness
    private val HighlighterOrange = Color(0xFFFFA500)  // Classic "Orange" web color (very bright)
    private val SafetyOrange = Color(0xFFFF7900)  // OSHA-approved high-visibility orange








    private val Red500 = Color(0xFFF44336)   // Classic Material Red
    private val Red400 = Color(0xFFEF5350)   // Lighter, softer red


    fun stopUpdating4() {
        isRunning = false
    }

    private fun updateMatrix4() {
        val (randomX, randomY) = selectRandomCoordinate4()

        var currentLength = 10
        var currentWidth = 1

        //var currentSmallLength = 8
        //var currentSmallWidth = 1

        drawRectangle4(
            randomX,
            randomY,
            currentWidth,
            currentLength,
            poolOfChar,
            Color(0xFF00FF00) // green  // NeonGreen  is 0xFF00FF00
        )

        /*
        drawRectangle4(
            randomX,
            randomY,
            currentSmallWidth,
            currentSmallLength,
            poolOfChar,
            Color(0xFF010101) // black
        )
        */

        if (calculateCharacterPercentage4(matrix, poolOfChar) > breakPointS) {
            val (randomX2, randomY2) = selectRandomCoordinate4()

            drawRectangle4(
                randomX2,
                randomY2,
                currentWidth,
                currentLength,
                poolOfChar2,
                Color(0xFFFF5F15) // red  // was 0xFFE64A19  0xFFF44336  red  // warm orange 0xFFFF5F15
            )

            /*
            drawRectangle4(
                randomX2,
                randomY2,
                currentSmallWidth,
                currentSmallLength,
                poolOfChar2,
                Color(0xFFFF00AA)  // orange 800
            )
            */
        }
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
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS4 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS4 - 1)) {
                matrix[y][x] = poolOfChar.random()
                colorMatrix[y][x] = color
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