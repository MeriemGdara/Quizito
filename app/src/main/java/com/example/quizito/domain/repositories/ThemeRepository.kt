package com.example.quizito.domain.repositories

import com.example.quizito.domain.models.Theme

interface ThemeRepository {
    suspend fun getThemes(): List<Theme>
}
