// path: domain/repositories/QuizRepository.kt
package com.example.quizito.domain.repositories

import com.example.quizito.domain.models.Quiz

interface QuizRepository {
    suspend fun getQuizzesByTheme(themeId: String): List<Quiz>
    suspend fun getQuizById(quizId: String): Quiz?
}
