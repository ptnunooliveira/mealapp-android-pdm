package upca.example.mealapp.ui.viewmodel.categories

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import upca.example.mealapp.data.repository.CategoriesRepository

class CategoriesViewModel : ViewModel() {

    private val repository = CategoriesRepository()
    private val _uiState = MutableStateFlow(CategoriesViewState())
    val uiState: StateFlow<CategoriesViewState> = _uiState.asStateFlow()

    fun fetchCategories() {

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null
        )

        repository.getCategories(
            onSuccess = { categories ->
                _uiState.value = _uiState.value.copy(
                    categoriesList = categories,
                    isLoading = false,
                    error = null
                )
            },
            onError = { message ->
                _uiState.value = _uiState.value.copy(
                    categoriesList = emptyList(),
                    isLoading = false,
                    error = message
                )
            }
        )
    }
}