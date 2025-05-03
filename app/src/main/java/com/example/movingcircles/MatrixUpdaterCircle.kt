package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import com.example.movingcircles.ui.theme.Pink700
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterCircle(
    var matrix: Array<Array<MatrixCell>>, // Changed to MatrixCell type
    val sleepTime: Long = 8,
    val diameterToUseC: Int = 5,
    val breakPoint: Int = 27,
    val poolOfChar: Array<Char> = arrayOf('Ͼ', 'Ͽ') ,  // Ͼ Ͽ ᴑ ᴏ ॰ ᴑ ◯ Ο ο О • ॰ ᐤ ° ᐤ ൦ Ჿ ('Ͼ', 'Ͽ') ('◯', '◯')
    val poolOfChar2: Array<Char> = arrayOf('.', '·') // ('.', '·')
) {

    private val Pink500 = Color(0xFFE91E63)

    private val Orange900 = Color(0xFFE65100)
    private val Orange850 = Color(0xFFD84300)  // Custom (between 800-900)
    private val Orange700 = Color(0xFFF57C00)  // Material Orange 700 (different hue)
    private val Orange750 = Color(0xFFEF6C00)  // Custom (between 700-800)



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
    //private val Violet200 = Color(0xFFCE93D8)
    //private val Violet300 = Color(0xFFBA68C8)




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






    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating(onMatrixUpdated: (Array<Array<MatrixCell>>, Double) -> Unit) {
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
            drawCircle(matrix, myRandomX, myRandomY, diameterToUseC, poolOfChar, Red800)  // was Red750
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUseC, poolOfChar2, Pink650) // was Pink600
            }
        } else {
            // Odd count - use alternate colors
            drawCircle(matrix, myRandomX, myRandomY, diameterToUseC, poolOfChar, Orange900)  // was Orange900
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUseC, poolOfChar2, Pink850)  // was Pink800
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
        matrix: Array<Array<MatrixCell>>,
        centerX: Int,
        centerY: Int,
        diameter: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val radius = diameter / 1
        val yStart = maxOf(centerY - radius, 0)
        val yEnd = minOf(centerY + radius, matrix.size - 1)
        val xStart = maxOf(centerX - radius, 0)
        val xEnd = minOf(centerX + radius, if (matrix.isNotEmpty() && matrix[0].isNotEmpty()) matrix[0].size - 1 else 0)

        for (y in yStart..yEnd) {
            for (x in xStart..xEnd) {
                val aspectRatio = 2.3
                val dx = x - centerX
                val dy = (y - centerY) * aspectRatio
                if (dx * dx + dy * dy <= radius * radius) {
                    val randomChar = poolOfChar[Random.nextInt(poolOfChar.size)]
                    matrix[y][x] = MatrixCell(randomChar, color)
                }
            }
        }
    }

    private fun calculateCharacterPercentage(
        matrix: Array<Array<MatrixCell>>,
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