// path: domain/models/Quiz.kt
package com.example.quizito.domain.models

data class Quiz(
    val id: String,
    val themeId: String,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
)
