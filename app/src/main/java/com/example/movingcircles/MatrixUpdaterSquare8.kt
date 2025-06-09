package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare8(
    var matrix: Array<CharArray>,
    var colorMatrix: Array<Array<Color>>
) {
    private val MatrixLengthS8: Int = matrix[0].size
    private val MatrixHeightS8: Int = matrix.size
    val sleepTimeS8: Long = 10
    val breakPointS8: Int = 80  // was  12


    val poolOfChar2: Array<Char> = arrayOf('○', '°', '°', '○', ')', '(') // ('○', '°', '°', 'o', '°', '°', 'O')


    val poolOfChar: Array<Char> = arrayOf(
        '§', '¶', '¬', '¢', '£',
        '¥', '®', '©', 'ª', 'k',
        'É', 'Ê', 'Ë', 'Ì', 'Í',
        'K', 'L', 'M', 'N',
        'u', 'v', 'w', 'x', 'y', 'z',
        '!', '@', '#', '$', '%',
        '^', '&', '*', '9', '3',
        ']', '{',
        ';', ':', '\'', '"', ',',
        '.', '<', '>', '/', '?'
    )


    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating8(onMatrixUpdated: (Array<CharArray>, Array<Array<Color>>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix8()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val colorMatrixCopy = colorMatrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage8(matrixCopy, poolOfChar)
                onMatrixUpdated(matrixCopy, colorMatrixCopy, switchValue)
                Thread.sleep(sleepTimeS8)
            }
        }
    }



    fun stopUpdating8() {
        isRunning = false
    }


    val Red900 = Color(0xFFB71C1C)
    val Red950 = Color(0xFFA20E0E)
    val Red1000 = Color(0xFF8E0000)

    private val ElectricMagenta = Color(0xFFFF00FF)
    private val RadioactivePink = Color(0xFFFF00AA)
    private val CyberPink = Color(0xFFFF0099)
    private val ScreamingPink = Color(0xFFFF00CC)

    private var counter8 = 0

    private fun updateMatrix8() {


        val (randomX, randomY) = selectRandomCoordinate8()

        val (currentLength, currentWidth) = if (counter8 % 2 == 0) {

            2 to 2  // 2 by 2
        } else {

            2 to 2  // 2 by 2
        }

        // Randomly choose between 1 and 2
        val randomChoice = Random.nextInt(1, 3) // Generates 1 or 2

        val selectedColor = if (randomChoice == 1) {
            Color(0xFFA20E0E)
        } else {
            Color(0xFFB71C1C)
        }

        drawRectangle8(
            randomX,
            randomY,
            currentWidth,
            currentLength,
            poolOfChar,
            selectedColor
        )

        // selectedColor2 now uses the same randomChoice
        val selectedColor2 = if (randomChoice == 1) {
            Color(0xFFD7A3E1)  // 0xFFD7A3E1  0xFFFF00EE
        } else {
            Color(0xFFD7A3E1)  // 0xFFC885D2   0xFFFF00EE
        }

        if (calculateCharacterPercentage8(matrix, poolOfChar) > breakPointS8) {
            drawRectangle8(
                randomX,
                randomY,
                currentWidth,
                currentLength,
                poolOfChar2,
                selectedColor2
            )
        }
        counter8++

    }





    private fun selectRandomCoordinate8(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLengthS8),
            Random.nextInt(0, MatrixHeightS8)
        )
    }

    private fun drawRectangle8(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS8 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS8 - 1)) {
                matrix[y][x] = poolOfChar.random()
                colorMatrix[y][x] = color
            }
        }
    }

    private fun calculateCharacterPercentage8(
        matrix: Array<CharArray>,
        poolOfChar: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { char -> if (char in poolOfChar) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}