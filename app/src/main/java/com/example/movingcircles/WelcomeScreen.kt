package com.example.movingcircles

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Thermostat // Changed to available icon
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
                    WelcomeContent {
                        startActivity(Intent(this@WelcomeScreen, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeContent(onStartClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Circles",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        IconButton(
            onClick = onStartClicked,
            modifier = Modifier.size(120.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Thermostat, // Using available thermostat icon
                contentDescription = "Start",
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = "start",
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}