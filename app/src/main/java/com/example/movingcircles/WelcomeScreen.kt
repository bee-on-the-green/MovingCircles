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
                        onCircles4Clicked = { startActivity(Intent(this, MainActivityCircle4::class.java)) },
                        onSquares5Clicked = { startActivity(Intent(this, MainActivitySquare5::class.java)) },
                        onSquares6Clicked = { startActivity(Intent(this, MainActivitySquare6::class.java)) }
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
            modifier = Modifier.size(70.dp), // was 60
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
    onCircles4Clicked: () -> Unit,
    onSquares5Clicked: () -> Unit, // Keep this parameter
    onSquares6Clicked: () -> Unit // Keep this parameter
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.pool_of_char),
                contentDescription = "Pool of Characters",
                modifier = Modifier
                    .size(470.dp)
                    .offset(y = (-132).dp)
            )

            Image(
                painter = painterResource(id = R.drawable.welcome_image),
                contentDescription = "Welcome Image",
                modifier = Modifier
                    .size(820.dp)
                    .padding(top = 50.dp, bottom = 20.dp)
                    .offset(y = (-320).dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp)  // was 47 then 30
                ) {
                    IconButtonWithLabel(
                        onClick = onCirclesClicked,
                        iconResId = R.drawable.circle_icon,  // circles
                        label = "B&W Disk"
                    )
                    IconButtonWithLabel(
                        onClick = onSquaresClicked,
                        iconResId = R.drawable.square_icon,  // square
                        label = "Brackets"
                    )
                    IconButtonWithLabel(
                        onClick = onSquaresSmallClicked,
                        iconResId = R.drawable.circleforandroid,  //
                        label = "Losange"
                    )
                    IconButtonWithLabel(
                        onClick = onCircles2Clicked,
                        iconResId = R.drawable.icon_sun,  // circle 2
                        label = "Fuego"
                    )
                    IconButtonWithLabel( // Moved from third row
                        onClick = onSquares5Clicked,
                        iconResId = R.drawable.multiple_squares,  // squere 5
                        label = "Drops"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp)  // was 47
                ) {
                    IconButtonWithLabel(
                        onClick = onSquares2Clicked,
                        iconResId = R.drawable.square_green_yellow,  // square2
                        label = "Squares"
                    )
                    IconButtonWithLabel(
                        onClick = onSquares3Clicked,
                        iconResId = R.drawable.carre_rond,  // square3
                        label = "Archipel"
                    )
                    IconButtonWithLabel(
                        onClick = onSquares4Clicked,
                        iconResId = R.drawable.rond,  // square 4
                        label = "Fluo"
                    )
                    IconButtonWithLabel(
                        onClick = onCircles4Clicked,
                        iconResId = R.drawable.icon_square_purple, // circle 4
                        label = "Island"
                    )
                    IconButtonWithLabel( // Moved from third row
                        onClick = onSquares6Clicked,
                        iconResId = R.drawable.pacman,  // square 6
                        label = "Neo"
                    )
                }
                // The third Spacer and Row are removed as requested.
            }
        }
    }
}