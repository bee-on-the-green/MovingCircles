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
                        },
                        onSquaresClicked = {
                            startActivity(Intent(this@WelcomeScreen, MainActivitySquare::class.java))
                        },
                        onSquaresSmallClicked = {
                            startActivity(Intent(this@WelcomeScreen, MainActivityCircle::class.java))
                        },
                        onCircles2Clicked = {
                            startActivity(Intent(this@WelcomeScreen, MainActivityCircles2::class.java))
                        },
                        onSquares2Clicked = {
                            startActivity(Intent(this@WelcomeScreen, MainActivitySquare2::class.java))
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
                .size(600.dp) // was 850
                .padding(bottom = 75.dp)  // was 65
        )

        Text(
            text = "Circles and Squares:",
            fontSize = 25.sp,
            modifier = Modifier.padding(bottom = 60.dp)  // was 40
        )

        Row(
            modifier = Modifier
                .padding(16.dp)
                .offset(y = (-30).dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Circles Mode
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(100.dp)
            ) {
                IconButton(
                    onClick = onCirclesClicked,
                    modifier = Modifier.size(80.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.circle_icon),
                        contentDescription = "Circles Mode",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = "B&W Circles",
                    fontSize = 11.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Squares Mode
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(100.dp)
            ) {
                IconButton(
                    onClick = onSquaresClicked,
                    modifier = Modifier.size(80.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.square_icon),
                        contentDescription = "Squares Mode",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = "One&Zero",
                    fontSize = 11.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Small Squares Mode
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(100.dp)
            ) {
                IconButton(
                    onClick = onSquaresSmallClicked,
                    modifier = Modifier.size(80.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_sun), // was icon_square_purple
                        contentDescription = "Small Squares Mode",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = "Orange",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Circles2 Mode
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(100.dp)
            ) {
                IconButton(
                    onClick = onCircles2Clicked,
                    modifier = Modifier.size(80.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_square_purple), // was icon_sun
                        contentDescription = "Circles2 Mode",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = "Purple",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Squares2 Mode
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(100.dp)
            ) {
                IconButton(
                    onClick = onSquares2Clicked,
                    modifier = Modifier.size(80.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.square_green_yellow),
                        contentDescription = "Squares2 Mode",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = "Squares2",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}