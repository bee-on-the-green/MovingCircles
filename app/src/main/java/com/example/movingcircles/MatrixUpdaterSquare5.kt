package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare5(
    var matrix: Array<CharArray>,
    var colorMatrix: Array<Array<Color>>
) {
    private val MatrixLengthS5: Int = matrix[0].size
    private val MatrixHeightS5: Int = matrix.size
    val sleepTimeS5: Long = 10

    val breakPointS: Int = 93

    val poolOfChar2: Array<Char> = arrayOf('○', '°', '○', '°', '°', '°')  //  o O 0
    val poolOfChar: Array<Char> = arrayOf('(', ')')

    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating5(onMatrixUpdated: (Array<CharArray>, Array<Array<Color>>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix5()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val colorMatrixCopy = colorMatrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage5(matrixCopy, poolOfChar)
                onMatrixUpdated(matrixCopy, colorMatrixCopy, switchValue)
                Thread.sleep(sleepTimeS5)
            }
        }
    }

    private val Red900 = Color(0xFFB71C1C)
    private val Red800 = Color(0xFFC62828)
    private val Red700 = Color(0xFFD32F2F)
    private val Red600 = Color(0xFFE53935)

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
    private val NeonGreen = Color(0xFF00FF00)
    private val ElectricGreen = Color(0xFF00FF7F)
    private val LimeGreen = Color(0xFF32CD32)
    private val AcidGreen = Color(0xFFADFF2F)
    private val EmeraldGreen = Color(0xFF50C878)

    private val Orange50 = Color(0xFFFFF3E0)
    private val Orange100 = Color(0xFFFFE0B2)
    private val Orange200 = Color(0xFFFFCC80)
    private val Orange300 = Color(0xFFFFB74D)
    private val Orange400 = Color(0xFFFFA726)
    private val Orange500 = Color(0xFFFF9800)
    private val Orange600 = Color(0xFFFB8C00)  // orange 600
    private val Orange700 = Color(0xFFF57C00)
    private val Orange800 = Color(0xFFEF6C00)
    private val Orange850 = Color(0xFFE65100)
    private val Orange900 = Color(0xFFE64A19)
    private val OrangeA100 = Color(0xFFFFD180)
    private val OrangeA200 = Color(0xFFFFAB40)
    private val OrangeA400 = Color(0xFFFF9100)
    private val OrangeA700 = Color(0xFFFF6D00)

    private val NeonOrange = Color(0xFFFF6600)
    private val ElectricOrange = Color(0xFFFF4500)
    private val SunsetOrange = Color(0xFFFF5F15)
    private val TrafficOrange = Color(0xFFFF5500)
    private val PumpkinGlow = Color(0xFFFF6D00)
    private val TangerineBurst = Color(0xFFFF8C00)
    private val FlamingOrange = Color(0xFFFF7F33)
    private val CyberOrange = Color(0xFFFF4D00)

    private val UltraOrange = Color(0xFFFF3D00)
    private val LaserOrange = Color(0xFFFF2A00)
    private val HighlighterOrange = Color(0xFFFFA500)
    private val SafetyOrange = Color(0xFFFF7900)

    private val Red500 = Color(0xFFF44336)
    private val Red400 = Color(0xFFEF5350)

    fun stopUpdating5() {
        isRunning = false
    }



    //private var counter5 = 0

    private fun updateMatrix5() {

        val (randomX, randomY) = selectRandomCoordinate5()
        val currentWidth5 = 1
        val currentLength5 = 6  // 7 can be okay too


        drawRectangle5(
            randomX,
            randomY,
            currentWidth5,
            currentLength5,
            poolOfChar,
            Color(0xFF1B5E20)  // was   0xFF00FF00
        )

        if (calculateCharacterPercentage5(matrix, poolOfChar) > breakPointS) {
            drawRectangle5(
                randomX,
                randomY,
                currentWidth5,
                currentLength5,
                poolOfChar2,
                Color(0xFF00FF00)  // was 0xFFFF5F15  // orange A700  // orange 600
            )
        }
        //counter5++ // Increment the counter each time the function is called

    }








    private fun selectRandomCoordinate5(): Pair<Int, Int> {

        return Pair(
            Random.nextInt(0, MatrixLengthS5),
            Random.nextInt(0, MatrixHeightS5)
        )
    }

    private fun drawRectangle5(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS5 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS5 - 1)) {
                matrix[y][x] = poolOfChar.random()
                colorMatrix[y][x] = color
            }
        }
    }

    private fun calculateCharacterPercentage5(
        matrix: Array<CharArray>,
        poolOfChar: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { char -> if (char in poolOfChar) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}