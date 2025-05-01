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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
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
                        },
                        onSquares3Clicked = {
                            startActivity(Intent(this@WelcomeScreen, MainActivitySquare3::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun IconButtonWithLabel(
    onClick: () -> Unit,
    iconResId: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(80.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(70.dp)
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "$label Mode",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RectangleShape)
            )
        }
        Text(
            text = label,
            fontSize = 11.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun WelcomeContent(
    onCirclesClicked: () -> Unit,
    onSquaresClicked: () -> Unit,
    onSquaresSmallClicked: () -> Unit,
    onCircles2Clicked: () -> Unit,
    onSquares2Clicked: () -> Unit,
    onSquares3Clicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome_image),
            contentDescription = "Welcome Image",
            modifier = Modifier
                .size(500.dp)
                .padding(bottom = 50.dp)
        )

        Text(
            text = "Circles and Squares:",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 50.dp)
        )

        // Main container for both rows
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .offset(y = (-20).dp),
            horizontalAlignment = Alignment.Start
        ) {
            // First row with 5 icons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButtonWithLabel(
                    onClick = onCirclesClicked,
                    iconResId = R.drawable.circle_icon,
                    label = "B&W"
                )

                IconButtonWithLabel(
                    onClick = onSquaresClicked,
                    iconResId = R.drawable.square_icon,
                    label = "One&Zero"
                )

                IconButtonWithLabel(
                    onClick = onSquaresSmallClicked,
                    iconResId = R.drawable.icon_sun,
                    label = "Orange"
                )

                IconButtonWithLabel(
                    onClick = onCircles2Clicked,
                    iconResId = R.drawable.icon_square_purple,
                    label = "Purple"
                )

                IconButtonWithLabel(
                    onClick = onSquares2Clicked,
                    iconResId = R.drawable.square_green_yellow,
                    label = "Squares2"
                )
            }

            // Second row with Squares3 icon aligned below B&W
            Spacer(modifier = Modifier.height(16.dp))
            IconButtonWithLabel(
                onClick = onSquares3Clicked,
                iconResId = R.drawable.labirynte,
                label = "Squares3",
                modifier = Modifier.padding(start = 0.dp)
            )
        }
    }
}