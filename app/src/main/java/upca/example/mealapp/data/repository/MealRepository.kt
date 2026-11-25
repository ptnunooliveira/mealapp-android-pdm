package upca.example.mealapp.data.repository

import upca.example.mealapp.data.model.Meal
import upca.example.mealapp.data.model.MealDetail
import upca.example.mealapp.data.remote.MealApi

class MealRepository {

    fun getMealsByCategory(
        category: String,
        onSuccess : (List<Meal>) -> Unit,
        onError : (String) -> Unit
    ) {

        MealApi.getMealsByCategory(
            category = category,
            onSuccess = { meals ->
                val sortedMeals = meals.sortedBy { meal -> meal.name }
                onSuccess(sortedMeals)
            },
            onError = onError)
    }


    fun getMealById(
        id: String,
        onSuccess: (MealDetail) -> Unit,
        onError: (String) -> Unit
    ) {

        MealApi.getMealById(
            id = id,
            onSuccess = onSuccess,
            onError = onError
        )
    }

    fun getRandomMeal(
        onSuccess: (MealDetail) -> Unit,
        onError: (String) -> Unit
    ) {

        MealApi.getRandomMeal(
            onSuccess = onSuccess,
            onError = onError
        )
    }
}