package com.example.quizito.ui.screens.ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import com.example.quizito.domain.use_cases.GetQuizzesByThemeUseCase
import com.example.quizito.domain.models.Quiz

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizzesByThemeUseCase: GetQuizzesByThemeUseCase
) : ViewModel() {

    var quizzes by mutableStateOf(emptyList<Quiz>()) // ✅ corrigé
        private set

    var currentIndex by mutableStateOf(0)
        private set

    var selectedAnswer by mutableStateOf<Int?>(null)
        private set

    var score by mutableStateOf(0)
        private set

    fun loadQuizzes(themeId: String) {
        println("Quizzes chargés: ${quizzes.size}")
        quizzes.forEach { println("Quiz: ${it.question} - options: ${it.options}") }

        viewModelScope.launch {
            try {
                val list = getQuizzesByThemeUseCase(themeId)
                println("Quizzes fetched=${list.size}")
                list.forEach { println("VM quiz: ${it.id} / ${it.themeId} / ${it.question}") }
                quizzes = list
                currentIndex = 0
                score = 0
                selectedAnswer = null
            } catch (e: Exception) {
                println("Error loading quizzes: ${e.message}")
                quizzes = emptyList()
            }
        }
    }

    fun selectAnswer(index: Int) {
        selectedAnswer = index
    }

    fun submitAnswer() {
        val quiz = quizzes.getOrNull(currentIndex) ?: run {
            println("submitAnswer: no quiz at index=$currentIndex, size=${quizzes.size}")
            return
        }
        if (selectedAnswer == quiz.correctAnswerIndex) {
            score++
        }
        currentIndex++
        selectedAnswer = null
    }

    fun isFinished(): Boolean = currentIndex >= quizzes.size
}
