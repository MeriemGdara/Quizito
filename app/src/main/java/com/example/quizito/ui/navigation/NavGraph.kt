package com.example.quizito.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizito.domain.use_cases.AuthUseCases
import com.example.quizito.domain.use_cases.SaveScoreUseCase
import com.example.quizito.domain.use_cases.GetUserScoresUseCase
import com.example.quizito.domain.use_cases.GetThemeTitleUseCase
import com.example.quizito.ui.screens.*

@Composable
fun NavGraph(
    authUseCases: AuthUseCases,
    saveScoreUseCase: SaveScoreUseCase,
    getUserScoresUseCase: GetUserScoresUseCase,
    getThemeTitleUseCase: GetThemeTitleUseCase,   // ✅ manquait ici
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                authUseCases = authUseCases,
                onLoginSuccess = { navController.navigate("home") },
                onNavigateToSignUp = { navController.navigate("signup") }
            )
        }

        composable("signup") {
            SignUpScreen(
                authUseCases = authUseCases,
                onSignUpSuccess = {
                    navController.navigate("home") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }

        composable("home") {
            HomeScreen(onStartClicked = { navController.navigate("themes") })
        }

        composable("themes") {
            ThemeScreen(onThemeClick = { themeId ->
                navController.navigate("Quiz/$themeId")
            })
        }

        composable("Quiz/{themeId}") { backStackEntry ->
            val themeId = backStackEntry.arguments?.getString("themeId") ?: return@composable
            QuizQuestionScreen(
                themeId = themeId,
                onQuizFinished = { score, total ->
                    navController.navigate("QuizFinished/$score/$total/$themeId")
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("QuizFinished/{score}/{total}/{themeId}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            val total = backStackEntry.arguments?.getString("total")?.toIntOrNull() ?: 0
            val themeId = backStackEntry.arguments?.getString("themeId") ?: ""

            QuizFinishedScreen(
                score = score,
                total = total,
                themeId = themeId,
                saveScoreUseCase = saveScoreUseCase,
                onThemeClick = {
                    navController.navigate("themes") {
                        popUpTo("themes") { inclusive = false }
                    }
                },
                onScoresClick = { navController.navigate("scores") }
            )
        }

        composable("scores") {
            ScoresScreen(
                getUserScoresUseCase = getUserScoresUseCase,
                getThemeTitleUseCase = getThemeTitleUseCase,   // ✅ injection
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
