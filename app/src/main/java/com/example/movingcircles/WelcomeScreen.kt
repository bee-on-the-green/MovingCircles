package com.example.movingcircles

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movingcircles.ui.theme.movingcirclesTheme

// Main Activity class for the Welcome Screen
class WelcomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            movingcirclesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WelcomeContent {
                        // When start is clicked, launch MainActivity and finish this activity
                        startActivity(Intent(this@WelcomeScreen, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}

// Composable function for the Welcome Screen UI
@Composable
fun WelcomeContent(onStartClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Moving Circles",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        IconButton(
            onClick = onStartClicked,
            modifier = Modifier.size(120.dp)
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_media_play),
                contentDescription = "Start",
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = "Tap to start",
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}