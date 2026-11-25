package upca.example.mealapp.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import upca.example.mealapp.data.model.Meal
import upca.example.mealapp.data.model.User
import upca.example.mealapp.ui.screen.meallist.MealItem
import upca.example.mealapp.ui.theme.MealAppTheme
import upca.example.mealapp.ui.viewmodel.home.HomeViewModel
import upca.example.mealapp.ui.viewmodel.home.HomeViewState

@Composable
fun HomeContent(
    uiState: HomeViewState,
    onMealClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.error != null -> {
                Text(text = uiState.error)
            }

            uiState.user != null -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Welcome, ${uiState.user.name ?: "chef"}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "Your favorite recipes",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    if (uiState.favorites.isEmpty()) {
                        Text(
                            text = "No favorites yet. Tap ❤ on a recipe!",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 32.dp)
                        )
                    } else {
                        LazyColumn {
                            items(uiState.favorites) { meal ->
                                MealItem(
                                    meal = meal,
                                    onClick = { onMealClick(meal.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun HomeScreen(
    onMealClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUser()
        viewModel.fetchFavorites()
    }

    HomeContent(
        uiState = uiState,
        onMealClick = onMealClick,
        modifier = modifier
    )
}


@Preview(showBackground = true, heightDp = 800)
@Composable
fun HomeScreenPreview() {
    MealAppTheme {
        HomeContent(
            uiState = HomeViewState(
                isLoading = false,
                user = User(name = "Nuno"),
                favorites = listOf(
                    Meal(
                        id = "1",
                        name = "Bacalhau à Brás",
                        thumb = null,
                        country = "Portugal"
                    ),
                    Meal(
                        id = "2",
                        name = "Baked salmon with fennel & tomatoes",
                        thumb = null,
                        country = "British"
                    )
                )
            ),
            onMealClick = {}
        )
    }
}


@Preview(showBackground = true, heightDp = 800)
@Composable
fun HomeScreenPreviewEmpty() {
    MealAppTheme {
        HomeContent(
            uiState = HomeViewState(
                isLoading = false,
                user = User(name = "Nuno"),
                favorites = emptyList()
            ),
            onMealClick = {}
        )
    }
}