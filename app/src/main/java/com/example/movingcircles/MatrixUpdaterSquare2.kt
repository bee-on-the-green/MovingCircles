package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random
import com.example.movingcircles.ui.theme.PureWhite

class MatrixUpdaterSquare2(
    var matrix: Array<Array<MatrixCell2>>,
    private val MatrixLength: Int = 80,
    private val MatrixHeight: Int = MatrixLength * 55 / 100,
    val sleepTime: Long = 20,
    val lengthRectangle: Int = 15,
    val widthRectangles: Int = 1,
    val breakPoint: Int = 30,
    val poolOfChar: Array<Char> = arrayOf('o', 'o', '0'),  // Other char //Simplified primary chars ੦ⵙ◯೦  ⣏ ⣙ ⣫ ⣧ ⣫ ⣹ '⠕', '⢺'
    val poolOfChar2: Array<Char> = arrayOf('°', 'O') // other chars ০ᐤ൦৹ ('·', '.')  '⠣', '⢑'
) {
    private var isRunning = false
    private var updateCount: Int = 0


    private val Gray50  = Color(0xFFFAFAFA)   // Almost white (background tint)
    private val Gray100 = Color(0xFFF5F5F5)   // Slightly warmer light gray
    private val Gray150 = Color(0xFFEEEEEE)   // Custom (between 100-200)
    private val Gray200 = Color(0xFFE0E0E0)   // Material Gray 200 (neutral)
    private val Gray250 = Color(0xFFD6D6D6)   // Custom (between 200-300)
    private val Gray300 = Color(0xFFBDBDBD)   // Material Gray 300 (visible but soft)
    private val Gray400 = Color(0xFFBDBDBD)  // Medium light gray
    private val Gray500 = Color(0xFF9E9E9E)  // Medium gray
    private val Gray600 = Color(0xFF757575)  // Medium dark gray
    private val Gray700 = Color(0xFF616161)  // Dark gray
    private val Gray800 = Color(0xFF424242)  // Very dark gray
    private val Gray900 = Color(0xFF212121)  // Almost black
    private val Orange800 = Color(0xFFEF6C00)  // Burnt orange

    private val Pink650 = Color(0xFFAD1457)   // Darker (between 600-700)

    private val Pink500 = Color(0xFFE91E63)

    private val Orange900 = Color(0xFFE65100)
    private val Orange850 = Color(0xFFD84300)  // Custom (between 800-900)
    private val Orange700 = Color(0xFFF57C00)  // Material Orange 700 (different hue)
    private val Orange750 = Color(0xFFEF6C00)  // Custom (between 700-800)



    private val Red850 = Color(0xFFA01818)  // Custom (between 800-900)
    private val Red800 = Color(0xFFC62828)

    private val PinkA700 = Color(0xFFC51162)        // Deep pink (Material)
    private val PinkA400 = Color(0xFFF50057)        // Bright pink (Material)
    private val PinkA200 = Color(0xFFFF4081)        // Vivid pink (Material)
    // Soft Pink-Tinted Grays
    private val GrayPink50 = Color(0xFFFFF7FA)  // Very light pink-gray (blush white)
    private val GrayPink100 = Color(0xFFF5EBF0)
    private val GrayPink200 = Color(0xFFE8D6DE)
    private val GrayPink300 = Color(0xFFDBBDCA)
    private val GrayPink400 = Color(0xFFC79FAF)

    // Deeper Pink Grays
    private val GrayPink500 = Color(0xFFB38295)
    private val GrayPink600 = Color(0xFF9E6A81)
    private val GrayPink700 = Color(0xFF85556C)
    private val GrayPink800 = Color(0xFF6B4356)
    private val GrayPink900 = Color(0xFF563246)



    // Soft Gray-Purples
    private val GrayPurple50 = Color(0xFFFAFAFF)  // Very light with purple tint
    private val GrayPurple100 = Color(0xFFF0EFF5)
    private val GrayPurple200 = Color(0xFFE0DEE8)
    private val GrayPurple300 = Color(0xFFC5C2D6)
    private val GrayPurple400 = Color(0xFFA7A3C2)

    // Medium Gray-Violets
    private val GrayViolet500 = Color(0xFF8A85A8)
    private val GrayViolet600 = Color(0xFF6E698D)
    private val GrayViolet700 = Color(0xFF565273)

    // Deeper Gray-Purples


    // Muted Violet-Grays
    private val VioletGray50 = Color(0xFFF8F7FB)
    private val VioletGray100 = Color(0xFFE9E8ED)
    private val VioletGray200 = Color(0xFFD4D2DD)
    private val VioletGray300 = Color(0xFFB8B6C5)

    // Dusty Purples
    private val DustyPurple400 = Color(0xFF9D99B1)
    private val DustyPurple500 = Color(0xFF7F7A96)
    private val DustyPurple600 = Color(0xFF66617D)
    // Deeper Purple Grays
    private val GrayPurple500 = Color(0xFF8A82B3)
    private val GrayPurple600 = Color(0xFF726A9E)
    private val GrayPurple700 = Color(0xFF5D5585)
    private val GrayPurple800 = Color(0xFF48436B)
    private val GrayPurple900 = Color(0xFF363256)






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
            if (updateCount % 2 == 0) {
                // Green vertical (width=1, length=20)
                val greenLength = 2
                val greenWidth = 2
                val (greenX, greenY) = selectRandomCoordinate()
                drawRectangle(greenX, greenY, greenWidth, greenLength, poolOfChar, Orange800)

                // Red horizontal (width=20, length=1)
                val redLength = 2
                val redWidth = 2
                val (redX, redY) = selectRandomCoordinate()
                drawRectangle(redX, redY, redWidth, redLength, poolOfChar, Red800)
            }
        } else {
            if (updateCount % 2 != 0) {
                // Gray50 vertical (width=1, length=20)
                val gray50Length = 2
                val gray50Width = 2
                val (gray50X, gray50Y) = selectRandomCoordinate()
                drawRectangle(gray50X, gray50Y, gray50Width, gray50Length, poolOfChar2, DustyPurple500)

                // Gray100 horizontal (width=20, length=1)
                val gray100Length = 2
                val gray100Width = 2
                val (gray100X, gray100Y) = selectRandomCoordinate()
                drawRectangle(gray100X, gray100Y, gray100Width, gray100Length, poolOfChar2, VioletGray300)
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