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

class MainActivitySquare13 : ComponentActivity() {
    private val matrixInitializer13 = MatrixInitializerSquare13()
    private lateinit var matrix: Array<CharArray>
    private lateinit var colorMatrix: Array<Array<Color>>
    private lateinit var matrixUpdater13: MatrixUpdaterSquare13

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
    private val MatrixCodeGreen = Color(0xFF00FF41)

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
                            BackToWelcomeButton13()
                            Spacer(modifier = Modifier.height(5.dp))
                            PlayPauseButton13(
                                isPaused = isPaused,
                                onPauseToggled = {
                                    isPaused = !isPaused
                                    if (isPaused) {
                                        updateJob?.cancel()
                                    } else {
                                        startMatrixUpdates13()
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
                            MatrixText13(matrixString, colorRanges, innerPadding)

                            val numberFormat = NumberFormat.getInstance()

                            Text(
                                text = """
                                Square13
        
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
        
                                Resolution: ${matrixInitializer13.resolution13} px (${matrixInitializer13.MatrixLengthS13}×${matrixInitializer13.MatrixHeightS13})
                                Shape size: 1x35 px
                                Encoding: UFT-8
                                """.trimIndent(),
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(6.dp)
                                    .offset(y = (-110).dp),
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

                LaunchedEffect(Unit) {
                    launch(Dispatchers.Default) {
                        val (charMatrix, colorMatrix) = matrixInitializer13.initializeMatrix13()
                        matrix = charMatrix
                        this@MainActivitySquare13.colorMatrix = colorMatrix
                        matrixString = matrix.joinToString("\n") { it.joinToString("") }
                        matrixUpdater13 = MatrixUpdaterSquare13(matrix, colorMatrix)
                        Hz = (1000.0 / matrixUpdater13.sleepTimeS13.toDouble()).roundToInt()
                        startMatrixUpdates13()
                    }
                }
            }
        }
    }

    private fun startMatrixUpdates13() {
        updateJob = lifecycleScope.launch {
            matrixUpdater13.startUpdating13 { updatedMatrix, updatedColorMatrix, switchValue ->
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
                    colorMatrix = updatedColorMatrix
                    matrixString = updatedMatrix.joinToString("\n") { it.joinToString("") }
                    colorRanges = buildColorRanges(updatedColorMatrix, matrixString)

                    calculateElapsedTime()
                    Hz = (1000.0 / matrixUpdater13.sleepTimeS13.toDouble()).roundToInt()
                    SwitchValue = switchValue
                    updateCount++
                }
            }
        }
    }

    private fun buildColorRanges(
        colorMatrix: Array<Array<Color>>,
        matrixString: String
    ): List<AnnotatedString.Range<Color>> {
        val ranges = mutableListOf<AnnotatedString.Range<Color>>()
        var position = 0
        for (y in colorMatrix.indices) {
            for (x in colorMatrix[y].indices) {
                val color = colorMatrix[y][x]
                if (color != Color.White) {
                    ranges.add(AnnotatedString.Range(color, position, position + 1))
                }
                position++
            }
            position++ // For the newline character
        }
        return ranges
    }

    override fun onDestroy() {
        super.onDestroy()
        updateJob?.cancel()
        matrixUpdater13.stopUpdating13()
    }

    private fun calculateElapsedTime() {
        val elapsedTimeMillis = System.currentTimeMillis() - startTime
        val totalSeconds = elapsedTimeMillis / 1000
        val minutes = (totalSeconds / 60).toInt()
        val seconds = (totalSeconds % 60).toInt()
        timeElapsed = Pair(minutes, seconds)
    }

    @Composable
    fun BackToWelcomeButton13() {
        IconButton(
            onClick = {
                startActivity(Intent(this@MainActivitySquare13, WelcomeScreen::class.java))
                finish()
            },
            modifier = Modifier
                .padding(42.dp)
                .size(80.dp)
                .offset(x = 50.dp, y = 80.dp)
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
    fun PlayPauseButton13(
        isPaused: Boolean,
        onPauseToggled: () -> Unit
    ) {
        IconButton(
            onClick = onPauseToggled,
            modifier = Modifier
                .padding(42.dp)
                .size(80.dp)
                .offset(x = 50.dp, y = 30.dp)
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
    fun MatrixText13(
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
            color = MatrixCodeGreen,
            fontFamily = FontFamily(Font(R.font.firacode_light)),
            fontWeight = FontWeight.Normal,
            fontSize = 25.sp,  // 20.5
            letterSpacing = 0.62.sp,  // 0.3
            style = TextStyle(
                lineHeight = 3.sp
            )
        )
    }
}
