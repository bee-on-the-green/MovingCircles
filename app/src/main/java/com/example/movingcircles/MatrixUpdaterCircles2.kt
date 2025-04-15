package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import com.example.movingcircles.ui.theme.Pink800
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterCircles2(
    var matrix: Array<Array<MatrixCell2>>,
    val sleepTime: Long = 10,
    val diameterToUse: Int = 7, // was 11
    val breakPoint: Int = 30,
    val poolOfChar: Array<Char> = arrayOf('{', '}'),
    val poolOfChar2: Array<Char> = arrayOf('.', '·')
) {

    private val Pink500 = Color(0xFFE91E63)

    private val Orange900 = Color(0xFFE65100)
    private val Orange850 = Color(0xFFD84300)  // Custom (between 800-900)
    private val Orange700 = Color(0xFFF57C00)  // Material Orange 700 (different hue)
    private val Orange750 = Color(0xFFEF6C00)  // Custom (between 700-800)
    private val Orange800 = Color(0xFFEF6C00)  // Burnt orange
    private val BrightOrange = Color(0xFFFF6D00)  // Pure electric orange
    private val NeonOrange = Color(0xFFFF3D00)   // Intense, slightly reddish
    private val VividOrange = Color(0xFFFF5200)  // Glowing orange
    private val SunburstOrange = Color(0xFFFF7900)  // Warm, radiant
    private val FireOrange = Color(0xFFFF5E00)   // Between orange and red

    private val Red900 = Color(0xFFB71C1C)
    private val Red850 = Color(0xFFA01818)  // Custom (between 800-900)
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
    private val BrightRed = Color(0xFFFF0000)    // Pure RGB red
    private val NeonRed = Color(0xFFFF1744)      // Pinkish bright red
    private val ElectricRed = Color(0xFFFF2D00)  // Fiery red-orange
    private val ScarletRed = Color(0xFFFF2400)   // Intense, slightly orange
    private val BloodRed = Color(0xFFFF1A1A)     // Deep, glowing red

    private val LaserRed = Color(0xFFFF0A0A)    // Almost blinding red
    private val HotPinkRed = Color(0xFFFF006E)   // Red with a pink shift
    private val FlamingOrange = Color(0xFFFF8C00)  // Brighter than classic orange

    private val Violet50 = Color(0xFFF3E5F5)
    private val Violet100 = Color(0xFFE1BEE7)
    private val Violet200 = Color(0xFFCE93D8)
    private val Violet300 = Color(0xFFBA68C8)
    private val Violet400 = Color(0xFFAB47BC)
    private val Violet500 = Color(0xFF9C27B0)  // Primary Violet
    private val Violet600 = Color(0xFF8E24AA)
    private val Violet700 = Color(0xFF7B1FA2)
    private val Violet800 = Color(0xFF6A1B9A)
    private val Violet900 = Color(0xFF4A148C)  // Deep Violet

    // Custom violet shades (darker/muted)
    private val Violet950 = Color(0xFF38006B)
    private val VioletA100 = Color(0xFFEA80FC)  // Bright violet accent
    private val VioletA200 = Color(0xFFE040FB)  // Medium violet accent
    private val VioletA400 = Color(0xFFD500F9)  // Vivid violet accent
    private val VioletA700 = Color(0xFFAA00FF)  // Electric purple

    // Muted/desaturated violets
    private val MutedViolet = Color(0xFF9575CD)
    private val DustyViolet = Color(0xFF7E57C2)
    private val DeepViolet = Color(0xFF673AB7)



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
            drawCircle(matrix, myRandomX, myRandomY, diameterToUse, poolOfChar, Violet300) // orange 800
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUse, poolOfChar2, Red900) // pink 600
            }
        } else {
            // Odd count - use alternate colors
            drawCircle(matrix, myRandomX, myRandomY, diameterToUse, poolOfChar, Violet200)  //
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUse, poolOfChar2, Orange800)  // pink 900
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
                val aspectRatio = 2.4 // was 2.0
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