package com.example.quizito.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.quizito.R
import com.example.quizito.data.repositories.models.ScoreDto
import com.example.quizito.domain.use_cases.GetUserScoresUseCase
import com.google.firebase.auth.FirebaseAuth
import com.example.quizito.domain.use_cases.GetThemeTitleUseCase
import androidx.compose.ui.graphics.Color

@Composable
fun ScoresScreen(
    getUserScoresUseCase: GetUserScoresUseCase,
    getThemeTitleUseCase: GetThemeTitleUseCase, // ✅ nouveau use case
    onBackClick: () -> Unit
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"
    var scores by remember { mutableStateOf<List<ScoreDto>>(emptyList()) }
    var themeTitles by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

    LaunchedEffect(userId) {
        scores = getUserScoresUseCase(userId)

        // ✅ récupérer les titres des thèmes
        val titlesMap = mutableMapOf<String, String>()
        for (score in scores) {
            titlesMap[score.themeId] = getThemeTitleUseCase(score.themeId)
        }
        themeTitles = titlesMap
    }
    Image(
        painter = painterResource(id = R.drawable.score),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer // ou Color.White
            ),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Mes Scores",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White                )
            }
        }



        if (scores.isEmpty()) {
            Text("Aucun score enregistré 😕")
        } else {
            scores.forEach { score ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Thème : ${themeTitles[score.themeId] ?: score.themeId}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "Score : ${score.score}/${score.total}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Button(onClick = onBackClick) {
            Text("Retour")
        }
    }
}
