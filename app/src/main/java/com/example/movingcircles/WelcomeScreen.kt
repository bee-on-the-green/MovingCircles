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
            modifier = Modifier.size(75.dp),  // was 70
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
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Images with their original positions
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.pool_of_char),
                contentDescription = "Pool of Characters",
                modifier = Modifier
                    .size(470.dp)  // was 450
                    .offset(y = (-132).dp)  // was minus 120
            )

            Image(
                painter = painterResource(id = R.drawable.welcome_image),
                contentDescription = "Welcome Image",
                modifier = Modifier
                    .size(820.dp)  // was 800
                    .padding(top = 50.dp, bottom = 20.dp)
                    .offset(y = (-320).dp)  // was 300
            )
        }

        // Icons positioned absolutely at the top
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)  // This anchors to bottom
                .padding(bottom = 60.dp),     // THE SMALLER, THE MORE TO THE BOTTOM  /// was 30
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(47.dp)  // was 16.dp
                ) {
                    IconButtonWithLabel(
                        onClick = onCirclesClicked,
                        iconResId = R.drawable.circle_icon,
                        label = "Braces" // is main  // OKAY
                    )
                    IconButtonWithLabel(
                        onClick = onSquaresClicked,
                        iconResId = R.drawable.square_icon,
                        label = "Brackets"  // is square  :: OKAY
                    )
                    IconButtonWithLabel(
                        onClick = onSquaresSmallClicked,
                        iconResId = R.drawable.circleforandroid,
                        label = "Losange" // circle  :: OKAY
                    )
                    IconButtonWithLabel(
                        onClick = onCircles2Clicked,
                        iconResId = R.drawable.icon_sun,
                        label = "Fuego"  // circles 2  // OKAY
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(47.dp)  // was 16.dp
                ) {
                    IconButtonWithLabel(
                        onClick = onSquares2Clicked,
                        iconResId = R.drawable.square_green_yellow,
                        label = "Squares"  // square 2  // OKAY
                    )
                    IconButtonWithLabel(
                        onClick = onSquares3Clicked,
                        iconResId = R.drawable.carre_rond,
                        label = "Archipel"  // square 3 // OKAY
                    )
                    IconButtonWithLabel(
                        onClick = onSquares4Clicked,
                        iconResId = R.drawable.rond,
                        label = "Fluo"  // square 4  :: OKAY
                    )
                    IconButtonWithLabel(
                        onClick = onCircles4Clicked,
                        iconResId = R.drawable.icon_square_purple,
                        label = "Island"  // circle 4  // OKAY
                    )
                }
            }
        }
    }
}