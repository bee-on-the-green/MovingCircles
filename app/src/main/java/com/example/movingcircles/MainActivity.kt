package com.example.movingcircles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*
import androidx.lifecycle.lifecycleScope
import com.example.movingcircles.ui.theme.movingcirclesTheme
import kotlin.math.roundToInt
import java.text.NumberFormat
import androidx.compose.ui.graphics.Color

val ratioBetweenLengthAndWidth: Int = 55
val lengthOfMatrix: Int = 102
val heightOfMatrix: Int = lengthOfMatrix * ratioBetweenLengthAndWidth / 100
val poolOfCharInitial: Array<Char> = arrayOf('.', '·')

class MainActivity : ComponentActivity() {

    private val matrixInitializer = MatrixInitializer()
    private lateinit var matrix: MutableList<MutableList<Char>>
    private val matrixUpdater = MatrixUpdater(
        matrix = arrayOf(),
        lengthOfMatrix = lengthOfMatrix,
        heightOfMatrix = heightOfMatrix
    )

    private var updateJob: Job? = null
    private var startTime: Long = 0
    private var isFirstUpdate = true
    private var timeElapsed: Pair<Int, Int> by mutableStateOf(Pair(0, 0))
    private var Hz: Int by mutableStateOf(0)
    private var isPaused by mutableStateOf(false)
    private var SwitchValue: Double by mutableStateOf(0.0)
    private var updateCount: Int by mutableStateOf(0)
    private var exactUpdateTime: Long by mutableStateOf(0L)
    private var lastUpdateTime: Long by mutableStateOf(0L)
    private var updateCounterForRefresh: Int by mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            movingcirclesTheme {
                var matrixString by remember { mutableStateOf("") }

                fun updateMatrixString(newMatrix: MutableList<MutableList<Char>>) {
                    matrixString = matrixToString(newMatrix)
                }

                MatrixScreen(
                    matrixString = matrixString,
                    isPaused = isPaused,
                    timeElapsed = timeElapsed,
                    Hz = Hz,
                    exactUpdateTime = exactUpdateTime,
                    SwitchValue = SwitchValue,
                    updateCount = updateCount,
                    onPauseChange = { paused ->
                        isPaused = paused
                        if (paused) {
                            updateJob?.cancel()
                        } else {
                            startMatrixUpdates(::updateMatrixString)
                        }
                    },
                    onBackClick = { finish() },
                    updateMatrix = { startMatrixUpdates(::updateMatrixString) }
                )

                LaunchedEffect(Unit) {
                    launch(Dispatchers.Default) {
                        matrix = matrixInitializer.initializeMatrix()
                        updateMatrixString(matrix)
                        matrixUpdater.matrix = matrix.map { it.toCharArray() }.toTypedArray()
                        Hz = (1000.0 / matrixUpdater.sleepTime.toDouble()).roundToInt()
                        startMatrixUpdates(::updateMatrixString)
                    }
                }
            }
        }
    }

    private fun startMatrixUpdates(updateFn: (MutableList<MutableList<Char>>) -> Unit) {
        updateJob = lifecycleScope.launch {
            matrixUpdater.startUpdating { updatedMatrix, switchValue ->
                launch(Dispatchers.Main) {
                    val currentTime = System.currentTimeMillis()

                    if (isFirstUpdate) {
                        startTime = currentTime
                        isFirstUpdate = false
                    } else {
                        exactUpdateTime = currentTime - lastUpdateTime
                    }
                    lastUpdateTime = currentTime

                    val newMatrix = updatedMatrix.map { it.toMutableList() }.toMutableList()
                    updateFn(newMatrix)
                    calculateElapsedTime()
                    SwitchValue = switchValue
                    updateCount++

                    if (updateCounterForRefresh >= 20) {
                        Hz = (1000.0 / matrixUpdater.sleepTime.toDouble()).roundToInt()
                        updateCounterForRefresh = 0
                    }
                    updateCounterForRefresh++
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        updateJob?.cancel()
        matrixUpdater.stopUpdating()
    }

    private fun matrixToString(matrix: MutableList<MutableList<Char>>): String {
        return matrix.joinToString("\n") { row -> row.joinToString("") }
    }

    private fun calculateElapsedTime() {
        val elapsedTimeMillis = System.currentTimeMillis() - startTime
        val totalSeconds = elapsedTimeMillis / 1000
        timeElapsed = Pair((totalSeconds / 60).toInt(), (totalSeconds % 60).toInt())
    }
}

@Composable
fun MatrixScreen(
    matrixString: String,
    isPaused: Boolean,
    timeElapsed: Pair<Int, Int>,
    Hz: Int,
    exactUpdateTime: Long,
    SwitchValue: Double,
    updateCount: Int,
    onPauseChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    updateMatrix: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BackToWelcomeButton(onClick = onBackClick)
                PlayPauseButton(
                    isPaused = isPaused,
                    onPauseChange = { paused ->
                        onPauseChange(paused)
                        if (!paused) {
                            updateMatrix()
                        }
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Start
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                MatrixText(matrixString, innerPadding)

                val numberFormat = NumberFormat.getInstance()

                Text(
                    text = "Elapsed: ${
                        if (timeElapsed.first > 0)
                            "${timeElapsed.first} min, ${timeElapsed.second} sec"
                        else
                            "${timeElapsed.second} sec"
                    }\n" +
                            "Refresh: ${Hz} Hz  (${exactUpdateTime} ms per second)\n" +
                            "Density: ${"%.2f".format(SwitchValue)}%\n" +
                            "Cycles: ${numberFormat.format(updateCount)}\n",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(6.dp)
                        .offset(y = (-200).dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    style = TextStyle(lineHeight = 12.sp)
                )
            }
        }
    }
}

@Composable
fun PlayPauseButton(
    isPaused: Boolean,
    onPauseChange: (Boolean) -> Unit
) {
    IconButton(
        onClick = { onPauseChange(!isPaused) },
        modifier = Modifier
            .padding(50.dp)
            .size(120.dp)
    ) {
        Icon(
            painter = painterResource(id = if (isPaused) R.drawable.the_play else R.drawable.the_pause),
            contentDescription = if (isPaused) "Play" else "Pause",
            modifier = Modifier.size(120.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun BackToWelcomeButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(50.dp)
            .size(120.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.my_back_button_black),
            contentDescription = "Back to Welcome",
            modifier = Modifier.size(120.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun MatrixText(matrixString: String, innerPadding: PaddingValues) {
    Text(
        text = matrixString,
        modifier = Modifier.padding(innerPadding),
        fontSize = 9.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Normal,
        style = TextStyle(lineHeight = 10.sp)
    )
}