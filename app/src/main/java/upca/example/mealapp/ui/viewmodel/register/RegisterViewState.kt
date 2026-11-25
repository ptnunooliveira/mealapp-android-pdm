package upca.example.mealapp.ui.viewmodel.register

data class RegisterViewState(
    val email : String = "",
    val password : String = "",
    val name : String = "",
    val isLoading : Boolean = false,
    val error : String? = null
)
