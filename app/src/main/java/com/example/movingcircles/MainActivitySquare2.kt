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
import androidx.compose.ui.text.font.Font
import com.example.movingcircles.R

class MainActivitySquare2 : ComponentActivity() {
    private val matrixInitializer = MatrixInitializerSquare2()
    private lateinit var matrix: MutableList<MutableList<MatrixCell2>>
    private val matrixUpdater = MatrixUpdaterSquare2(matrix = Array(0) { Array(0) { MatrixCell2(' ') } })

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            movingcirclesTheme {
                var matrixState by remember { mutableStateOf("") }
                var matrixColors by remember { mutableStateOf(listOf<AnnotatedString.Range<Color>>()) }

                fun updateMatrixState(newMatrix: MutableList<MutableList<MatrixCell2>>) {
                    val (text, colorRanges) = matrixToAnnotatedString(newMatrix)
                    matrixState = text
                    matrixColors = colorRanges
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        BackToWelcomeButton()
                    },
                    floatingActionButtonPosition = FabPosition.Start
                ) { innerPadding ->
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.weight(1f)) {
                            MatrixText(matrixState, matrixColors, innerPadding)

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

                        IconButton(
                            onClick = {
                                isPaused = !isPaused
                                if (isPaused) {
                                    updateJob?.cancel()
                                } else {
                                    startMatrixUpdates(::updateMatrixState)
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .weight(0.1f)
                                .padding(bottom = 50.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = if (isPaused) android.R.drawable.ic_media_play else android.R.drawable.ic_media_pause),
                                contentDescription = if (isPaused) "Play" else "Pause",
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }

                LaunchedEffect(Unit) {
                    launch(Dispatchers.Default) {
                        matrix = matrixInitializer.initializeMatrix()
                        updateMatrixState(matrix)
                        matrixUpdater.matrix = matrix.map { it.toTypedArray() }.toTypedArray()
                        Hz = (1000.0 / matrixUpdater.sleepTime.toDouble()).roundToInt()

                        startMatrixUpdates(::updateMatrixState)
                    }
                }
            }
        }
    }

    private fun startMatrixUpdates(updateFn: (MutableList<MutableList<MatrixCell2>>) -> Unit) {
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
                    Hz = (1000.0 / matrixUpdater.sleepTime.toDouble()).roundToInt()
                    SwitchValue = switchValue
                    updateCount++
                    println("Elapsed Time: ${timeElapsed.first} minutes, ${timeElapsed.second} seconds")
                }
            }
        }
    }

    private fun matrixToAnnotatedString(matrix: MutableList<MutableList<MatrixCell2>>): Pair<String, List<AnnotatedString.Range<Color>>> {
        val stringBuilder = StringBuilder()
        val colorRanges = mutableListOf<AnnotatedString.Range<Color>>()
        var position = 0

        matrix.forEach { row ->
            row.forEach { cell ->
                stringBuilder.append(cell.char)
                if (cell.color != Color.White) {
                    colorRanges.add(
                        AnnotatedString.Range(
                            item = cell.color,
                            start = position,
                            end = position + 1
                        )
                    )
                }
                position++
            }
            stringBuilder.append("\n")
            position++
        }

        return Pair(stringBuilder.toString(), colorRanges)
    }

    override fun onDestroy() {
        super.onDestroy()
        updateJob?.cancel()
        matrixUpdater.stopUpdating()
    }

    private fun calculateElapsedTime() {
        val elapsedTimeMillis = System.currentTimeMillis() - startTime
        val totalSeconds = elapsedTimeMillis / 1000
        val minutes = (totalSeconds / 60).toInt()
        val seconds = (totalSeconds % 60).toInt()
        timeElapsed = Pair(minutes, seconds)
    }

    @Composable
    fun BackToWelcomeButton() {
        IconButton(
            onClick = {
                startActivity(Intent(this@MainActivitySquare2, WelcomeScreen::class.java))
                finish()
            },
            modifier = Modifier
                .padding(16.dp)
                .size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_revert),
                contentDescription = "Back to Welcome",
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    @Composable
    fun MatrixText(
        matrixString: String,
        colorRanges: List<AnnotatedString.Range<Color>>,
        innerPadding: PaddingValues
    ) {
        val annotatedString = buildAnnotatedString {
            append(matrixString)
            colorRanges.forEach { range ->
                addStyle(
                    style = SpanStyle(color = range.item),
                    start = range.start,
                    end = range.end
                )
            }
        }

        Text(
            text = annotatedString,
            modifier = Modifier.padding(innerPadding),
            color = PureWhite,
            fontSize = 9.sp,
            fontFamily = FontFamily(Font(R.font.firacode_light)),
            fontWeight = FontWeight.Normal,
            style = TextStyle(lineHeight = 13.sp)
        )
    }
}