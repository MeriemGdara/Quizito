package com.example.quizito.data.data_sources.remote

import com.example.quizito.data.repositories.models.ScoreDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ScoreRemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore
) {

    /**
     * Enregistre le score d’un utilisateur pour un quiz donné.
     */

    suspend fun saveScore(score: ScoreDto) {
        println("Tentative d'enregistrement score: $score")
        db.collection("Scores")
            .add(score)
            .await()
        println("Score ajouté avec succès")
    }


    /**
     * Récupère tous les scores d’un utilisateur.
     */
    suspend fun getScoresByUser(userId: String): List<ScoreDto> {
        val snapshot = db.collection("Scores")
            .whereEqualTo("userId", userId)
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject(ScoreDto::class.java) }
    }

    /**
     * Récupère les scores d’un utilisateur pour un thème précis.
     */
    suspend fun getScoresByUserAndTheme(userId: String, themeId: String): List<ScoreDto> {
        val snapshot = db.collection("Scores")
            .whereEqualTo("userId", userId)
            .whereEqualTo("themeId", themeId)
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject(ScoreDto::class.java) }
    }
}
