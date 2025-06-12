package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare12(
    var matrix: Array<CharArray>,
    var colorMatrix: Array<Array<Color>>
) {
    private val MatrixLengthS12: Int = matrix[0].size
    private val MatrixHeightS12: Int = matrix.size


    val sleepTimeS12: Long = 15

    val breakPointS12: Int = 70


    val poolOfChar2: Array<Char> = arrayOf('○')   //'○'  (•, •)  'Ͼ Ͽ'

    val poolOfChar: Array<Char> = arrayOf('○')


    private var isRunning = false
    private var updateCount: Int = 0



    private val PureDarkPurpleGrayed2 = Color(0xFF70254B) //

    private val Mauve300 = Color(0xFFAF86CC)   // Muted lavender
    private val Grape400 = Color(0xFF8C6DAD)   // Earthy purple
    private val Violet225_dark1 = Color(0xFFB57AC0)
    private val Violet225custom = Color(0xFFC08FC9)




    // List of colors for random selection
    private val randomColorsForPool2 = listOf(
        Violet225custom,    // RadioactivePink
        Mauve300,
        Grape400
    )

    suspend fun startUpdating12(onMatrixUpdated: (Array<CharArray>, Array<Array<Color>>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix12()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val colorMatrixCopy = colorMatrix.map { it.clone() }.toTypedArray()
                // Now we are calculating the percentage of a specific color, not characters from a pool
                val switchValue = calculateColorPercentage12(colorMatrixCopy, PureDarkPurpleGrayed2)
                onMatrixUpdated(matrixCopy, colorMatrixCopy, switchValue)
                Thread.sleep(sleepTimeS12)
            }
        }
    }


    fun stopUpdating12() {
        isRunning = false
    }



    private fun updateMatrix12() {
        val (randomX, randomY) = selectRandomCoordinate12()

        val currentLength = 22
        val currentWidth = 1


        /*
        if (counter12 % 2 == 0) {
            currentLength = 8
            currentWidth = 1
        } else {
            currentLength = 8
            currentWidth = 1
        }
        */
        drawRectangle12(
            randomX,
            randomY,
            currentWidth,
            currentLength,
            poolOfChar,
            PureDarkPurpleGrayed2
        )


        if (calculateColorPercentage12(colorMatrix, PureDarkPurpleGrayed2) > breakPointS12) {
            drawRectangle12(
                randomX,
                randomY,
                currentWidth,
                currentLength,
                poolOfChar2,
                randomColorsForPool2.random() // Random color selected from the list
            )
        }
        //counter12++
    }


    private fun selectRandomCoordinate12(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLengthS12),
            Random.nextInt(0, MatrixHeightS12)
        )
    }

    private fun drawRectangle12(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS12 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS12 - 1)) {
                matrix[y][x] = poolOfChar.random()
                colorMatrix[y][x] = color
            }
        }
    }

    // New function to calculate percentage of a specific color
    private fun calculateColorPercentage12(
        colorMatrix: Array<Array<Color>>,
        targetColor: Color
    ): Double {
        var count = 0
        colorMatrix.forEach { row -> row.forEach { color -> if (color == targetColor) count++ } }
        return (count.toDouble() / (colorMatrix.size * colorMatrix[0].size)) * 100
    }
}
