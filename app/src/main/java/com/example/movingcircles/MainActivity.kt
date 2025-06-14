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
import androidx.compose.ui.text.font.Font

val ratioBetweenLengthAndWidth: Int = 75  // wshould be 87  ratio //             lineHeight = 6.8.sp,  // was 6  and letterSpacing = 0.sp

val lengthOfMatrix: Int = 95  // 102
val heightOfMatrix: Int = lengthOfMatrix * ratioBetweenLengthAndWidth / 100
val poolOfCharInitial: Array<Char> = arrayOf(' ', ' ')
val resolution: Int = lengthOfMatrix * heightOfMatrix
val diameterToBeUsed: Int = 9  //

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
                    onPauseToggled = {
                        isPaused = !isPaused
                        if (isPaused) {
                            updateJob?.cancel()
                        } else {
                            startMatrixUpdates(::updateMatrixString)
                        }
                    },
                    onBackClick = { finish() }
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
        updateJob?.cancel() // Cancel any existing job first
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
    onPauseToggled: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 25.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                Spacer(modifier = Modifier.height(750.dp)) // was 700
                BackToWelcomeButton(onClick = onBackClick)
                Spacer(modifier = Modifier.height(5.dp))
                PlayPauseButton(
                    isPaused = isPaused,
                    onPauseToggled = onPauseToggled
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                MatrixText(matrixString, innerPadding)

                val numberFormat = NumberFormat.getInstance()

                Text(
                    text = """
        Main
        
        Elapsed: ${
                        if (timeElapsed.first > 0)
                            "${timeElapsed.first} min, ${timeElapsed.second} sec"
                        else
                            "${timeElapsed.second} sec"
                    }
        Cycles: ${numberFormat.format(updateCount)}
        
        Frequency: ${Hz} Hz
        Loop runtime: ${exactUpdateTime} ms
        Density: ${"%.2f".format(SwitchValue)}%
        
        Resolution: ${resolution} px (${lengthOfMatrix}x${heightOfMatrix})
        Shape diameter: $diameterToBeUsed px
        Encoding: UTF-8
    """.trimIndent(),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(6.dp)
                        .offset(y = (-110).dp),  // was 170
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(
                        Font(R.font.firacode_regular),
                    ),
                    style = TextStyle(lineHeight = 14.sp)
                )
            }
        }
    }
}

@Composable
fun PlayPauseButton(
    isPaused: Boolean,
    onPauseToggled: () -> Unit
) {
    IconButton(
        onClick = onPauseToggled,
        modifier = Modifier
            .padding(42.dp)
            .size(80.dp)
            .offset(x = 50.dp, y = 30.dp)  //  .offset(x = 50.dp, y = 50.dp)
    ) {
        Icon(
            painter = painterResource(id = if (isPaused) R.drawable.the_play else R.drawable.the_pause),
            contentDescription = if (isPaused) "Play" else "Pause",
            modifier = Modifier.size(80.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun BackToWelcomeButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(42.dp)
            .size(80.dp)
            .offset(x = 50.dp, y = 80.dp)  //was   .offset(x = 50.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.my_back_button_black),
            contentDescription = "Back to Welcome",
            modifier = Modifier.size(80.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun MatrixText(matrixString: String, innerPadding: PaddingValues) {
    Text(
        text = matrixString,  // ubuntu r
        modifier = Modifier.padding(innerPadding),
        fontSize = 9.1.sp,  // was 9
        fontFamily = FontFamily(Font(R.font.ubuntumono_r)),  // fontFamily = FontFamily(Font(R.font.firacode_regular))  // was fontFamily = FontFamily.Monospace
        fontWeight = FontWeight.Normal,
        style = TextStyle(
            lineHeight = 6.35.sp,  // was 6
            letterSpacing = 0.35.sp
        )


    )
}
