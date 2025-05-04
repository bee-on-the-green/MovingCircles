package com.example.movingcircles

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*
import androidx.lifecycle.lifecycleScope
import com.example.movingcircles.ui.theme.movingcirclesTheme
import com.example.movingcircles.ui.theme.PureWhite
import kotlin.math.roundToInt
import java.text.NumberFormat

class MainActivitySquare3 : ComponentActivity() {
    private val matrixInitializer3 = MatrixInitializerSquare3()
    private lateinit var matrix: Array<CharArray>
    private val matrixUpdater3 = MatrixUpdaterSquare3(matrix = emptyArray())

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
    private var colorRanges: List<AnnotatedString.Range<Color>> by mutableStateOf(emptyList())
    private var matrixString by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            movingcirclesTheme {
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
                            Spacer(modifier = Modifier.height(750.dp))
                            BackToWelcomeButton3()
                            Spacer(modifier = Modifier.height(5.dp))
                            PlayPauseButton3(
                                isPaused = isPaused,
                                onPauseToggled = {
                                    isPaused = !isPaused
                                    if (isPaused) {
                                        updateJob?.cancel()
                                    } else {
                                        startMatrixUpdates3()
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End
                ) { innerPadding ->
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.weight(1f)) {
                            MatrixText3(matrixString, colorRanges, innerPadding)

                            val numberFormat = NumberFormat.getInstance()

                            Text(
                                text = "Elapsed: ${
                                    if (timeElapsed.first > 0)
                                        "${timeElapsed.first} min, ${timeElapsed.second} sec"
                                    else
                                        "${timeElapsed.second} sec"
                                }\n" +
                                        "Cycles: ${numberFormat.format(updateCount)}\n" +
                                        "\n" +
                                        "Frequency: ${Hz} Hz  (${exactUpdateTime} ms per second)\n" +
                                        "Loop runtime: ${exactUpdateTime} ms\n" +
                                        "Density: ${"%.2f".format(SwitchValue)}%\n" +
                                        "\n" +
                                        "Resolution: ${matrixInitializer3.resolution3} px (${matrixInitializer3.MatrixLengthS3}×${matrixInitializer3.MatrixHeightS3})\n" +
                                        "Shape size: 3*5 px\n" +
                                        "Encoding: UTF-8\n",

                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(6.dp)
                                    .offset(y = (-170).dp),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(
                                    Font(R.font.firacode_regular),
                                ),
                                style = TextStyle(lineHeight = 14.sp)
                            )
                        }
                    }
                }

                LaunchedEffect(Unit) {
                    launch(Dispatchers.Default) {
                        val initializedMatrix = matrixInitializer3.initializeMatrix3()
                        matrix = initializedMatrix.map { it.toCharArray() }.toTypedArray()
                        matrixString = matrixToString(initializedMatrix)
                        matrixUpdater3.matrix = matrix
                        Hz = (1000.0 / matrixUpdater3.sleepTime.toDouble()).roundToInt()
                        startMatrixUpdates3()
                    }
                }
            }
        }
    }

    private fun startMatrixUpdates3() {
        updateJob = lifecycleScope.launch {
            matrixUpdater3.startUpdating3 { updatedMatrix, switchValue ->
                launch(Dispatchers.Main) {
                    val currentTime = System.currentTimeMillis()

                    if (isFirstUpdate) {
                        startTime = currentTime
                        isFirstUpdate = false
                    } else {
                        exactUpdateTime = currentTime - lastUpdateTime
                    }
                    lastUpdateTime = currentTime

                    matrix = updatedMatrix
                    val newMatrixList = updatedMatrix.map { it.toList() }.toList()
                    matrixString = matrixToString(newMatrixList)
                    calculateElapsedTime()
                    Hz = (1000.0 / matrixUpdater3.sleepTime.toDouble()).roundToInt()
                    SwitchValue = switchValue
                    updateCount++
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        updateJob?.cancel()
        matrixUpdater3.stopUpdating3()
    }

    private fun matrixToString(matrix: List<List<Char>>): String {
        return matrix.joinToString("\n") { row ->
            row.joinToString("")
        }
    }

    private fun calculateElapsedTime() {
        val elapsedTimeMillis = System.currentTimeMillis() - startTime
        val totalSeconds = elapsedTimeMillis / 1000
        val minutes = (totalSeconds / 60).toInt()
        val seconds = (totalSeconds % 60).toInt()
        timeElapsed = Pair(minutes, seconds)
    }

    @Composable
    fun BackToWelcomeButton3() {
        IconButton(
            onClick = {
                startActivity(Intent(this@MainActivitySquare3, WelcomeScreen::class.java))
                finish()
            },
            modifier = Modifier
                .padding(42.dp)
                .size(80.dp)
                .offset(x = 50.dp, y = 50.dp)
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
    fun PlayPauseButton3(
        isPaused: Boolean,
        onPauseToggled: () -> Unit
    ) {
        IconButton(
            onClick = onPauseToggled,
            modifier = Modifier
                .padding(42.dp)
                .size(80.dp)
                .offset(x = 50.dp, y = 40.dp)
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
    fun MatrixText3(
        matrixString: String,
        colorRanges: List<AnnotatedString.Range<Color>>,
        innerPadding: PaddingValues
    ) {
        val annotatedString = remember(matrixString, colorRanges) {
            buildAnnotatedString {
                append(matrixString)
                colorRanges.sortedByDescending { it.start }.forEach { range ->
                    try {
                        addStyle(
                            style = SpanStyle(color = range.item),
                            start = range.start,
                            end = range.end
                        )
                    } catch (e: Exception) {
                        println("Error applying color at position ${range.start}-${range.end}")
                    }
                }
            }
        }

        Text(
            text = annotatedString,
            modifier = Modifier.padding(innerPadding),
            color = PureWhite,
            fontFamily = FontFamily(Font(R.font.firacode_regular)),
            fontWeight = FontWeight.Normal,
            fontSize = 7.sp,
            style = TextStyle(lineHeight = 13.3.sp)
        )
    }
}