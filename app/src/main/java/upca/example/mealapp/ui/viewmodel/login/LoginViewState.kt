package upca.example.mealapp.ui.viewmodel.login

data class LoginViewState(
    val email : String = "",
    val password : String = "",
    val isLoading : Boolean = false,
    val error : String? = null
)
