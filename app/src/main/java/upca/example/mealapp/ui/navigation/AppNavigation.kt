package upca.example.mealapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import upca.example.mealapp.ui.screen.categories.CategoriesListScreen
import upca.example.mealapp.ui.screen.home.HomeScreen
import upca.example.mealapp.ui.screen.login.LoginScreen
import upca.example.mealapp.ui.screen.mealdetail.MealDetailScreen
import upca.example.mealapp.ui.screen.meallist.MealListScreen
import upca.example.mealapp.ui.screen.register.RegisterScreen
import upca.example.mealapp.ui.screen.surprise.SurpriseScreen


object Routes {
    const val LIST = "list/{category}"
    const val DETAIL = "detail/{id}"
    const val HOME = "home"
    const val SURPRISE = "surprise"
    const val CATEGORIES = "categories"
    const val LOGIN = "login"
    const val REGISTER = "register"


    fun list(category: String) = "list/$category"
    fun detail(id: String) = "detail/$id"
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(Routes.LIST) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "Seafood"

            MealListScreen(
                category = category,
                onMealClick = { id ->
                    navController.navigate(Routes.detail(id))
                }
            )
        }

        composable(Routes.DETAIL) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""

            MealDetailScreen(
                id = id
            )
        }

        composable(Routes.HOME) {
            HomeScreen(onMealClick = { mealDetail ->
                navController.navigate(Routes.DETAIL)
            })
        }

        composable(Routes.SURPRISE) {
            SurpriseScreen()
        }

        composable(Routes.CATEGORIES) {
            CategoriesListScreen(
                onCategoryClick = { category ->
                    navController.navigate(Routes.list(category))
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}