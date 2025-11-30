// path: data/repositories.models/QuizDto.kt
package com.example.quizito.data.repositories.models

import com.example.quizito.domain.models.Quiz

data class QuizDto(
    val id: String = "",
    val themeId: String = "",
    val question: String = "",
    val options: List<String> = emptyList(),
    val correctAnswerIndex: Int = -1,
) {
    fun toDomain(): Quiz = Quiz(
        id = id,
        themeId = themeId,
        question = question,
        options = options,
        correctAnswerIndex = correctAnswerIndex,
    )
}
