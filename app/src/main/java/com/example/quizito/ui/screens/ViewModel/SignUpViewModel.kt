package com.example.quizito.ui.screens.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

class SignUpViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun signUp(username: String, onResult: (Boolean, String) -> Unit, onAuthSuccess: () -> Unit) {
        val email = "$username@quizito.com"
        val password = "defaultPassword123"

        Log.d("SignUpViewModel", "Début signUp pour username: $username")

        // Vérifier si le username existe déjà
        db.collection("users")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { query ->
                Log.d("SignUpViewModel", "Query Firestore réussie, documents trouvés: ${query.size()}")
                if (!query.isEmpty) {
                    onResult(false, "Username déjà utilisé")
                } else {
                    // Créer compte Firebase Auth
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val uid = auth.currentUser?.uid ?: ""
                                Log.d("SignUpViewModel", "Auth réussie, UID: $uid")

                                // 🔥 Naviguer immédiatement après Auth succès (utilisateur connecté)
                                onAuthSuccess()

                                // Stocker dans Firestore en arrière-plan
                                val user = hashMapOf(
                                    "username" to username,
                                    "createdAt" to FieldValue.serverTimestamp()
                                )
                                db.collection("users").document(uid)
                                    .set(user)
                                    .addOnSuccessListener {
                                        Log.d("SignUpViewModel", "Firestore set réussie")
                                        onResult(true, "Utilisateur créé avec succès")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("SignUpViewModel", "Erreur Firestore: ${e.message}")
                                        onResult(false, "Erreur Firestore: ${e.message}")  // Avertissement, mais navigation déjà faite
                                    }
                            } else {
                                Log.e("SignUpViewModel", "Erreur Auth: ${task.exception?.message}")
                                onResult(false, "Erreur Auth: ${task.exception?.message}")
                            }
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("SignUpViewModel", "Erreur query Firestore: ${e.message}")
                onResult(false, "Erreur Firestore: ${e.message}")
            }
    }
}