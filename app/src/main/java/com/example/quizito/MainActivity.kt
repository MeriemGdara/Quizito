package com.example.quizito

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.quizito.domain.use_cases.AuthUseCases
import com.example.quizito.domain.use_cases.SaveScoreUseCase
import com.example.quizito.domain.use_cases.GetUserScoresUseCase
import com.example.quizito.domain.use_cases.GetThemeTitleUseCase
import com.example.quizito.ui.navigation.NavGraph
import com.example.quizito.ui.theme.QuizitoTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authUseCases: AuthUseCases

    @Inject
    lateinit var saveScoreUseCase: SaveScoreUseCase

    @Inject
    lateinit var getUserScoresUseCase: GetUserScoresUseCase

    @Inject
    lateinit var getThemeTitleUseCase: GetThemeTitleUseCase  // ✅ injection nécessaire pour ScoresScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizitoTheme {
                NavGraph(
                    authUseCases = authUseCases,
                    saveScoreUseCase = saveScoreUseCase,
                    getUserScoresUseCase = getUserScoresUseCase,
                    getThemeTitleUseCase = getThemeTitleUseCase   // ✅ passer au NavGraph
                )
            }
        }
    }
}
