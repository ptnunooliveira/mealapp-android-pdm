package upca.example.mealapp.ui.viewmodel.meallist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import upca.example.mealapp.data.repository.MealRepository

class MealListViewModel : ViewModel() {

    private val repository = MealRepository()
    private val _uiState = MutableStateFlow(MealListViewState())
    val uiState : StateFlow<MealListViewState> = _uiState.asStateFlow()

    fun fetchMeals(category: String) {

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null
        )

        repository.getMealsByCategory(
            category = category,
            onSuccess = { meals ->
                _uiState.value = _uiState.value.copy(
                    mealList = meals,
                    isLoading = false,
                    error = null
                )
            },
            onError = { message ->
                _uiState.value = _uiState.value.copy(
                    mealList = emptyList(),
                    isLoading = false,
                    error = message
                )
            }
        )
    }
}