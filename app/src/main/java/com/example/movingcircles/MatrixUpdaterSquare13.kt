package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random


class MatrixUpdaterSquare13(
    var matrix: Array<CharArray>,
    var colorMatrix: Array<Array<Color>>
) {
    private val MatrixLengthS13: Int = matrix[0].size
    private val MatrixHeightS13: Int = matrix.size
    val sleepTimeS13: Long = 60


    val breakPointS13: Int = 75


    val poolOfChar: Array<Char> = arrayOf('○')


    private var isRunning = false
    private var updateCount: Int = 0

    val Amethyst500 = Color(0xFF724785) // Stone-like
    val Magenta200 = Color(0xFF6E3985)  // Electric purple-pink

    private val PumpkinGlow = Color(0xFF9F2C59)
    private val CyberOrange = Color(0xFFB71D59)
    private val FlamingOrange = Color(0xFFB94E78)



    // List of colors for random selection
    private val randomColorsForPool2 = listOf(
        FlamingOrange,    // RadioactivePink
        PumpkinGlow,
        CyberOrange
    )

    suspend fun startUpdating13(onMatrixUpdated: (Array<CharArray>, Array<Array<Color>>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix13()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val colorMatrixCopy = colorMatrix.map { it.clone() }.toTypedArray()
                // Now we are calculating the percentage of a specific color, not characters from a pool
                val switchValue = calculateColorPercentage13(colorMatrixCopy, listOf(Amethyst500, Magenta200))
                onMatrixUpdated(matrixCopy, colorMatrixCopy, switchValue)
                Thread.sleep(sleepTimeS13)
            }
        }
    }


    fun stopUpdating13() {
        isRunning = false
    }




    private fun updateMatrix13() {
        val (randomX, randomY) = selectRandomCoordinate13()

        val currentLength: Int
        val currentWidth: Int


            currentLength = 70
            currentWidth = 1

        drawRectangle13(
            randomX,
            randomY,
            currentWidth,
            currentLength,
            poolOfChar,  // listOf('○')  // was poolOfChar
            listOf(Amethyst500, Magenta200) // Pass a list of colors
        )


        if (calculateColorPercentage13(colorMatrix, listOf(Amethyst500, Magenta200)) > breakPointS13) {
            drawRectangle13(
                randomX,
                randomY,
                currentWidth,
                currentLength,
                poolOfChar,
                randomColorsForPool2 // Pass the list directly
            )
        }

    }


    private fun selectRandomCoordinate13(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLengthS13),
            Random.nextInt(0, MatrixHeightS13)
        )
    }

    private fun drawRectangle13(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>,
        colors: List<Color> // Change the parameter to a list of colors
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2
        val selectedColor = colors.random() // Select a random color from the list

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS13 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS13 - 1)) {
                matrix[y][x] = poolOfChar.random()
                colorMatrix[y][x] = selectedColor // Use the randomly selected color
            }
        }
    }

    // New function to calculate percentage of a specific color
    private fun calculateColorPercentage13(
        colorMatrix: Array<Array<Color>>,
        targetColors: List<Color>
    ): Double {
        var count = 0
        colorMatrix.forEach { row -> row.forEach { color -> if (color in targetColors) count++ } }
        return (count.toDouble() / (colorMatrix.size * colorMatrix[0].size)) * 100
    }
}