package com.example.quizito.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.quizito.R
import android.R.attr.fontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onStartClicked: () -> Unit  // Callback pour naviguer vers le dashboard
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.home),
                contentScale = ContentScale.Crop
            )
    ) {
        Button(
            onClick = { onStartClicked() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4448B)),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp), // augmente la valeur pour remonter le bouton
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Commencer",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                ),
                color = Color.White
            )
        }
    }
}
