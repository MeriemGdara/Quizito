package com.example.quizito.domain.use_cases

import com.example.quizito.data.repositories.models.ScoreDto
import com.example.quizito.data.data_sources.remote.ScoreRemoteDataSource

class GetUserScoresUseCase(
    private val remoteDataSource: ScoreRemoteDataSource
) {
    /**
     * Récupère tous les scores d’un utilisateur connecté.
     */
    suspend operator fun invoke(userId: String): List<ScoreDto> {
        return remoteDataSource.getScoresByUser(userId)
    }
}
