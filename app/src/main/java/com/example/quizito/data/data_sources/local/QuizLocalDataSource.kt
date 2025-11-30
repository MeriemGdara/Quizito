// path: data/data_sources/local/QuizLocalDataSource.kt
package com.example.quizito.data.data_sources.local

import com.example.quizito.data.repositories.models.QuizDto

// Optional stub for caching (Room/DataStore later).
class QuizLocalDataSource {
    private val cacheByTheme = mutableMapOf<String, List<QuizDto>>()
    private val cacheById = mutableMapOf<String, QuizDto>()

    fun saveQuizzes(themeId: String, quizzes: List<QuizDto>) {
        cacheByTheme[themeId] = quizzes
        quizzes.forEach { cacheById[it.id] = it }
    }

    fun getQuizzes(themeId: String): List<QuizDto>? = cacheByTheme[themeId]

    fun getQuiz(quizId: String): QuizDto? = cacheById[quizId]
}
