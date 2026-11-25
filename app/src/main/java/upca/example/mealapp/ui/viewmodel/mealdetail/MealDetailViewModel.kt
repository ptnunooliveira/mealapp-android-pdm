package upca.example.mealapp.ui.viewmodel.mealdetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import upca.example.mealapp.data.model.Meal
import upca.example.mealapp.data.repository.FavoritesRepository
import upca.example.mealapp.data.repository.MealRepository

class MealDetailViewModel: ViewModel() {

    private val repository = MealRepository()
    private val favoritesRepository = FavoritesRepository()
    private val _uiState = MutableStateFlow(MealDetailViewState())
    val uiState : StateFlow<MealDetailViewState> = _uiState.asStateFlow()

    fun fetchMeal(id: String) {

        _uiState.value = _uiState.value.copy(
            meal = null,
            isFavorite = false,
            isLoading = true,
            error = null
        )

        repository.getMealById(
            id = id,
            onSuccess = { mealDetail ->
                _uiState.value = _uiState.value.copy(
                    meal = mealDetail,
                    isLoading = false,
                    error = null
                )
                checkFavorite(mealDetail.id)
            },
            onError = { message ->
                _uiState.value = _uiState.value.copy(
                    meal = null,
                    isLoading = false,
                    error = message
                )
            }
        )
    }

    fun fetchRandomMeal() {

        _uiState.value = _uiState.value.copy(
            meal = null,
            isFavorite = false,
            isLoading = true,
            error = null
        )

        repository.getRandomMeal(
            onSuccess = { mealDetail ->
                _uiState.value = _uiState.value.copy(
                    meal = mealDetail,
                    isLoading = false,
                    error = null
                )
                checkFavorite(mealDetail.id)
            },
            onError = { message ->
                _uiState.value = _uiState.value.copy(
                    meal = null,
                    isLoading = false,
                    error = message
                )
            }
        )
    }


    private fun checkFavorite(
        idMeal: String
    ) {

        favoritesRepository.isFavorite(
            idMeal = idMeal,
            onResult = { result ->
                _uiState.value = _uiState.value.copy(
                    isFavorite = result,
                )
            },
            onError = {}
        )
    }


    fun toggleFavorite() {
        val meal = _uiState.value.meal
        if (meal == null) return

        if(_uiState.value.isFavorite) {
            favoritesRepository.removeFavorite(
                idMeal = meal.id,
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        isFavorite = false
                    )
                },
                onError = {}
            )
        } else {

            val favorite = Meal(
                id = meal.id,
                name = meal.name,
                thumb = meal.thumb,
                country = meal.country
            )

            favoritesRepository.addFavorite(
                meal = favorite,
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        isFavorite = true
                    )
                },
                onError = {}
            )
        }
    }
}