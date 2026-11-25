package upca.example.mealapp.ui.viewmodel.home

import upca.example.mealapp.data.model.Meal
import upca.example.mealapp.data.model.User

data class HomeViewState(
    val user: User? = null,
    val favorites: List<Meal> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
