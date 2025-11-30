/**
 * Ce fichier contient la classe générique Resource pour gérer l'état des données.
 * Elle peut représenter le succès, l'erreur ou le chargement d'une opération réseau.
 */
package com.example.quizito.common

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
){
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
    class Loading<T>: Resource<T>()
}
