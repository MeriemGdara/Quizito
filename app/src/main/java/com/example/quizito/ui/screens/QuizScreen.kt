package com.example.quizito.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.graphics.Color
import com.example.quizito.ui.screens.ViewModel.QuizViewModel
import com.example.quizito.R

@Composable
fun QuizQuestionScreen(
    themeId: String,
    viewModel: QuizViewModel = hiltViewModel(),
    onQuizFinished: (Int, Int) -> Unit,
    onBackClick: () -> Unit // ✅ callback pour retour
) {
    LaunchedEffect(themeId) { viewModel.loadQuizzes(themeId) }

    val quizzes = viewModel.quizzes
    val currentIndex = viewModel.currentIndex
    val selectedAnswer = viewModel.selectedAnswer

    if (quizzes.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.bg1),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Aucun quiz disponible pour ce thème 😕", style = MaterialTheme.typography.bodyLarge)
            }
        }
    } else if (viewModel.isFinished() || currentIndex !in quizzes.indices) {
        onQuizFinished(viewModel.score, quizzes.size)
    } else {
        val quiz = quizzes[currentIndex]
        Box(modifier = Modifier.fillMaxSize()) {
            // ✅ Image en background
            Image(
                painter = painterResource(id = R.drawable.bg1),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top, // ✅ centré verticalement
                horizontalAlignment = Alignment.CenterHorizontally // ✅ centré horizontalement
            ) {
                // ✅ Flèche retour en haut
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

                Spacer(Modifier.height(60.dp))

                Text(
                    text = "Question ${currentIndex + 1}/${quizzes.size}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = quiz.question,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                quiz.options.forEachIndexed { index, option ->
                    val isSelected = selectedAnswer == index
                    OutlinedButton(
                        onClick = { viewModel.selectAnswer(index) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) Color(0xFF496D3A).copy(alpha = 0.3f)
                            else MaterialTheme.colorScheme.surface
                        )
                    ) { Text(option, style = MaterialTheme.typography.bodyLarge) }
                }

                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = { viewModel.submitAnswer() },
                    enabled = selectedAnswer != null,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF496D3A)), // ✅ couleur personnalisée
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Valider", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
