// path: data/repositories/QuizRepositoryImpl.kt
package com.example.quizito.data.repositories

import com.example.quizito.data.data_sources.local.QuizLocalDataSource
import com.example.quizito.data.data_sources.remote.QuizRemoteDataSource
import com.example.quizito.domain.models.Quiz
import com.example.quizito.domain.repositories.QuizRepository

class QuizRepositoryImpl(
    private val remote: QuizRemoteDataSource,
    private val local: QuizLocalDataSource
) : QuizRepository {

    override suspend fun getQuizzesByTheme(themeId: String): List<Quiz> {
        // Try cache first
        val cached = local.getQuizzes(themeId)?.map { it.toDomain() }
        if (!cached.isNullOrEmpty()) return cached

        // Fallback to remote
        val dtos = remote.getQuizzesByTheme(themeId)
        local.saveQuizzes(themeId, dtos)
        return dtos.map { it.toDomain() }
    }

    override suspend fun getQuizById(quizId: String): Quiz? {
        val cached = local.getQuiz(quizId)?.toDomain()
        if (cached != null) return cached

        val dto = remote.getQuizById(quizId)
        if (dto != null) {
            local.saveQuizzes(dto.themeId, listOf(dto))
        }
        return dto?.toDomain()
    }
}
