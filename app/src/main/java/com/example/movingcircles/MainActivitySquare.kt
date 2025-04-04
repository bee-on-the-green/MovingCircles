package com.example.movingcircles

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*
import androidx.lifecycle.lifecycleScope
import kotlin.math.roundToInt

class MainActivitySquare : ComponentActivity() {
    private val matrixInitializer = MatrixInitializerSquare()
    private lateinit var matrix: MutableList<MutableList<Char>>
    private val matrixUpdater = MatrixUpdaterSquare(matrix = arrayOf())

    private var updateJob: Job? = null
    private var startTime: Long = 0
    private var isFirstUpdate = true
    private var timeElapsed: Pair<Int, Int> by mutableStateOf(Pair(0, 0))
    private var Hz: Int by mutableStateOf(0)
    private var isPaused by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var matrixString by remember { mutableStateOf("") }

            // Hardcoded colors
            val backgroundColor = Color.Black
            val textColor = Color.White

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            startActivity(Intent(this@MainActivitySquare, WelcomeScreen::class.java))
                            finish()
                        },
                        modifier = Modifier.padding(16.dp),
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to menu",
                            tint = Color.White
                        )
                    }
                },
                floatingActionButtonPosition = FabPosition.Start
            ) { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    color = backgroundColor
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(32.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            Text(
                                text = matrixString,
                                modifier = Modifier.padding(4.dp),
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace,
                                style = TextStyle(lineHeight = 10.sp),
                                color = textColor
                            )

                            Text(
                                text = "Elapsed: ${timeElapsed.first} min, ${timeElapsed.second} sec\n" +
                                        "Refresh: ${Hz} Hz",
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp),
                                fontSize = 12.sp,
                                color = textColor
                            )
                        }

                        IconButton(
                            onClick = { isPaused = !isPaused },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(50.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = textColor
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = if (isPaused)
                                    android.R.drawable.ic_media_play else
                                    android.R.drawable.ic_media_pause),
                                contentDescription = if (isPaused) "Play" else "Pause",
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            }

            LaunchedEffect(isPaused) {
                if (!isPaused) {
                    updateJob?.cancel()
                    updateJob = lifecycleScope.launch {
                        matrix = withContext(Dispatchers.IO) {
                            matrixInitializer.initializeMatrix()
                        }
                        matrixString = matrixToString(matrix)
                        matrixUpdater.matrix = matrix.map { it.toCharArray() }.toTypedArray()

                        withContext(Dispatchers.IO) {
                            matrixUpdater.startUpdating { updatedMatrix, _ ->
                                launch(Dispatchers.Main) {
                                    if (isFirstUpdate) {
                                        startTime = System.currentTimeMillis()
                                        isFirstUpdate = false
                                    }
                                    matrixString = matrixToString(
                                        updatedMatrix.map { it.toMutableList() }.toMutableList()
                                    )
                                    calculateElapsedTime()
                                    Hz = (1000.0 / matrixUpdater.sleepTime).roundToInt()
                                }
                            }
                        }
                    }
                } else {
                    updateJob?.cancel()
                }
            }
        }
    }

    private fun matrixToString(matrix: MutableList<MutableList<Char>>): String {
        return matrix.joinToString("\n") { row -> row.joinToString("") }
    }

    private fun calculateElapsedTime() {
        val elapsed = (System.currentTimeMillis() - startTime) / 1000
        timeElapsed = Pair((elapsed / 60).toInt(), (elapsed % 60).toInt())
    }

    override fun onDestroy() {
        super.onDestroy()
        updateJob?.cancel()
        matrixUpdater.stopUpdating()
    }
}