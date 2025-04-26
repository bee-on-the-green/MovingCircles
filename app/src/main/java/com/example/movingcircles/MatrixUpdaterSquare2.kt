package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare2(
    var matrix: Array<Array<MatrixCell2>>,
    private val MatrixLength: Int = 90,
    private val MatrixHeight: Int = MatrixLength * 58 / 100,
    val sleepTime: Long = 2,
    val lengthRectangle: Int = 15,
    val widthRectangles: Int = 1,
    val breakPoint: Int = 70,
    val poolOfChar: Array<Char> = arrayOf('Ͼ', 'Ͽ'),  // Simplified primary chars ੦ⵙ◯೦ ('😨', '😲', '😵') ⣏ ⣙ ⣫ ⣧ ⣫ ⣹
    val poolOfChar2: Array<Char> = arrayOf('·', '.') // ০ is no, ੦ is no, ൦ is no,   ('·', '.')
) {
    private var isRunning = false
    private var updateCount: Int = 0

    // Define some colors similar to the Circles2 implementation
    private val MatrixRed1 = Color(0xFFFF0000)    // Pure RGB red (classic Matrix code red)
    private val MatrixRed2 = Color(0xFFFF0A0A)    // Laser red (intense glow)
    private val MatrixRed3 = Color(0xFFFF1A1A)    // Blood red (slightly deeper)
    private val MatrixRed4 = Color(0xFFFF2D00)    // Electric red (orange tint)
    private val MatrixRed5 = Color(0xFFFF1744)    // Neon pink-red (cyberpunk style)
    private val MatrixRed6 = Color(0xFFE91E63)    // Hot pink-red (Material-like but bright)

    private val MatrixGreen1 = Color(0xFF00FF00)     // Pure Matrix green (classic terminal)
    private val MatrixGreen2 = Color(0xFF00FF41)     // Slightly teal-tinged (glowing)
    private val MatrixGreen3 = Color(0xFF00FF66)     // Brighter, more digital)
    private val MatrixGreen4 = Color(0xFF0AFF0A)     // Electric lime green)
    private val MatrixGreen5 = Color(0xFF4AFF4A)     // Softer but still bright)


    private val Gray50  = Color(0xFFFAFAFA)   // Almost white (background tint)
    private val Gray100 = Color(0xFFF5F5F5)   // Slightly warmer light gray
    private val Gray150 = Color(0xFFEEEEEE)   // Custom (between 100-200)
    private val Gray200 = Color(0xFFE0E0E0)   // Material Gray 200 (neutral)
    private val Gray250 = Color(0xFFD6D6D6)   // Custom (between 200-300)
    private val Gray300 = Color(0xFFBDBDBD)   // Material Gray 300 (visible but soft)



    // Soft Pink-Tinted Grays
    private val GrayPink50 = Color(0xFFFFF7FA)  // Very light pink-gray (blush white)
    private val GrayPink100 = Color(0xFFF5EBF0)
    private val GrayPink200 = Color(0xFFE8D6DE)
    private val GrayPink300 = Color(0xFFDBBDCA)
    private val GrayPink400 = Color(0xFFC79FAF)

    // Deeper Pink Grays
    private val GrayPink500 = Color(0xFFB38295)
    private val GrayPink600 = Color(0xFF9E6A81)

    private val GrayPink800 = Color(0xFF6B4356)
    private val GrayPink900 = Color(0xFF563246)



    // Purple-Tinted Grays
    private val GrayPurple50 = Color(0xFFF8F7FF)  // Very light lavender gray
    private val GrayPurple100 = Color(0xFFEDEBF5)
    private val GrayPurple200 = Color(0xFFD9D6E8)
    private val GrayPurple300 = Color(0xFFC2BDDB)
    private val GrayPurple400 = Color(0xFFA59FC7)

    // Deeper Purple Grays
    private val GrayPurple500 = Color(0xFF8A82B3)
    private val GrayPurple600 = Color(0xFF726A9E)
    private val GrayPurple700 = Color(0xFF5D5585)
    private val GrayPurple800 = Color(0xFF48436B)
    private val GrayPurple900 = Color(0xFF363256)


    private val Red900 = Color(0xFFB71C1C)


    private val Orange850 = Color(0xFFD84300)  // Custom (between 800-900)
    private val Orange700 = Color(0xFFF57C00)  // Material Orange 700 (different hue)
    private val Orange750 = Color(0xFFEF6C00)  // Custom (between 700-800)
    private val Orange800 = Color(0xFFEF6C00)  // Burnt orange

    // Darker versions (approximately 40-50% darker)
    private val DarkRed900 = Color(0xFF7A1212)  // Darker red
    private val DarkOrange850 = Color(0xFF902D00) // Darker orange
    private val VividOrange = Color(0xFFFF5200)  // Glowing orange
    private val SunburstOrange = Color(0xFFFF7900)  // Warm, radiant
    private val FireOrange = Color(0xFFFF5E00)   // Between orange and red



    // Near-black but still slightly red/orange
    private val ExtremelyDarkRed = Color(0xFF300505)     // ~80% darker than original
    private val ExtremelyDarkOrange = Color(0xFF3D1000)  // ~80% darker than original

    // Slightly more visible (but still very dark)
    private val SuperDarkRed = Color(0xFF450808)        // ~75% darker
    private val SuperDarkOrange = Color(0xFF551900)     // ~75% darker

    // For a slightly more visible but still very dark purple (~70% darker)
    private val SuperDarkPurple = Color(0xFF2D0A36)     // Darkened from #FF9C27B0
    private val SuperDarkDeepPurple = Color(0xFF240D2E)  // Darkened from #FF7B1FA2

    // Alternative (slightly more muted)
    private val SuperDarkPurpleAlt = Color(0xFF3A0B3A)   // More balanced darkness


    // Darkened from #FFE91E63 (~70% darker)
    private val SuperDarkPink = Color(0xFF4A061D)

    // Darkened from #FFC2185B (~70% darker)
    private val SuperDarkDeepPink = Color(0xFF3D0521)

    // Alternative (slightly more muted)
    private val SuperDarkPinkAlt = Color(0xFF550D28)  // A bit more visible
    private val GrayPink700 = Color(0xFF806A7F)




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









    /*

        private fun updateMatrix() {
            val (randomX, randomY) = selectRandomCoordinate()
            val lengthOfRectangle = if (updateCount % 2 == 0) 20 else 1
            val widthOfRectangle = if (updateCount % 2 == 0) 1 else 20

            if (updateCount % 2 == 0) {
                drawRectangle(randomX, randomY, lengthOfRectangle, widthOfRectangle, poolOfChar, MatrixRed4)
                if (calculateCharacterPercentage(matrix, poolOfChar) > breakPoint) {
                    drawRectangle(randomX, randomY, lengthOfRectangle, widthOfRectangle, poolOfChar2, Gray50)
                }
            } else {
                drawRectangle(randomX, randomY, lengthOfRectangle, widthOfRectangle, poolOfChar, MatrixGreen1)
                if (calculateCharacterPercentage(matrix, poolOfChar) > breakPoint) {
                    drawRectangle(randomX, randomY, lengthOfRectangle, widthOfRectangle, poolOfChar2, Gray50)
                }
            }
            updateCount++
        }

    */



    private fun updateMatrix() {
        if (calculateCharacterPercentage(matrix, poolOfChar) < breakPoint) {
            if (updateCount % 2 == 0) {
                // Green vertical (width=1, length=20)
                val greenLength = 12
                val greenWidth = 11
                val (greenX, greenY) = selectRandomCoordinate()
                drawRectangle(greenX, greenY, greenWidth, greenLength, poolOfChar, Orange700)

                // Red horizontal (width=20, length=1)
                val redLength = 14
                val redWidth = 12
                val (redX, redY) = selectRandomCoordinate()
                drawRectangle(redX, redY, redWidth, redLength, poolOfChar,MatrixRed5)
            }
        } else {
            if (updateCount % 2 != 0) {
                // Gray50 vertical (width=1, length=20)
                val gray50Length = 1
                val gray50Width = 2
                val (gray50X, gray50Y) = selectRandomCoordinate()
                drawRectangle(gray50X, gray50Y, gray50Width, gray50Length, poolOfChar2, GrayPink500)

                // Gray100 horizontal (width=20, length=1)
                val gray100Length = 1
                val gray100Width = 2
                val (gray100X, gray100Y) = selectRandomCoordinate()
                drawRectangle(gray100X, gray100Y, gray100Width, gray100Length, poolOfChar2, GrayPink600)
            }
        }
        updateCount++
    }










    private fun selectRandomCoordinate(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLength),
            Random.nextInt(0, MatrixHeight)
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

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeight - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLength - 1)) {
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