package com.example.quizito.domain.use_cases

import com.example.quizito.domain.models.Theme  // Import ajouté : Pour Theme
import com.example.quizito.domain.repositories.ThemeRepository  // Import ajouté : Pour ThemeRepository

class GetThemesUseCase(
    private val repository: ThemeRepository  // Corrigé : Était "Theme", maintenant "ThemeRepository"
) {
    suspend operator fun invoke(): List<Theme> {
        return repository.getThemes()
    }
}