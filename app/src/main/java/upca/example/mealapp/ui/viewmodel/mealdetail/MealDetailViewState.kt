package upca.example.mealapp.ui.viewmodel.mealdetail

import upca.example.mealapp.data.model.MealDetail

data class MealDetailViewState(
    val meal: MealDetail? = null,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null
)