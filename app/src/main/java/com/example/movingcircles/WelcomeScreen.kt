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
                        onSquares6Clicked = { startActivity(Intent(this, MainActivitySquare6::class.java)) },
                        onSquares7Clicked = { startActivity(Intent(this, MainActivitySquare7::class.java)) },
                        onSquares8Clicked = { startActivity(Intent(this, MainActivitySquare8::class.java)) },
                        onSquares9Clicked = { startActivity(Intent(this, MainActivitySquare9::class.java)) },
                        onSquares10Clicked = { startActivity(Intent(this, MainActivitySquare10::class.java)) }, // New activity for Square10
                        onCircles5Clicked = { startActivity(Intent(this, MainActivityCircle5::class.java)) } // New activity for Circle5
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
        modifier = modifier.width(92.dp)
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
            fontSize = 10.sp,
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
    onSquares5Clicked: () -> Unit,
    onSquares6Clicked: () -> Unit,
    onSquares7Clicked: () -> Unit,
    onSquares8Clicked: () -> Unit,
    onSquares9Clicked: () -> Unit,
    onSquares10Clicked: () -> Unit, // New parameter for Square10
    onCircles5Clicked: () -> Unit // New parameter for Circle5
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.pool_of_char),
            contentDescription = "Pool of Characters",
            modifier = Modifier
                .size(530.dp)
                .offset(y = (-480).dp, x = 0.dp)
                .align(Alignment.Center)
        )

        Image(
            painter = painterResource(id = R.drawable.welcome_image),
            contentDescription = "Welcome Image",
            modifier = Modifier
                .size(600.dp)
                .padding(top = 50.dp, bottom = 20.dp)
                .offset(y = (-170).dp, x = 0.dp)
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),  // was 200
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    IconButtonWithLabel(
                        onClick = onCirclesClicked,
                        iconResId = R.drawable.circle_icon,
                        label = "Ellipse"
                    )
                    IconButtonWithLabel(
                        onClick = onSquaresClicked,
                        iconResId = R.drawable.square_icon,
                        label = "Trees"
                    )
                    IconButtonWithLabel(
                        onClick = onSquaresSmallClicked,
                        iconResId = R.drawable.circleforandroid,
                        label = "Losange"
                    )
                    IconButtonWithLabel(
                        onClick = onCircles2Clicked,
                        iconResId = R.drawable.icon_sun,
                        label = "Fuego"
                    )
                    IconButtonWithLabel(
                        onClick = onSquares7Clicked,
                        iconResId = R.drawable.squares_and_dots_midgray,
                        label = "Magenta"
                    )
                    IconButtonWithLabel(
                        onClick = onSquares6Clicked,
                        iconResId = R.drawable.pacman,
                        label = "Neo"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))  // was 16
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    IconButtonWithLabel(
                        onClick = onSquares2Clicked,
                        iconResId = R.drawable.square_green_yellow,
                        label = "Dice"
                    )
                    IconButtonWithLabel(
                        onClick = onSquares3Clicked,
                        iconResId = R.drawable.carre_rond,
                        label = "Archipel"
                    )
                    IconButtonWithLabel(
                        onClick = onSquares4Clicked,
                        iconResId = R.drawable.rond,
                        label = "Neon glow"
                    )
                    IconButtonWithLabel(
                        onClick = onCircles4Clicked,
                        iconResId = R.drawable.icon_square_purple,
                        label = "Island"
                    )
                    IconButtonWithLabel(
                        onClick = onSquares5Clicked,
                        iconResId = R.drawable.multiple_squares,
                        label = "Drops"
                    )
                    IconButtonWithLabel(
                        onClick = onSquares8Clicked,
                        iconResId = R.drawable.intricate,
                        label = "Patchwork"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))  // was 16
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    IconButtonWithLabel(
                        onClick = onSquares9Clicked,
                        iconResId = R.drawable.labirynte,
                        label = "Square9"
                    )
                    IconButtonWithLabel(
                        onClick = onSquares10Clicked,
                        iconResId = R.drawable.squaresdots,
                        label = "Square10"
                    )
                    IconButtonWithLabel(
                        onClick = onCircles5Clicked,
                        iconResId = R.drawable.oeil,
                        label = "Oeil"
                    )
                }
            }
        }
    }
}
