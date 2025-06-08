package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterCircle5(
    var matrix: Array<Array<MatrixCell2>>,
    val sleepTimeC5: Long = 28,
    val diameterToUseC5: Int,
    val breakPoint: Int = 86,  // was 86
    val poolOfChar: Array<Char> = arrayOf('·', '.', '\'', '·', '.', '.', '\''),
    val poolOfChar2: Array<Char> = arrayOf('°', '²', ',', ',', '•')
) {
    private val Violet200 = Color(0xFFCE93D8)
    private val Violet300 = Color(0xFFBA68C8)
    private val Violet150 = Color(0xFFE1BEE7)    // Very light violet
    private val Violet100 = Color(0xFFF3E5F5)    // Almost white with violet tint
    private val Violet50 = Color(0xFFF9F0FA)     // Barely perceptible violet
    private val VioletMoonlight = Color(0xFFE8DAEF)  // Soft moonlight violet
    private val VioletMist = Color(0xFFEDE7F6)    // Misty white-violet

    // Medium Pinks (buttons, accents)
    val Pink200 = Color(0xFFFF8A9D)  // Cotton candy
    val Pink300 = Color(0xFFFF6B8B)  // Bright pink
    val Pink400 = Color(0xFFF75990)  // Vibrant pink
    val Pink500 = Color(0xFFE91E63)  // Classic Material pink (your reference)
    val Pink600 = Color(0xFFD81B60)  // Rich pink

    // Deep/Dark Pinks (text, bold accents)
    val Pink700 = Color(0xFFC2185B)  // Raspberry
    val Pink800 = Color(0xFFAD1457)  // Berry



    private val Orange800 = Color(0xFFEF6C00)
    private val Orange850 = Color(0xFFE65100)    // Darker than 800
    private val Orange900 = Color(0xFFD84315)    // Deep burnt orange
    private val Orange950 = Color(0xFFBF360C)    // Very dark orange-brown
    private val Orange1000 = Color(0xFF9E2C0A)   // Near-black orange
    private val OrangeBlack = Color(0xFF7F1D08)  // Maximum darkness while keeping orange hue
    private val Red900 = Color(0xFFB71C1C)
    // Pure white to light gray progression
    private val PureWhite = Color(0xFFFFFFFF)      // Pure white
    private val White95 = Color(0xFFF2F2F2)        // 95% white (very slight gray)
    private val White90 = Color(0xFFE6E6E6)        // 90% white (lightest gray)
    private val White85 = Color(0xFFD9D9D9)        // 85% white (light gray)
    private val White80 = Color(0xFFCCCCCC)        // 80% white (medium-light gray)

    // Progressively darker grays (continuing from White80)
    private val Gray70 = Color(0xFFB3B3B3)       // Slightly darker than White80
    private val Gray60 = Color(0xFF999999)       // Medium-light gray
    private val Gray50 = Color(0xFF808080)       // True middle gray
    private val Gray40 = Color(0xFF666666)       // Medium-dark gray
    private val Gray30 = Color(0xFF4D4D4D)       // Dark gray
    private val Gray20 = Color(0xFF333333)       // Very dark gray

    // Near-black colors (darkest to lightest)
    private val Black1000 = Color(0xFF010101)       // Closest to pure black without being #000000
    private val Black950 = Color(0xFF0A0A0A)        // Barely distinguishable from pure black
    private val Black900 = Color(0xFF121212)        // Standard "dark mode" black
    private val Black850 = Color(0xFF1A1A1A)        // Slightly elevated black
    private val Black800 = Color(0xFF212121)        // Dark charcoal

    // Near-black with subtle undertones
    private val BlackCool = Color(0xFF050A0E)       // Blue-tinted near-black
    private val BlackWarm = Color(0xFF0A0808)       // Red-brown tinted near-black
    private val BlackViolet = Color(0xFF0D0714)     // Violet-tinged near-black
    private val BlackGreen = Color(0xFF070D0A)      // Greenish near-black



    private val Yellow50 = Color(0xFFFFFF99)    // Very pale yellow
    private val Yellow100 = Color(0xFFFFFF66)   // Light pastel yellow
    private val Yellow200 = Color(0xFFFFFF00)   // Pure digital yellow
    private val Yellow300 = Color(0xFFFFF176)   // Soft warm yellow
    private val Yellow400 = Color(0xFFFFEB3B)   // Bright lemon yellow
    private val Yellow500 = Color(0xFFFFD600)   // Vivid golden yellow
    private val Yellow600 = Color(0xFFFFC107)   // Amber yellow (Material Design)
    private val Yellow700 = Color(0xFFFFB300)   // Rich golden yellow
    private val Yellow800 = Color(0xFFFFA000)   // Deep vibrant yellow
    private val Yellow900 = Color(0xFFFFA000)   // Orange-tinted yellow. Changed to Yellow900 instead of Yellow950 for consistency
    private val Yellow950 = Color(0xFFFF6F00)   // Bright amber. Changed to Yellow950 instead of Yellow1000 for consistency
    private val Yellow1000 = Color(0xFFFF5722)  // Fiery orange-yellow. Changed to Yellow1000 instead of Yellow1000 for consistency


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
                Thread.sleep(sleepTimeC5)
            }
        }
    }

    fun stopUpdating() {
        isRunning = false
    }



    private fun updateMatrix() {
        val (myRandomX, myRandomY) = selectRandomCoordinate()

        if (updateCount % 2 == 0) {
            drawCircle(matrix, myRandomX, myRandomY, diameterToUseC5, poolOfChar, Black900)
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUseC5, poolOfChar2, Pink300)
            }
        } else {
            drawCircle(matrix, myRandomX, myRandomY, diameterToUseC5, poolOfChar, Black900)
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUseC5, poolOfChar2, Pink500)
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
                val aspectRatio = 1.9
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
