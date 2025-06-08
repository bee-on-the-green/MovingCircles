package com.example.movingcircles

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare10(
    var matrix: Array<CharArray>,
    var colorMatrix: Array<Array<Color>>
) {
    private val MatrixLengthS10: Int = matrix[0].size
    private val MatrixHeightS10: Int = matrix.size
    val sleepTimeS10: Long = 10
    val breakPointS10: Int = 75

    //val poolOfChar: Array<Char> = arrayOf('O', 'O')  //·, ,  (•, O o 0  '○
    val poolOfChar2: Array<Char> = arrayOf('°', '•', '.', '·', '°', '○')  // arrayOf('°', '•', '•', '·', '○')   ('°', '•', '•', '·')



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
        'ç', '<', '>', '/', '?'
    )

    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating10(onMatrixUpdated: (Array<CharArray>, Array<Array<Color>>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix10()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val colorMatrixCopy = colorMatrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage10(matrixCopy, poolOfChar)
                onMatrixUpdated(matrixCopy, colorMatrixCopy, switchValue)
                Thread.sleep(sleepTimeS10)
            }
        }
    }

    fun stopUpdating10() {
        isRunning = false
    }


    private val ComplementaryMagenta = Color(0xFF5E1B59)
    val PureDarkPurple = Color(0xFF6A0C3C)

    private val DarkMaroon = Color(0xFF7B0E42)
    private val DeepViolet = Color(0xFF8E24AA)

    val LightMagenta = Color(0xFFAD1457)




    private var counter10 = 0

    private fun updateMatrix10() {
        val (randomX, randomY) = selectRandomCoordinate10()

        val selectedColor = if (counter10 % 2 == 0) Color(0xFF6A0C3C) else Color(0xFF7B0E42)  // 672E48FF   0xFF4B7D4B  0xFF4CAF50  // 0xFF315E1B

        val (currentLength, currentWidth) = if (counter10 % 2 == 0) {
            2 to 2  // was 5 and 5  // 3 and 7
        } else {
            2 to 2  // was 5 and 5
        }

        drawRectangle10(
            randomX,
            randomY,
            currentWidth,
            currentLength,
            poolOfChar,
            selectedColor
        )

        if (calculateCharacterPercentage10(matrix, poolOfChar) > breakPointS10) {
            drawRectangle10(
                randomX,
                randomY,
                currentWidth,
                currentLength,
                poolOfChar2,
                Color(0xFFE53935)
            )
        }
        counter10++
    }

    private fun selectRandomCoordinate10(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLengthS10),
            Random.nextInt(0, MatrixHeightS10)
        )
    }

    private fun drawRectangle10(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>,
        color: Color
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeightS10 - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS10 - 1)) {
                matrix[y][x] = poolOfChar.random()
                colorMatrix[y][x] = color
            }
        }
    }

    private fun calculateCharacterPercentage10(
        matrix: Array<CharArray>,
        targetChars: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { char -> if (char in targetChars) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}