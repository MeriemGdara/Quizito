/**
 * Ce fichier contient l'interface AuthRepository et son implémentation.
 * Il sert de couche intermédiaire entre le DataSource (Firebase) et le Domain.
 */
package com.example.quizito.data.repositories

import com.example.quizito.common.Resource
import com.example.quizito.data.data_sources.remote.FirebaseAuthDataSource
import com.example.quizito.domain.models.User

// Interface pour le Repository
interface AuthRepository {
    suspend fun signUp(username: String): Resource<User>
    suspend fun login(username: String): Resource<User>
    fun logout()
}

// Implémentation du Repository en utilisant FirebaseAuthDataSource
class AuthRepositoryImpl(private val dataSource: FirebaseAuthDataSource) : AuthRepository {
    override suspend fun signUp(username: String): Resource<User> = dataSource.signUp(username)
    override suspend fun login(username: String): Resource<User> = dataSource.login(username)
    override fun logout() = dataSource.logout()
}
