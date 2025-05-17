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
                        onCirclesClicked = { startActivity(Intent(this, MainActivity::class.java)) },
                        onSquaresClicked = { startActivity(Intent(this, MainActivitySquare::class.java)) },
                        onSquaresSmallClicked = { startActivity(Intent(this, MainActivityCircle::class.java)) },
                        onCircles2Clicked = { startActivity(Intent(this, MainActivityCircles2::class.java)) },
                        onSquares2Clicked = { startActivity(Intent(this, MainActivitySquare2::class.java)) },
                        onSquares3Clicked = { startActivity(Intent(this, MainActivitySquare3::class.java)) },
                        onSquares4Clicked = { startActivity(Intent(this, MainActivitySquare4::class.java)) },
                        onCircles4Clicked = { startActivity(Intent(this, MainActivityCircle4::class.java)) }
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
        Surface(
            onClick = onClick,
            shape = RectangleShape,
            modifier = Modifier.size(70.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "$label Mode",
                contentScale = ContentScale.Crop,
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
    onSquares3Clicked: () -> Unit,
    onSquares4Clicked: () -> Unit,
    onCircles4Clicked: () -> Unit
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

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .offset(y = (-20).dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButtonWithLabel(
                    onClick = onCirclesClicked,
                    iconResId = R.drawable.circle_icon,
                    label = "Main"
                )
                IconButtonWithLabel(
                    onClick = onSquaresClicked,
                    iconResId = R.drawable.square_icon,
                    label = "Square"
                )
                IconButtonWithLabel(
                    onClick = onSquaresSmallClicked,
                    iconResId = R.drawable.icon_sun,
                    label = "Circle"
                )
                IconButtonWithLabel(
                    onClick = onCircles2Clicked,
                    iconResId = R.drawable.icon_square_purple,
                    label = "Circle2"
                )
                IconButtonWithLabel(
                    onClick = onSquares2Clicked,
                    iconResId = R.drawable.square_green_yellow,
                    label = "Squares2"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButtonWithLabel(
                    onClick = onSquares3Clicked,
                    iconResId = R.drawable.labirynte,
                    label = "Squares3"
                )
                IconButtonWithLabel(
                    onClick = onSquares4Clicked,
                    iconResId = R.drawable.carre_rond,
                    label = "Squares4"
                )
                IconButtonWithLabel(
                    onClick = onCircles4Clicked,
                    iconResId = R.drawable.rond,
                    label = "Circles4"
                )
            }
        }
    }
}