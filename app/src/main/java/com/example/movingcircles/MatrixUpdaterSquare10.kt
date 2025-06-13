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
    val sleepTimeS10: Long = 568
    val breakPointS10: Int = 71


// ('○', '°', '°', '○', ')', '(')
    val poolOfChar: Array<Char> = arrayOf(
    'µ', '¶',
    'Y', '»', '¼', '½', '¾',
    '¿', 'À', 'Á', 'Â',
    'Ä', 'Å', 'Æ', 'Ç', 'È',
    'É', 'Ê', 'Ë', 'Ì',
    'Î', 'Ï', 'Ð', 'Ñ', 'Ò',
    'Ó', 'Ô', 'Õ', 'Ö',
    'Ø', 'Ù', 'Ú', 'Û', 'Ü',
    'Ý', 'Þ', 'ß', 'à', 'á',
    'â', 'ã', 'ä', 'å', 'æ',
    'ç', 'è', 'é', 'ê', 'ë',
    'X', 'W', 'î', 'ï', 'M',
    )



    val poolOfChar2: Array<Char> = arrayOf(')', '(', '○', ')', '(')   //// // ('Ͽ', 'Ͼ')  ('○', '°', '°', '○', ')', '(')






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
    // Define the new colors
    val Red900 = Color(0xFFB71C1C)
    val Red1000 = Color(0xFF8E0000)
    val Red950 = Color(0xFF9B0000)
    val Red850 = Color(0xFFD32F2F)
    private val SoftOrchid = Color(0xFFD7A3E1)
    private val DustyPlum = Color(0xFFC98FD0)
    private val MauveTwilight = Color(0xFFB57FBD)
    private val Violet225 = Color(0xFFC885D2)  // Between 200-300
    private val Violet250 = Color(0xFFC277CC)  // Closer to 300
    private val RadioactivePink = Color(0xFFFF00AA)
    private val DarkGreen900_Tint1 = Color(0xFF356D36)  // green2
    private val DarkGreen900_Tint2 = Color(0xFF4B7D4B)
    private val DarkGreen900_Tint3 = Color(0xFF618C61)
    private val DarkGreen900_Tint4 = Color(0xFF779C76)
    private val DarkGreen900_Tint5 = Color(0xFF8DAC8C)

    // Material Design Green Palette (related shades)
    private val Green500 = Color(0xFF4CAF50)
    private val Green600 = Color(0xFF43A047)
    private val Green700 = Color(0xFF388E3C)
    private val Green800 = Color(0xFF2E7D32)

    // Monochromatic Greens (different hues, but still green-based)
    private val MonochromaticGreen1 = Color(0xFF27882E)
    private val MonochromaticGreen2 = Color(0xFF33B33D)
    private val MonochromaticGreen3 = Color(0xFF40DD4B)



    val YellowGreenMuted = Color(0xFFB5DC4C) // HSL: Hue ~90°, Saturation ~70%, Lightness ~52%
    val YellowGreenPure = Color(0xFFCADD40)  // HSL: Hue ~80°, Saturation ~75%, Lightness ~55%
    val PaleYellowSoft = Color(0xFFE6D666)   // HSL: Hue ~60°, Saturation ~60%, Lightness ~60%

    private var counter10 = 0

    private fun updateMatrix10() {


        val (randomX, randomY) = selectRandomCoordinate10()

        val currentLength = 110  // was 88
        val currentWidth = 2


        // Randomly choose between 1 and 2
        val randomChoice = Random.nextInt(1, 3) // Generates 1 or 2

        val selectedColor = if (randomChoice == 1) {
            Color(0xFF4B7D4B)
        } else {
            Color(0xFF356D36)
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
                Color(0xFF40DD4B))

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
        // Calculate the start and end coordinates for X (length)
        val startX = centerX - length / 2
        val endX = centerX + (length - 1) / 2

        // Calculate the start and end coordinates for Y (width)
        val startY = centerY - width / 2
        val endY = centerY + (width - 1) / 2

        // Clamp to matrix bounds and iterate
        for (y in maxOf(startY, 0)..minOf(endY, MatrixHeightS10 - 1)) {
            for (x in maxOf(startX, 0)..minOf(endX, MatrixLengthS10 - 1)) {
                matrix[y][x] = poolOfChar.random()
                colorMatrix[y][x] = color
            }
        }
    }







    /*
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
*/




    private fun calculateCharacterPercentage10(
        matrix: Array<CharArray>,
        targetChars: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { char -> if (char in targetChars) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}