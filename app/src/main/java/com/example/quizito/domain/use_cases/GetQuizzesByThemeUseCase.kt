// path: domain/use_cases/GetQuizzesByThemeUseCase.kt
package com.example.quizito.domain.use_cases

import com.example.quizito.domain.models.Quiz
import com.example.quizito.domain.repositories.QuizRepository

class GetQuizzesByThemeUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(themeId: String): List<Quiz> {
        return repository.getQuizzesByTheme(themeId)
    }
}
