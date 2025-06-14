package com.example.themoviedatabase.userInterface.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.themoviedatabase.userInterface.navigation.Screen
import com.example.themoviedatabase.userInterface.viewmodel.MovieListViewModel
import kotlinx.coroutines.delay
import androidx.compose.foundation.Canvas
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SplashScreen(navController: NavController, viewModel: MovieListViewModel) {
    var isCubeFormed by remember { mutableStateOf(false) }
    var showWelcome by remember { mutableStateOf(false) }
    val movies by viewModel.movies.collectAsState()

    // Trigger cube formation when data is loaded
    LaunchedEffect(movies) {
        if (movies.isNotEmpty()) {
            isCubeFormed = true
            delay(1000) // Show cube for 1 second
            showWelcome = true
            delay(1000) // Blink "Welcome" for 1 second
            navController.navigate(Screen.MovieList.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    // Animation for floating lines
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angleAnimation"
    )

    // Animation for cube formation
    val cubeProgress by animateFloatAsState(
        targetValue = if (isCubeFormed) 1f else 0f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "cubeProgress"
    )

    // Blinking effect for "Welcome"
    val welcomeAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "welcomeAlpha"
    )

    // Pulsing effect for "Awesomeness Loading..."
    val loadingScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "loadingScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF1A237E), Color.Black),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Starry background
        Canvas(modifier = Modifier.fillMaxSize()) {
            repeat(100) {
                val x = (0..size.width.toInt()).random().toFloat()
                val y = (0..size.height.toInt()).random().toFloat()
                drawCircle(Color.White.copy(alpha = 0.2f), radius = 2f, center = Offset(x, y))
            }
        }

        // Four lines orbiting or forming cube
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .scale(1f + cubeProgress * 0.2f)
                .rotate(angle * (1f - cubeProgress)) // Stop rotation when cube forms
        ) {
            val lineLength = 80f
            val lineWidth = 8f
            val glowColor = Color(0xFF00E676)

            fun DrawScope.drawLineAtOrbit(index: Int, progress: Float) {
                val orbitRadius = 60f
                val theta = (angle + index * 90f) * (1f - progress) * Math.PI / 180
                val x = (size.width / 2 + orbitRadius * cos(theta)).toFloat()
                val y = (size.height / 2 + orbitRadius * sin(theta)).toFloat()

                drawRect(
                    color = glowColor,
                    topLeft = Offset(x - lineLength / 2, y - lineWidth / 2),
                    size = Size(lineLength, lineWidth),
                    alpha = 0.8f
                )
            }

            fun DrawScope.drawCubeLine(index: Int, progress: Float) {
                val cubeSize = 80f
                val positions = listOf(
                    Offset(size.width / 2 - cubeSize / 2, size.height / 2 - cubeSize / 2), // Top-left
                    Offset(size.width / 2 + cubeSize / 2, size.height / 2 - cubeSize / 2), // Top-right
                    Offset(size.width / 2 + cubeSize / 2, size.height / 2 + cubeSize / 2), // Bottom-right
                    Offset(size.width / 2 - cubeSize / 2, size.height / 2 + cubeSize / 2)  // Bottom-left
                )
                val offsets = when (index) {
                    0 -> Offset(positions[0].x, positions[0].y) // Top horizontal
                    1 -> Offset(positions[1].x, positions[1].y) // Right vertical
                    2 -> Offset(positions[2].x - cubeSize, positions[2].y) // Bottom horizontal
                    else -> Offset(positions[3].x, positions[3].y - cubeSize) // Left vertical
                }
                drawRect(
                    color = glowColor,
                    topLeft = offsets,
                    size = Size(
                        if (index % 2 == 0) cubeSize * progress else lineWidth,
                        if (index % 2 == 0) lineWidth else cubeSize * progress
                    ),
                    alpha = 0.8f
                )
            }

            repeat(4) { index ->
                if (cubeProgress < 1f) {
                    drawLineAtOrbit(index, cubeProgress)
                } else {
                    drawCubeLine(index, cubeProgress)
                }
            }
        }

        // Text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 280.dp)
        ) {
            if (!showWelcome) {
                Text(
                    text = "Awesomeness Loading...",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.scale(loadingScale)
                )
            } else {
                Text(
                    text = "Welcome",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .offset(y = (-80).dp) // Center in cube
                        .alpha(welcomeAlpha)
                )
            }
        }
    }
}