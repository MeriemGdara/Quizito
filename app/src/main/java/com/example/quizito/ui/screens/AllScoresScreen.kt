package com.example.quizito.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.quizito.R
import com.example.quizito.data.repositories.models.ScoreDto
import com.example.quizito.domain.use_cases.GetThemeTitleUseCase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import androidx.compose.ui.unit.sp

@Composable
fun AllScoresScreen(
    getThemeTitleUseCase: GetThemeTitleUseCase,
    onBackClick: () -> Unit
) {
    var topScores by remember { mutableStateOf<Map<String, List<ScoreDto>>>(emptyMap()) }
    var usernames by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()

        // ✅ Charger les scores
        val snapshot = db.collection("Scores").get().await()
        val scores = snapshot.documents.mapNotNull { it.toObject(ScoreDto::class.java) }

        // ✅ Grouper par thème et trier
        val grouped = scores.groupBy { it.themeId }
            .mapValues { entry ->
                entry.value.sortedByDescending { it.score }.take(3)
            }
        topScores = grouped

        // ✅ Récupérer les usernames en utilisant l'ID du document
        val ids = scores.map { it.userId }.distinct()
        val usersSnapshot = db.collection("users").get().await()

        val map = usersSnapshot.documents.associate { doc ->
            val id = doc.id // ⚠️ utiliser l'ID du document
            val username = doc.getString("username") ?: id
            id to username
        }
        usernames = map
    }
    Image(
        painter = painterResource(id = R.drawable.allscore),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Retour",
                    tint = Color.Black
                )
            }
        }
        Text(
            "Classement",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp // taille plus grande
            ),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(35.dp))

        if (topScores.isEmpty()) {
            Text("Aucun score disponible 😕")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                topScores.forEach { (themeId, scores) ->
                    item {
                        val themeTitle by produceState(initialValue = themeId) {
                            value = getThemeTitleUseCase(themeId)
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = themeTitle,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 30.sp
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Divider()

                                scores.forEachIndexed { index, score ->
                                    val username = usernames[score.userId] ?: score.userId
                                    Text(
                                        text = "${index + 1}. $username → ${score.score}/${score.total}",
                                        fontSize = 20.sp, // taille plus grande
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
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
