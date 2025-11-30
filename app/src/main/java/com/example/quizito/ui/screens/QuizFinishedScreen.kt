package com.example.quizito.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.quizito.R
import androidx.compose.ui.unit.Dp
import com.example.quizito.data.repositories.models.ScoreDto
import com.example.quizito.domain.use_cases.SaveScoreUseCase
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
@Composable
fun QuizFinishedScreen(
    score: Int,
    total: Int,
    themeId: String,
    saveScoreUseCase: SaveScoreUseCase,
    marginTop: Dp = 170.dp,
    onThemeClick: () -> Unit,
    onScoresClick: () -> Unit // ✅ nouvelle callback
) {
    val message = if (score >= total / 2) {
        "Bravo champion !"
    } else {
        "Tu peux mieux faire champion !"
    }

    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"

    LaunchedEffect(Unit) {
        val scoreDto = ScoreDto(
            userId = userId,
            themeId = themeId,
            score = score,
            total = total
        )
        try {
            saveScoreUseCase(scoreDto)
            Log.d("Quizito", "Score enregistré: $score/$total pour $userId")
        } catch (e: Exception) {
            Log.e("Quizito", "Erreur enregistrement score: ${e.message}")
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = marginTop)
                .align(Alignment.TopCenter),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
            ),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Quiz terminé 🎉",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Score : $score / $total",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Button(
                    onClick = onThemeClick,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        "Choisir un autre thème",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                // ✅ Bouton pour voir les scores
                Button(
                    onClick = onScoresClick,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        "Voir mes scores",
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}
