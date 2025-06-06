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

data class MatrixCell2(
    val char: Char,
    val color: Color
)

class MainActivityCircle4 : ComponentActivity() {
    private val matrixInitializer = MatrixInitializerCircle4()
    private lateinit var matrix: MutableList<MutableList<MatrixCell2>>
    private val matrixUpdater = MatrixUpdaterCircle4(
        matrix = Array(0) { Array(0) { MatrixCell2(' ', Color.White) } },
        diameterToUseC4 = matrixInitializer.diameterToUseC4
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
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(end = 25.dp),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.End
                        ) {
                            Spacer(modifier = Modifier.height(750.dp))
                            BackToWelcomeButton()
                            Spacer(modifier = Modifier.height(5.dp))
                            PlayPauseButton(
                                isPaused = isPaused,
                                onPauseToggled = {
                                    isPaused = !isPaused
                                    if (isPaused) {
                                        updateJob?.cancel()
                                    } else {
                                        startMatrixUpdates(::updateMatrixState)
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
                            MatrixText(matrixState, matrixColors, innerPadding)

                            val numberFormat = NumberFormat.getInstance()

                            Text(
                                text = """
                                CORAL ISLAND
        
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
        
                                Resolution: ${matrixInitializer.resolutionC} px (${matrixInitializer.MatrixLengthC4}×${matrixInitializer.MatrixHeightC4})
                                Shape diameter: ${matrixInitializer.diameterToUseC4} px
                                Encoding: ASCII
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
                        matrix = matrixInitializer.initializeMatrix()
                        updateMatrixState(matrix)
                        matrixUpdater.matrix = matrix.map { it.toTypedArray() }.toTypedArray()
                        Hz = (1000.0 / matrixUpdater.sleepTimeC4.toDouble()).roundToInt()

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
                    Hz = (1000.0 / matrixUpdater.sleepTimeC4.toDouble()).roundToInt()
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
                startActivity(Intent(this@MainActivityCircle4, WelcomeScreen::class.java))
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
    fun PlayPauseButton(
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
            fontSize = 11.2.sp,  // 13
            fontFamily = FontFamily(Font(R.font.firacode_regular)),
            fontWeight = FontWeight.Normal,
            style = TextStyle(lineHeight = 13.37.sp)  // was 14.37
        )

    }
}