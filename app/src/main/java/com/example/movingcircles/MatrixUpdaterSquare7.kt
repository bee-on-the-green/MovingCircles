package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare7(
    var matrix: Array<CharArray>,
    var colorMatrix: Array<Array<Color>>
) {
    private val MatrixLengthS7: Int = matrix[0].size
    private val MatrixHeightS7: Int = matrix.size
    val sleepTimeS7: Long = 9

    val breakPointS7: Int = 91


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

    suspend fun startUpdating7(onMatrixUpdated: (Array<CharArray>, Array<Array<Color>>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix7()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val colorMatrixCopy = colorMatrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage7(matrixCopy, poolOfChar)
                onMatrixUpdated(matrixCopy, colorMatrixCopy, switchValue)
                Thread.sleep(sleepTimeS7)
            }
        }
    }



    fun stopUpdating7() {
        isRunning = false
    }







    private var counter7 = 0

    private fun updateMatrix7() {


        val (randomX, randomY) = selectRandomCoordinate7()

        val (currentLength, currentWidth) = if (counter7 % 2 == 0) {

            62 to 1
        } else {

            1 to 62
        }



        drawRectangle7(
            randomX,
            randomY,
            currentWidth,
            currentLength,
            poolOfChar,
            Color(0xFF1B5E20)
        )

        if (calculateCharacterPercentage7(matrix, poolOfChar) > breakPointS7) {
            drawRectangle7(
                randomX,
                randomY,
                currentWidth,
                currentLength,
                poolOfChar2,
                Color(0xFFD84315)
            )
        }
        counter7++

    }





    private fun selectRandomCoordinate7(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLengthS7),
            Random.nextInt(0, MatrixHeightS7)
        )
    }

    private fun drawRectangle7(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS7 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS7 - 1)) {
                matrix[y][x] = poolOfChar.random()
                colorMatrix[y][x] = color
            }
        }
    }

    private fun calculateCharacterPercentage7(
        matrix: Array<CharArray>,
        poolOfChar: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { char -> if (char in poolOfChar) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}