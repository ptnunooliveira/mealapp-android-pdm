package upca.example.mealapp.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import upca.example.mealapp.data.model.User


class AuthRepository {

    private val auth = Firebase.auth
    private val db = Firebase.firestore


    fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")

                    val id = auth.currentUser?.uid

                    if (id == null) {
                        onError("Could not get user id")
                    } else {
                        val userDoc = User(
                            id = id,
                            name = name,
                            email = email
                        )

                        db.collection("users")
                            .document(id)
                            .set(userDoc)

                        onSuccess(userDoc)
                    }
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    onError(task.exception?.message ?: "Registration failed")
                }
            }
    }


    fun login(
        email: String,
        password: String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmailAndPassword:success")
                    getCurrentUser(onSuccess, onError)
                } else {
                    Log.w(TAG, "signInWithEmailAndPassword:failure", task.exception)
                    onError(task.exception?.message ?: "Login failed")
                }
            }
    }


    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }


    fun logout() {
        auth.signOut()
    }


    fun getCurrentUser(
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit,
    ) {

        val id = auth.currentUser?.uid

        if (id == null) {
            onError("Could not find the user")
        } else {
            db.collection("users")
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                    val user = document.toObject<User>()
                    if (user == null) {
                        onError("Could not get user details")
                    } else {
                        onSuccess(user)
                    }
                }
                .addOnFailureListener { e ->
                    onError(e.message ?: "Could not load user")
                }
        }
    }
}