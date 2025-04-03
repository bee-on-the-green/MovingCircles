package com.example.movingcircles

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movingcircles.ui.theme.movingcirclesTheme

class WelcomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            movingcirclesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WelcomeContent(
                        onCirclesClicked = {
                            startActivity(Intent(this@WelcomeScreen, MainActivity::class.java))
                            finish()
                        },
                        onSquaresClicked = {
                            startActivity(Intent(this@WelcomeScreen, MainActivitySquare::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeContent(
    onCirclesClicked: () -> Unit,
    onSquaresClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choose Mode",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Circles Mode Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onCirclesClicked,
                    modifier = Modifier.size(120.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Thermostat,
                        contentDescription = "Circles Mode",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = "Circles",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Squares Mode Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onSquaresClicked,
                    modifier = Modifier.size(120.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CropSquare,
                        contentDescription = "Squares Mode",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = "Squares",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}