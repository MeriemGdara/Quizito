package com.example.quizito.data.repositories

import com.example.quizito.data.data_sources.remote.ThemeRemoteDataSource  // Import ajouté : Pour ThemeRemoteDataSource
import com.example.quizito.data.repositories.models.ThemeDto  // Import ajouté : Pour ThemeDto (sous-package)
import com.example.quizito.domain.models.Theme  // Import ajouté : Pour Theme
import com.example.quizito.domain.repositories.ThemeRepository  // Import ajouté : Pour ThemeRepository
import android.content.Context

class ThemeRepositoryImpl(
    private val remote: ThemeRemoteDataSource,
    private val context: Context
) : ThemeRepository {

    override suspend fun getThemes(): List<Theme> {
        return remote.getThemes().map { it.toDomain(context) }
    }
}
