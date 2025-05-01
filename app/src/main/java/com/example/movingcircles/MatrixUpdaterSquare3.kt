package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare3(
    var matrix: Array<CharArray>,
    private val MatrixLength: Int = MatrixInitializerSquare3().MatrixLength,  // Reference from Initializer
    private val MatrixHeight: Int = MatrixInitializerSquare3().MatrixHeight,  // Reference from Initializer
    val sleepTime: Long = 2,

    val breakPoint: Int = 80,
    val poolOfChar2: Array<Char> = arrayOf('°', '²', ',', ',', '•'),
    val poolOfChar: Array<Char> = arrayOf('·', '.', '\'', '·', '.', '.', '\''),  // ˆ • ¸
) {
    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating3(onMatrixUpdated: (Array<CharArray>, Double) -> Unit) {
        isRunning = true
        withContext(Dispatchers.IO) {
            while (isRunning) {
                updateMatrix3()
                val matrixCopy = matrix.map { it.clone() }.toTypedArray()
                val switchValue = calculateCharacterPercentage3(matrixCopy, poolOfChar)
                onMatrixUpdated(matrixCopy, switchValue)
                Thread.sleep(sleepTime)
            }
        }
    }

    fun stopUpdating3() {
        isRunning = false
    }





        private fun updateMatrix3() {
            val (randomX, randomY) = selectRandomCoordinate3()
            val currentLength = 3
            val currentWidth = 5



            drawRectangle3(randomX, randomY, currentLength, currentWidth, poolOfChar)
            //drawRectangle3(randomX, randomY, currentWidth, currentLength, poolOfChar)

            if (calculateCharacterPercentage3(matrix, poolOfChar) > breakPoint) {

               // val (randomA, randomB) = selectRandomCoordinate3()

            drawRectangle3(randomX, randomY, currentWidth, currentLength, poolOfChar2)
            //drawRectangle3(randomB, randomY, currentWidth, currentLength, poolOfChar2)


            }
            updateCount++
        }






    private fun selectRandomCoordinate3(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLength),
            Random.nextInt(0, MatrixHeight)
        )
    }



    private fun drawRectangle3(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeight - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLength - 1)) {
                matrix[y][x] = poolOfChar.random()
            }
        }
    }






    private fun calculateCharacterPercentage3(
        matrix: Array<CharArray>,
        poolOfChar: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { char -> if (char in poolOfChar) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}