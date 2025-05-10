package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare2(
    var matrix: Array<Array<MatrixCell2>>,
    val MatrixLengthS2: Int = MatrixInitializerSquare2().MatrixLengthS2,  // Reference from Initializer
    val MatrixHeightS2: Int = MatrixInitializerSquare2().MatrixHeightS2,  // Reference from Initializer
    val sleepTime: Long = 30,
    val breakPoint: Int = 30,
    val poolOfChar: Array<Char> = arrayOf('Ͽ', 'Ͼ'),
    val poolOfChar2: Array<Char> = arrayOf('0', '1')
) {
    private var isRunning = false
    private var updateCount: Int = 0

    // Colors (kept as-is)
    private val MatrixRed5 = Color(0xFFFF1744)
    private val MatrixGreen1 = Color(0xFF00FF00)
    private val Orange700 = Color(0xFFF57C00)
    private val PureBlack = Color(0xFF000000)


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

    private val Red800 = Color(0xFFC62828)
    private val Red750 = Color(0xFFD32F2F)  // Custom (between 700-800)
    private val Red700 = Color(0xFFD32F2F)
    private val Red650 = Color(0xFFE53935)  // Custom (between 600-700)
    private val Red600 = Color(0xFFE53935)
    private val Red550 = Color(0xFFEF5350)  // Custom (between 500-600)
    private val Red500 = Color(0xFFF44336)
    private val Red450 = Color(0xFFEF5350)  // Custom (between 400-500)
    private val Red400 = Color(0xFFEF5350)
    private val Red350 = Color(0xFFE57373)  // Custom (between 300-400)
    private val Red300 = Color(0xFFE57373)





    private val PinkNeon = Color(0xFFFF00FF)       // Pure magenta (extremely bright)
    private val PinkHot = Color(0xFFFF0077)         // Intense hot pink
    private val PinkElectric = Color(0xFFFF00AA)    // Electric pink
    private val PinkFuchsia = Color(0xFFFF0088)     // Deep fuchsia

    // Material Design Pinks (Bright variants)
    private val PinkA700 = Color(0xFFC51162)        // Deep pink (Material)
    private val PinkA400 = Color(0xFFF50057)        // Bright pink (Material)
    private val PinkA200 = Color(0xFFFF4081)        // Vivid pink (Material)

    // Custom Bright Pinks (Between Material shades)
    private val PinkA650 = Color(0xFFE91E63)        // Custom (between A700-A400)
    private val PinkA350 = Color(0xFFFF5C8D)        // Custom (between A400-A200)

    // Light but still bright pinks (Less aggressive but still vivid)


    private val Pink350 = Color(0xFFF06292)         // Custom (between 300-400)
    private val Pink300 = Color(0xFFF06292)         // Lighter yet still vibrant

    // Lighter Bright Pinks (Still vibrant but softer)
    private val Pink400 = Color(0xFFEC407A)   // Material Pink 400
    private val Pink450 = Color(0xFFF06292)   // Brighter (between 400-500)

    private val Pink550 = Color(0xFFD81B60)   // Deeper (between 500-600)
    private val Pink600 = Color(0xFFC2185B)   // Material Pink 600
    private val Pink650 = Color(0xFFAD1457)   // Darker (between 600-700)
    private val Pink700 = Color(0xFFAD1457)   // Material Pink 700
    private val Pink750 = Color(0xFF9C125A)   // Custom (between 700-800)
    private val Pink800 = Color(0xFF880E4F)   // Material Pink 800
    private val Pink850 = Color(0xFF7A0D47)   // Custom (between 800-900)
    private val Pink900 = Color(0xFF4A0A2A)   // Material Pink 900 (deep burgundy-pink)
    private val Orange800 = Color(0xFFEF6C00)  // Burnt orange  new one
    private val Red900 = Color(0xFFB71C1C)
    private val Violet200 = Color(0xFFCE93D8)  // new one
    private val Violet300 = Color(0xFFBA68C8)



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



        if (calculateCharacterPercentage(matrix, poolOfChar) < breakPoint) {

                var (randomX, randomY) = selectRandomCoordinate()

                drawRectangle(randomX, randomY, 7, 4, poolOfChar, PinkNeon)  // Vertical
                drawRectangle(randomX, randomY, 2, 2, poolOfChar, Orange700)

            val charPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)

            if (charPercentageAtCurrentTime > breakPoint) {
                drawRectangle(randomX, randomY, 7, 4, poolOfChar, MatrixRed5) // was 400


                drawRectangle(randomX, randomY, 2, 2, poolOfChar, Orange800)  // Horizontal
            }

        }
        updateCount++
    }

    private fun selectRandomCoordinate(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLengthS2),
            Random.nextInt(0, MatrixHeightS2)
        )
    }







    private fun drawRectangle(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2
        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS2 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS2 - 1)) {
                matrix[y][x] = MatrixCell2(poolOfChar.random(), color)
            }
        }
    }

    private fun calculateCharacterPercentage(
        matrix: Array<Array<MatrixCell2>>,
        poolOfChar: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { cell -> if (cell.char in poolOfChar) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}