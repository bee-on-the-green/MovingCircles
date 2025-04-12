package com.example.movingcircles

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
                        },
                        onSquaresSmallClicked = {
                            startActivity(Intent(this@WelcomeScreen, MainActivityCircle::class.java))
                            finish()
                        },
                        onCircles2Clicked = {
                            startActivity(Intent(this@WelcomeScreen, MainActivityCircles2::class.java))
                            finish()
                        },
                        onSquares2Clicked = {
                            startActivity(Intent(this@WelcomeScreen, MainActivitySquare2::class.java))
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
    onSquaresClicked: () -> Unit,
    onSquaresSmallClicked: () -> Unit,
    onCircles2Clicked: () -> Unit,
    onSquares2Clicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Welcome image at the top
        Image(
            painter = painterResource(id = R.drawable.welcome_image),
            contentDescription = "Welcome Image",
            modifier = Modifier
                .size(850.dp)
                .padding(bottom = 65.dp)
        )

        Text(
            text = "Choose a mode:",
            fontSize = 25.sp,
            modifier = Modifier.padding(bottom = 80.dp)
        )

        Row(
            modifier = Modifier
                .padding(16.dp)
                .offset(y = (-50).dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Circles Mode Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onCirclesClicked,
                    modifier = Modifier.size(120.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.circle_icon),
                        contentDescription = "Circles Mode",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Squares Mode Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onSquaresClicked,
                    modifier = Modifier.size(120.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.square_icon),
                        contentDescription = "Squares Mode",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Small Squares Mode Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onSquaresSmallClicked,
                    modifier = Modifier.size(120.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_square_purple),
                        contentDescription = "Small Squares Mode",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Circles2 Mode Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onCircles2Clicked,
                    modifier = Modifier.size(120.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_sun),
                        contentDescription = "Circles2 Mode",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Squares2 Mode Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onSquares2Clicked,
                    modifier = Modifier.size(120.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.square_green_yellow),
                        contentDescription = "Squares2 Mode",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}