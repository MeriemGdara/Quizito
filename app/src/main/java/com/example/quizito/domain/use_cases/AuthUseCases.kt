/**
 * Ce fichier contient les cas d'utilisation pour l'authentification.
 * Il sert d'intermédiaire entre la couche UI et le Repository.
 */
package com.example.quizito.domain.use_cases

import com.example.quizito.data.repositories.AuthRepository
import com.example.quizito.domain.models.User
import com.example.quizito.common.Resource

class AuthUseCases(private val repository: AuthRepository) {
    // Inscription avec username
    suspend fun signUp(username: String): Resource<User> = repository.signUp(username)

    // Login avec username
    suspend fun login(username: String): Resource<User> = repository.login(username)

    // Déconnexion
    fun logout() = repository.logout()
}
