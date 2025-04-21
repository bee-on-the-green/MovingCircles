package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare2(
    var matrix: Array<Array<MatrixCell2>>,
    private val MatrixLength: Int = 102,
    private val MatrixHeight: Int = MatrixLength * 42 / 100,
    val sleepTime: Long = 200,
    val lengthRectangle: Int = 15,
    val widthRectangles: Int = 1,
    val breakPoint: Int = 30,
    val poolOfChar: Array<Char> = arrayOf('0', '0', '1'),  // Simplified primary chars
    val poolOfChar2: Array<Char> = arrayOf('0', '0', '0') //('1', '0', '0', '0')  // Simplified secondary chars  ('·', '.')
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




/*
    private fun updateMatrix() {
        val (randomX, randomY) = selectRandomCoordinate()


        if (calculateCharacterPercentage(matrix, poolOfChar) > breakPoint) {



            if (updateCount % 2 == 0) {


                val rectangleLength = 20
                val rectangleWidth = 1


                // Even count - horizontal rectangle
                drawRectangle(randomX, randomY, rectangleLength, rectangleWidth, poolOfChar, MatrixGreen1)
            } else {

                val rectangleLength = 1
                val rectangleWidth = 20

                // Odd count - vertical rectangle (swap dimensions)
                drawRectangle(randomX, randomY, rectangleWidth, rectangleLength, poolOfChar, MatrixRed4)
            }
        } else {




            if (updateCount % 2 == 0) {
                // Even count - horizontal rectangle

                val rectangleLength = 20
                val rectangleWidth = 1

                drawRectangle(randomX, randomY, rectangleLength, rectangleWidth, poolOfChar2, Gray50)
            } else {

                val rectangleLength = 1
                val rectangleWidth = 20

                // Odd count - vertical rectangle (swap dimensions)
                drawRectangle(randomX, randomY, rectangleWidth, rectangleLength, poolOfChar2, Gray100)
            }
        }
        updateCount++
    }


*/











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