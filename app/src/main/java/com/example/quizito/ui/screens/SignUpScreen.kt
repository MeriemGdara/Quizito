package com.example.quizito.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.quizito.domain.use_cases.AuthUseCases
import androidx.compose.foundation.clickable
import com.example.quizito.ui.screens.ViewModel.SignUpViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.quizito.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.paint
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SignUpScreen(
    authUseCases: AuthUseCases,
    onSignUpSuccess: () -> Unit,  // Renommé en onNavigateToHome
    onNavigateToLogin: () -> Unit,
    viewModel: SignUpViewModel = viewModel()
) {
    var username by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val green = Color(0xFF5A8B55)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable._signup),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 170.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(0.9f),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(32.dp),
                elevation = CardDefaults.cardElevation(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Commencer l'aventure",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Choisis un pseudo") },
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = green,
                            cursorColor = green
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (username.isNotEmpty() && !isLoading) {
                                isLoading = true
                                message = ""  // Réinitialiser le message

                                try {
                                    viewModel.signUp(username,
                                        onResult = { success, msg ->
                                            message = msg
                                            isLoading = false  // Toujours remettre à false
                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                            Log.d("SignUpScreen", "onResult appelé: Success=$success, Message=$msg")
                                        },
                                        onAuthSuccess = {
                                            Log.d("SignUpScreen", "Navigation déclenchée après Auth succès")
                                            onSignUpSuccess()  // Navigation vers home
                                        }
                                    )
                                } catch (e: Exception) {
                                    Log.e("SignUpScreen", "Erreur inattendue: ${e.message}")
                                    message = "Erreur inattendue. Réessayez."
                                    isLoading = false
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = green,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(50.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        } else {
                            Text(
                                text = "Je m'inscris",
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center,
                                fontSize = 25.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Déjà un compte ? Clique ici",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToLogin() },
                        color = green,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (message.isNotEmpty()) {
                        Text(message, color = if (message.contains("succès")) Color.Green else Color.Red)
                    }
                }
            }
        }
    }
}