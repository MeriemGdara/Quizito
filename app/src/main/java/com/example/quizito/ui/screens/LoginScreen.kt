package com.example.quizito.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quizito.R
import com.example.quizito.domain.use_cases.AuthUseCases
import kotlinx.coroutines.launch
import androidx.compose.ui.draw.paint
import androidx.compose.ui.unit.sp
@Composable
fun LoginScreen(
    authUseCases: AuthUseCases,
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Couleur du bouton S'INSCRIRE
    val green = Color(0xFF5A8B55)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable._login),
                contentScale = ContentScale.Crop
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

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

                    // 🔵 Titre centré + bold
                    Text(
                        "Heureux de te revoir !",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 🔵 Champ username
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Entre ton pseudo") },
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = green,
                            cursorColor = green
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 🔵 Bouton Login
                    Button(
                        onClick = {
                            if (username.isNotEmpty()) {
                                scope.launch {
                                    when (val result = authUseCases.login(username)) {
                                        is com.example.quizito.common.Resource.Success -> {
                                            Toast.makeText(context, "Connexion réussie", Toast.LENGTH_SHORT).show()
                                            onLoginSuccess()
                                        }
                                        is com.example.quizito.common.Resource.Error -> {
                                            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                                        }
                                        else -> {}
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Entre un pseudo", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = green,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Text("Je me connecte", style = MaterialTheme.typography.titleMedium, fontSize = 25.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔵 Texte centré "Pas de compte ?"
                    Text(
                        text = "Pas encore de compte ? Inscris-toi",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToSignUp() },
                        textAlign = TextAlign.Center,
                        color = green
                    )
                }
            }
        }
    }
}
