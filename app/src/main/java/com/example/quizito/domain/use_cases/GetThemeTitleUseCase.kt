package com.example.quizito.domain.use_cases
import com.example.quizito.data.data_sources.remote.ThemeRemoteDataSource

class GetThemeTitleUseCase(
    private val themeRemoteDataSource: ThemeRemoteDataSource
) {
    suspend operator fun invoke(themeId: String): String {
        return themeRemoteDataSource.getThemeTitleById(themeId)
    }
}
