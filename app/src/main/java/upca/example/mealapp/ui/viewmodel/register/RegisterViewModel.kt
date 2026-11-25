package upca.example.mealapp.ui.viewmodel.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import upca.example.mealapp.data.repository.AuthRepository

class RegisterViewModel : ViewModel() {

    private val repository = AuthRepository()
    private val _uiState = MutableStateFlow(RegisterViewState())
    val uiState : StateFlow<RegisterViewState> = _uiState.asStateFlow()

    fun updateName(
        name : String
    ){
      _uiState.value = _uiState.value.copy(
          name = name,
          error = null,
          isLoading = false
      )
    }

    fun updateEmail(email : String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            error = null,
            isLoading = false
        )
    }

    fun updatePassword(password : String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            error = null,
            isLoading = false
        )
    }

    fun register(
        onSuccess: () -> Unit
    ) {

        if(_uiState.value.name.isBlank()) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = "Name is required"
            )
            return
        }

        if(_uiState.value.email.isBlank()) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = "Email is required"
            )
            return
        }

        if(_uiState.value.password.isBlank()) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = "Password is required"
            )
            return
        }

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null
        )

        repository.register(
            name = _uiState.value.name,
            email = _uiState.value.email,
            password = _uiState.value.password,
            onSuccess = { _ ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
                onSuccess()
            },
            onError = { message ->
                _uiState.value = _uiState.value.copy(
                    error = message,
                    isLoading = false
                )
            }
        )
    }
}