/**
 * Ce fichier définit le modèle User utilisé dans l'application.
 * Ici, chaque utilisateur a seulement un id unique et un username unique.
 */
package com.example.quizito.domain.models

data class User(
    val id: String,
    val username: String
)