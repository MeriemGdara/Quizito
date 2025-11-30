/**
 * Ce fichier contient la logique pour interagir avec Firebase Firestore.
 * Il fournit les méthodes signUp, login et logout pour la couche Repository.
 */
package com.example.quizito.data.data_sources.remote

import com.example.quizito.common.constants
import com.example.quizito.common.Resource
import com.example.quizito.domain.models.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*

class FirebaseAuthDataSource {

    private val db = FirebaseFirestore.getInstance()

    // Inscription d'un utilisateur avec un username unique
    suspend fun signUp(username: String): Resource<User> {
        return try {
            // Vérifier si le username existe déjà
            val snapshot = db.collection(constants.USERS_COLLECTION)
                .whereEqualTo("username", username)
                .get()
                .await()

            if (!snapshot.isEmpty) return Resource.Error("Username already exists")

            // Générer un id unique pour l'utilisateur
            val userId = UUID.randomUUID().toString()
            val user = User(userId, username)

            // Ajouter l'utilisateur dans Firestore
            db.collection(constants.USERS_COLLECTION)
                .document(userId)
                .set(user)
                .await()

            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    // Connexion d'un utilisateur via son username
    suspend fun login(username: String): Resource<User> {
        return try {
            val snapshot = db.collection(constants.USERS_COLLECTION)
                .whereEqualTo("username", username)
                .get()
                .await()

            if(snapshot.isEmpty) return Resource.Error("User not found")

            val doc = snapshot.documents[0]
            val user = User(doc.id, doc.getString("username") ?: "")
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    // Déconnexion
    fun logout() {
        // Pour le moment rien de spécifique
    }
}
