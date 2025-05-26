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
    val sleepTime: Long = 40

    val breakPointK: Int = 40
    val poolOfChar: Array<Char> = arrayOf('Ͼ', 'Ͽ')
    val poolOfChar2: Array<Char> = arrayOf('0', '0')

    private var isRunning = false
    var updateCount: Int = 0  // Changed to var for pulsing effect

    // Color declarations
    private val PinkNeon = Color(0xFFFF00FF)
    private val Orange700 = Color(0xFFF57C00)
    private val MatrixRed5 = Color(0xFFFF1744)
    private val Orange800 = Color(0xFFEF6C00)
    private val Black850 = Color(0xFF1A1A1A)
    private val Black800 = Color(0xFF212121)
    private val Violet225_dark1 = Color(0xFFB57AC0)
    private val Violet225_dark2 = Color(0xFFA370AD)
    private val Orange600 = Color(0xFFFB8C00)
    private val Orange500 = Color(0xFFFF9800)
    private val Orange400 = Color(0xFFFFA726)
    private val Orange300 = Color(0xFFFFB74D)
    private val Orange200 = Color(0xFFFFCC80)
    private val Orange100 = Color(0xFFFFE0B2)
    private val Orange50 = Color(0xFFFFF3E0)
    private val Orange850 = Color(0xFFE65100)
    private val Orange900 = Color(0xFFD84315)
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
    private val BrightRed900 = Color(0xFFB71C1C)
    private val Gray600 = Color(0xFF757575)
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
    private val PsychedelicPink = Color(0xFFFF00EE)
    private val ToxicPink = Color(0xFFFF00BB)
    private val Black = Color(0xFF000000)
    private val RichBlack = Color(0xFF0A0A0A)
    private val JetBlack = Color(0xFF121212)
    private val InkBlack = Color(0xFF1A1A1A)
    private val ScarletOrange = Color(0xFFFF4000)
    private val ScarletPink = Color(0xFFFF0066)
    private val ScarletVoltage = Color(0xFFFF00AA)
    private val ScarletPulse1 = Color(0xFFFF2400)
    private val ScarletPulse2 = Color(0xFFFF3C00)
    private val ScarletPulse3 = Color(0xFFFF2400)
    private val ScarletPulse4 = Color(0xFFFF0055)
    private val ScarletHolo1 = Color(0xFFFF2400)
    private val ScarletHolo2 = Color(0xFFFF5E00)
    private val ScarletHolo3 = Color(0xFFD10000)

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
                Thread.sleep(sleepTime)
            }
        }
    }

    fun stopUpdating2() {
        isRunning = false
    }


    /*
    private fun updateMatrix2() {
        val (randomX, randomY) = selectRandomCoordinate2()
        drawRectangle2(randomX, randomY, 4, 4, poolOfChar, ScarletOrange)

        val charPercentageAtCurrentTime = calculateCharacterPercentage2(matrix, poolOfChar)

        if (charPercentageAtCurrentTime > breakPoint) {
            val (randomX2, randomY2) = selectRandomCoordinate2()
            drawRectangle2(randomX2, randomY2, 4, 4, poolOfChar2, JetBlack)
        }
        updateCount++
    }

  */


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
        val pulseIndex = (updateCount / 1) % ScarletPulseGlow.size
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