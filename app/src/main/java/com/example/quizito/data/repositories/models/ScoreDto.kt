package com.example.quizito.data.repositories.models

data class ScoreDto(
    val userId: String = "",
    val themeId: String = "",
    val score: Int = 0,
    val total: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
