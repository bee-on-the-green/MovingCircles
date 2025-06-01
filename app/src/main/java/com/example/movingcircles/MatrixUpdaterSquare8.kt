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
    val sleepTimeS8: Long = 9

    val breakPointS8: Int = 91


    val poolOfChar2: Array<Char> = arrayOf('0', '0', '0', '1')


    val poolOfChar: Array<Char> = arrayOf(
        '§', '¶', '¬', '¢', '£',
        '¥', '®', '©', 'ª', 'º',
        'É', 'Ê', 'Ë', 'Ì', 'Í',
        'K', 'L', 'M', 'N',
        'u', 'v', 'w', 'x', 'y', 'z',
        '!', '@', '#', '$', '%',
        '^', '&', '*', '(', ')',
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







    private var counter8 = 0

    private fun updateMatrix8() {


        val (randomX, randomY) = selectRandomCoordinate8()

        val (currentLength, currentWidth) = if (counter8 % 2 == 0) {

            62 to 1
        } else {

            1 to 62
        }



        drawRectangle8(
            randomX,
            randomY,
            currentWidth,
            currentLength,
            poolOfChar,
            Color(0xFF1B5E20)
        )

        if (calculateCharacterPercentage8(matrix, poolOfChar) > breakPointS8) {
            drawRectangle8(
                randomX,
                randomY,
                currentWidth,
                currentLength,
                poolOfChar2,
                Color(0xFFD84315)
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