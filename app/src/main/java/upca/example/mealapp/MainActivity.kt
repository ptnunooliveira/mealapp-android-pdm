package upca.example.mealapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import upca.example.mealapp.data.repository.AuthRepository
import upca.example.mealapp.ui.components.MyBottomBar
import upca.example.mealapp.ui.components.MyTopBar
import upca.example.mealapp.ui.navigation.AppNavigation
import upca.example.mealapp.ui.navigation.Routes
import upca.example.mealapp.ui.theme.MealAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentEntry by navController.currentBackStackEntryAsState()
            val currentRoute = currentEntry?.destination?.route
            val canNavigateBack = currentRoute == Routes.DETAIL || currentRoute == Routes.LIST
            val isAuthScreen = currentRoute == Routes.LOGIN || currentRoute == Routes.REGISTER
            val title = when (currentRoute) {
                Routes.DETAIL -> "Recipe"
                Routes.LIST -> currentEntry?.arguments?.getString("category") ?: "Meals"
                else -> "MealApp"
            }
            val selectedTab = when (currentRoute) {
                Routes.DETAIL -> Routes.CATEGORIES
                Routes.LIST -> Routes.CATEGORIES
                else -> currentRoute
            }
            val startDestination = remember {
                if(AuthRepository().isLoggedIn()) Routes.HOME else Routes.LOGIN
            }

            MealAppTheme {
                Scaffold(
                    topBar = {
                        if(!isAuthScreen) {
                            MyTopBar(
                                title = title,
                                canNavigateBack = canNavigateBack,
                                onBackClick = { navController.popBackStack() },
                                onLogOutClick = {
                                    AuthRepository().logout()
                                    navController.navigate(Routes.LOGIN) {
                                        popUpTo(navController.graph.id) { inclusive = true }
                                    }
                                }
                            )
                        }
                    },
                    bottomBar = {
                        if(!isAuthScreen) {
                            MyBottomBar(
                                selectedRoute = selectedTab,
                                onItemClick = { route ->
                                    navController.navigate(route) {
                                        popUpTo(Routes.HOME) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
