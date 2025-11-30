// path: domain/use_cases/GetQuizByIdUseCase.kt
package com.example.quizito.domain.use_cases

import com.example.quizito.domain.models.Quiz
import com.example.quizito.domain.repositories.QuizRepository

class GetQuizByIdUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(quizId: String): Quiz? {
        return repository.getQuizById(quizId)
    }
}
