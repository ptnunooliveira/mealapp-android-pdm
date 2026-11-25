package upca.example.mealapp.ui.viewmodel.categories

import upca.example.mealapp.data.model.Category

data class CategoriesViewState(
    val categoriesList: List<Category> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)