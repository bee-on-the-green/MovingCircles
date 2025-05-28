package com.example.movingcircles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MatrixUpdaterSquare(
    var matrix: Array<CharArray>,
    private val MatrixLengthS: Int = MatrixInitializerSquare().MatrixLengthS,  // Reference from MatrixInitializerSquare
    private val MatrixHeigthS: Int = MatrixInitializerSquare().MatrixHeightS,  // Reference from MatrixInitializerSquare
    val resolutionS: Int = MatrixLengthS * MatrixHeigthS,
    val sleepTime: Long = 50,
    val breakPoint2: Int = 82,

    val poolOfChar: Array<Char> = arrayOf(')', '('), // ('0', '0', '1')
    val poolOfChar2: Array<Char> = arrayOf('•', '°') //  ('·', '.', '\'', '·', '.', '\'', '.', '\'')


) {
    private var isRunning = false
    private var updateCount: Int = 0

    suspend fun startUpdating(onMatrixUpdated: (Array<CharArray>, Double) -> Unit) {
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

        val currentLength = 1
        val currentWidth = 60

        val (randomX, randomY) = selectRandomCoordinate()

        drawRectangle(randomX, randomY, currentLength, currentWidth, poolOfChar)


        if (calculateCharacterPercentage(matrix, poolOfChar) > breakPoint2) {

            //val (randomX2, randomY2) = selectRandomCoordinate()
            drawRectangle(randomX, randomY, currentLength, currentWidth, poolOfChar2)
        }

    }









    private fun selectRandomCoordinate(): Pair<Int, Int> {
        return Pair(
            Random.nextInt(0, MatrixLengthS),
            Random.nextInt(0, MatrixHeigthS)
        )
    }

    private fun drawRectangle(
        centerX: Int,
        centerY: Int,
        length: Int,
        width: Int,
        poolOfChar: Array<Char>
    ) {
        val halfLength = length / 2
        val halfWidth = width / 2

        for (y in maxOf(centerY - halfWidth, 0)..minOf(centerY + halfWidth, MatrixHeigthS - 1)) {
            for (x in maxOf(centerX - halfLength, 0)..minOf(centerX + halfLength, MatrixLengthS - 1)) {
                matrix[y][x] = poolOfChar.random()
            }
        }
    }

    private fun calculateCharacterPercentage(
        matrix: Array<CharArray>,
        poolOfChar: Array<Char>
    ): Double {
        var count = 0
        matrix.forEach { row -> row.forEach { char -> if (char in poolOfChar) count++ } }
        return (count.toDouble() / (matrix.size * matrix[0].size)) * 100
    }
}