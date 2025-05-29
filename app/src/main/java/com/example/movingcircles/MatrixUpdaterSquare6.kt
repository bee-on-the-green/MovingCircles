package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare6(
    var matrix: Array<CharArray>,
    var colorMatrix: Array<Array<Color>>
) {
    private val MatrixLengthS6: Int = matrix[0].size
    private val MatrixHeightS6: Int = matrix.size
    val sleepTime: Long = 9

    val breakPointS: Int = 91


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

    private val Green850 = Color(0xFF2E7D32)
    private val Green900 = Color(0xFF1B5E20)
    private val SafetyOrange = Color(0xFFFF7900)
    private val NeonOrange = Color(0xFFFF6600)
    private val Orange600 = Color(0xFFE64A19)  // Material Orange 600
    private val Orange650 = Color(0xFFD84315)  // Custom (between 600-700)


/*
    '§', '¶', '¬', '¢', '£',
    '¥', '®', '©', 'ª', 'º',
    '±', 'µ', '¼', '½', '¾',

    'É', 'Ê', 'Ë', 'Ì', 'Í',
    'A', 'G', 'H', 'I', 'J',
    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',

    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
    'u', 'v', 'w', 'x', 'y', 'z',


    '!', '@', '#', '$', '%',
    '^', '&', '*', '(', ')',
    '-', '_', '=', '+', '[',
    ']', '{', '}', '|', '\\',
    ';', ':', '\'', '"', ',',
    '.', '<', '>', '/', '?'

*/



    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating6(onMatrixUpdated: (Array<CharArray>, Array<Array<Color>>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix6()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val colorMatrixCopy = colorMatrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage6(matrixCopy, poolOfChar)
                onMatrixUpdated(matrixCopy, colorMatrixCopy, switchValue)
                Thread.sleep(sleepTime)
            }
        }
    }



    fun stopUpdating6() {
        isRunning = false
    }









    private var counter6 = 0

    private fun updateMatrix6() {


        val (randomX, randomY) = selectRandomCoordinate6()

        val (currentLength, currentWidth) = if (counter6 % 2 == 0) {

            62 to 1
        } else {

            1 to 62
        }



        drawRectangle6(
            randomX,
            randomY,
            currentWidth,
            currentLength,
            poolOfChar,
            Color(0xFF1B5E20)  // 0xFF2E7D32
        )

        if (calculateCharacterPercentage6(matrix, poolOfChar) > breakPointS) {
            drawRectangle6(
                randomX,
                randomY,
                currentWidth,
                currentLength,
                poolOfChar2,
                Color(0xFFD84315)   // 0xFFE64A19
            )
        }
        counter6++

    }





    private fun selectRandomCoordinate6(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLengthS6),
            Random.nextInt(0, MatrixHeightS6)
        )
    }

    private fun drawRectangle6(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS6 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS6 - 1)) {
                matrix[y][x] = poolOfChar.random()
                colorMatrix[y][x] = color
            }
        }
    }

    private fun calculateCharacterPercentage6(
        matrix: Array<CharArray>,
        poolOfChar: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { char -> if (char in poolOfChar) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}