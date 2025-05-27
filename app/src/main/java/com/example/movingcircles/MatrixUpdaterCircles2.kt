package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterCircles2(
    var matrix: Array<Array<MatrixCell2>>,
    val sleepTime: Long = 20,
    val diameterToUseC2: Int,
    val breakPoint: Int = 85,
    val poolOfChar: Array<Char> = arrayOf('Ͽ', 'Ͼ'),  // was 'Ͽ', 'Ͼ'),  '⊙', '⊚'
    val poolOfChar2: Array<Char> = arrayOf('(', ')')  // ∍ '⊕', '⊖'
) {
    private val Violet200 = Color(0xFFCE93D8)

    private val Violet225 = Color(0xFFC885D2)  // Between 200-300
    private val Violet250 = Color(0xFFC277CC)  // Closer to 300

    private val Violet300 = Color(0xFFBA68C8)

    private val Orange800 = Color(0xFFEF6C00)
    private val Orange750 = Color(0xFFED5E0E)  // Warmer, less intense than 800
    private val Orange770 = Color(0xFFEF6507)  // Balanced midpoint




    private val Red650 = Color(0xFFDF3434)  // Slightly darker than 600
    private val Red680 = Color(0xFFDA2F2F)  // Almost at 700



    private val Violet225_dark1 = Color(0xFFB57AC0)  // NEW COLORS
    private val Violet225_dark2 = Color(0xFFA370AD)

    private val Violet300_dark1 = Color(0xFFA85CB5)
    private val Violet300_dark2 = Color(0xFF9651A2)

    private val Orange750_dark1 = Color(0xFFD4550D)
    private val Orange750_dark2 = Color(0xFFBC4B0B)

    private val Red600_dark1 = Color(0xFFCC3330)
    private val Red600_dark2 = Color(0xFFB32D2A)




    private val Red900 = Color(0xFFB71C1C)
    private val Red800 = Color(0xFFC62828)   // Darker than B71C1C but still bright
    private val Red700 = Color(0xFFD32F2F)   // Slightly lighter
    private val Red600 = Color(0xFFE53935)   // Brighter red



    private val Red500 = Color(0xFFF44336)   // Classic Material Red
    private val Red400 = Color(0xFFEF5350)   // Lighter, softer red
    private val Red300 = Color(0xFFE57373)   // Pastel-like red
    private val Red200 = Color(0xFFEF9A9A)   // Very light red
    private val Red100 = Color(0xFFFFCDD2)   // Almost pinkish
    private val Red50 = Color(0xFFFFEBEE)    // Lightest (pinkish-white)

    private val Violet400 = Color(0xFFAB47BC)   // Slightly darker than Violet300
    private val Violet500 = Color(0xFF9C27B0)   // Classic Material Purple
    private val Violet600 = Color(0xFF8E24AA)   // Rich violet
    private val Violet700 = Color(0xFF7B1FA2)   // Deep violet
    private val Violet800 = Color(0xFF6A1B9A)   // Very dark violet
    private val Violet900 = Color(0xFF4A148C)   // Deepest violet (almost purple-black)
    // Pure blacks (cool/warm undertones)
    private val BlackPure = Color(0xFF000000)       // True black
    private val BlackCool = Color(0xFF050A0E)       // Slightly blue-tinted
    private val BlackWarm = Color(0xFF0A0808)       // Subtle red-brown undertone

    // Near-black violets (progressively darker)
    private val Violet950 = Color(0xFF3D0C6E)       // Slightly darker than Violet900
    private val Violet975 = Color(0xFF2A094D)       // Almost black with violet tint
    private val Violet1000 = Color(0xFF1A0638)      // Deepest near-black violet


    // Light Violets
    private val LavenderMist = Color(0xFFE6C3ED)
    private val PastelLilac = Color(0xFFD9ABE3)

    // Light Purples
    private val SoftOrchid = Color(0xFFD7A3E1)
    private val PaleAmethyst = Color(0xFFC48FD6)

    // Light Plums
    private val DustyPlum = Color(0xFFC98FD0)
    private val MauveTwilight = Color(0xFFB57FBD)



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
            drawCircle(matrix, myRandomX, myRandomY, diameterToUseC2, poolOfChar, Violet225)  // was  Red600_dark1)
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUseC2, poolOfChar2, Red600_dark1) //  was Violet300_dark1
            }
        } else {
            drawCircle(matrix, myRandomX, myRandomY, diameterToUseC2, poolOfChar, SoftOrchid)  // Orange750_dark1
            val mainCharPercentageAtCurrentTime = calculateCharacterPercentage(matrix, poolOfChar)
            if (mainCharPercentageAtCurrentTime > breakPoint) {
                drawCircle(matrix, myRandomX, myRandomY, diameterToUseC2, poolOfChar2, Orange750_dark1) // Violet300_dark1
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
                val aspectRatio = 0.9  // was 0.9
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