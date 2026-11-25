package upca.example.mealapp.ui.screen.meallist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.viewmodel.compose.viewModel
import upca.example.mealapp.data.model.Meal
import upca.example.mealapp.ui.theme.MealAppTheme
import upca.example.mealapp.ui.viewmodel.meallist.MealListViewModel
import upca.example.mealapp.ui.viewmodel.meallist.MealListViewState


@Composable
fun MealListScreen(
    category: String,
    onMealClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MealListViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(category) {
        viewModel.fetchMeals(
            category = category
        )
    }

    MealListContent(
        uiState = uiState,
        onMealClick = onMealClick,
        modifier = modifier
    )
}


@Composable
fun MealListContent(
    uiState: MealListViewState,
    onMealClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.error != null -> {
                Text(text = uiState.error)
            }
            uiState.mealList.isEmpty() -> {
                Text(
                    "No meals found.",
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            else -> {
                LazyColumn {
                    items(uiState.mealList) { meal ->
                        MealItem(
                            meal = meal,
                            onClick = {
                                onMealClick(meal.id)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MealListSuccessPreview() {
    MealAppTheme {
        MealListContent(
            uiState = MealListViewState(
                isLoading = false,
                mealList = listOf(
                    Meal(
                        id = "1",
                        name = "Bacalhau à Brás",
                        thumb = "https://www.themealdb.com/images/media/meals/jg4r991779916649.jpg",
                        country = "Portugal"
                    ),
                    Meal(
                        id = "2",
                        name = "Baked salmon with fennel & tomatoes",
                        thumb = null,
                        country = null
                    ),
                    Meal(
                        id = "3",
                        name = "Cajun spiced fish tacos",
                        thumb = null,
                        country = "Mexico"
                    )
                )
            ),
            onMealClick = {}
        )
    }
}

@Preview(name = "Loading", showBackground = true)
@Composable
fun MealListLoadingPreview() {
    MealAppTheme {
        MealListContent(
            uiState = MealListViewState(),
            onMealClick = {}
        )
    }
}

@Preview(name = "Error", showBackground = true)
@Composable
fun MealListErrorPreview() {
    MealAppTheme {
        MealListContent(
            uiState = MealListViewState(
                isLoading = false,
                error = "HTTP error 500"
            ),
            onMealClick = {}
        )
    }
}

@Preview(name = "Empty", showBackground = true)
@Composable
fun MealListEmptyPreview() {
    MealAppTheme {
        MealListContent(
            uiState = MealListViewState(
                isLoading = false
            ),
            onMealClick = {}
        )
    }
}