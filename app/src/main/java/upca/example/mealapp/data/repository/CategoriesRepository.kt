package upca.example.mealapp.data.repository

import upca.example.mealapp.data.model.Category
import upca.example.mealapp.data.remote.MealApi

class CategoriesRepository {

    fun getCategories(
        onSuccess: (List<Category>) -> Unit,
        onError: (String) -> Unit
    ) {

        MealApi.getCategories(
            onSuccess = { categories ->
                val sortedCategories = categories.sortedBy { category -> category.name }
                onSuccess(sortedCategories)
            },
            onError = onError
        )
    }
}