package com.example.quizito.data.data_sources.remote

import com.example.quizito.data.repositories.models.ThemeDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ThemeRemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore
) {
    suspend fun getThemes(): List<ThemeDto> {
        val snapshot = db.collection("themes").get().await()
        return snapshot.documents.mapNotNull { it.toObject(ThemeDto::class.java) }
    }
    suspend fun getThemeTitleById(themeId: String): String {
        val snapshot = db.collection("themes")
            .whereEqualTo("id", themeId)   // ✅ recherche par champ "id"
            .get()
            .await()

        val doc = snapshot.documents.firstOrNull()
        return doc?.getString("title") ?: themeId
    }
}
