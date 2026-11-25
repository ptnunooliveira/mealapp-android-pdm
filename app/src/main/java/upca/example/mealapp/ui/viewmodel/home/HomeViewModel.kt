package upca.example.mealapp.ui.viewmodel.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import upca.example.mealapp.data.repository.AuthRepository
import upca.example.mealapp.data.repository.FavoritesRepository

class HomeViewModel : ViewModel() {

    private val repository = AuthRepository()
    private val favoritesRepository = FavoritesRepository()
    private val _uiState = MutableStateFlow(HomeViewState())
    val uiState : StateFlow<HomeViewState> = _uiState.asStateFlow()

    fun fetchUser() {

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null
        )

        repository.getCurrentUser(
            onSuccess = { user ->
                _uiState.value = _uiState.value.copy(
                    user = user,
                    isLoading = false,
                    error = null
                )
            },
            onError = { message ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = message
                )
            }
        )
    }


    fun fetchFavorites() {

        favoritesRepository.getFavorites(
            onSuccess = { favorites ->
                _uiState.value = _uiState.value.copy(
                    favorites = favorites
                )
            },
            onError = {}
        )
    }
}