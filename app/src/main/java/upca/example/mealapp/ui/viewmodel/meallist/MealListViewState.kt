package upca.example.mealapp.ui.viewmodel.meallist

import upca.example.mealapp.data.model.Meal

data class MealListViewState(
    val mealList: List<Meal> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)