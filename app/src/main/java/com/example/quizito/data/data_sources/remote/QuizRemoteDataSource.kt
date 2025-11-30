// path: data/data_sources/remote/QuizRemoteDataSource.kt
package com.example.quizito.data.data_sources.remote

import com.example.quizito.data.repositories.models.QuizDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class QuizRemoteDataSource(
    private val db: FirebaseFirestore
) {

    suspend fun getQuizzesByTheme(themeId: String): List<QuizDto> {
        val snapshot = db.collection("Quiz")
            .whereEqualTo("themeId", themeId)
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject(QuizDto::class.java) }
            .map { dto ->
                // Ensure Firestore document ID is set if not present
                if (dto.id.isBlank()) {
                    val docId = snapshot.documents
                        .firstOrNull { it.toObject(QuizDto::class.java)?.question == dto.question }?.id
                    dto.copy(id = docId ?: dto.id)
                } else dto
            }
        println("Firestore Quiz: themeId='$themeId', docsFound=${snapshot.size()}")
    }

    suspend fun getQuizById(quizId: String): QuizDto? {
        val doc = db.collection("Quiz").document(quizId).get().await()
        val dto = doc.toObject(QuizDto::class.java)
        return dto?.copy(id = if (dto.id.isBlank()) doc.id else dto.id)
    }
}
