package upca.example.mealapp.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import upca.example.mealapp.data.model.Meal

class FavoritesRepository {

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    fun addFavorite(
        meal: Meal,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val id = auth.currentUser?.uid

        if(id == null) {
            onError("User not found")
        } else {
            db.collection("users")
                .document(id)
                .collection("favorites")
                .document(meal.id)
                .set(meal)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { e ->
                    onError(e.message ?: "Could not save favorite")
                }
        }
    }


    fun removeFavorite(
        idMeal: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val id = auth.currentUser?.uid

        if(id == null) {
            onError("User not found")
        } else {
            db.collection("users")
                .document(id)
                .collection("favorites")
                .document(idMeal)
                .delete()
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { e ->
                    onError(e.message ?: "Could not delete favorite")
                }
        }
    }


    fun isFavorite(
        idMeal: String,
        onResult: (Boolean) -> Unit,
        onError: (String) -> Unit
    ) {

        val id = auth.currentUser?.uid

        if(id == null) {
            onError("User not found")
        } else {
            db.collection("users")
                .document(id)
                .collection("favorites")
                .document(idMeal)
                .get()
                .addOnSuccessListener { document ->
                    onResult(document.exists())
                }
                .addOnFailureListener { e ->
                    onError(e.message ?: "Could not check favorite")
                }
        }
    }


    fun getFavorites(
        onSuccess: (List<Meal>) -> Unit,
        onError: (String) -> Unit
    ) {

        val id = auth.currentUser?.uid

        if(id == null) {
            onError("User not found")
        } else {
            db.collection("users")
                .document(id)
                .collection("favorites")
                .get()
                .addOnSuccessListener { result ->

                    val favorites = mutableListOf<Meal>()
                    for(doc in result.documents) {
                        val meal = Meal(
                            id = doc.id,
                            name = doc.getString("name"),
                            thumb = doc.getString("thumb"),
                            country = doc.getString("country")
                        )
                        favorites.add(meal)
                    }
                    onSuccess(favorites)
                }
                .addOnFailureListener { e ->
                    onError(e.message ?: "Could not load favorite")
                }
        }
    }
}