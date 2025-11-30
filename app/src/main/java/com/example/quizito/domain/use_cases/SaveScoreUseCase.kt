package com.example.quizito.domain.use_cases

import com.example.quizito.data.repositories.models.ScoreDto
import com.example.quizito.data.data_sources.remote.ScoreRemoteDataSource

class SaveScoreUseCase(
    private val remoteDataSource: ScoreRemoteDataSource
) {
    suspend operator fun invoke(score: ScoreDto) {
        remoteDataSource.saveScore(score)
    }
}
