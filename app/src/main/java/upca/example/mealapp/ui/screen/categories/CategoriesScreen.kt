package upca.example.mealapp.ui.screen.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import upca.example.mealapp.data.model.Category
import upca.example.mealapp.ui.theme.MealAppTheme
import upca.example.mealapp.ui.viewmodel.categories.CategoriesViewModel
import upca.example.mealapp.ui.viewmodel.categories.CategoriesViewState

@Composable
fun CategoriesListContent(
    uiState: CategoriesViewState,
    onCategoryClick: (String) -> Unit,
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
            uiState.categoriesList.isEmpty() -> {
                Text(
                    "No categories found.",
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(uiState.categoriesList) { category ->
                        CategoryItem(
                            category = category,
                            onClick = { onCategoryClick(category.name) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CategoriesListScreen(
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    CategoriesListContent(
        uiState = uiState,
        onCategoryClick = onCategoryClick,
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun CategoriesListSuccessPreview() {
    MealAppTheme {
        CategoriesListContent(
            uiState = CategoriesViewState(
                isLoading = false,
                categoriesList = listOf(
                    Category(
                        id = "1",
                        name = "Beef",
                        thumb = "https://www.themealdb.com/images/media/meals/jg4r991779916649.jpg"
                    ),
                    Category(
                        id = "2",
                        name = "Fish",
                        thumb = null
                    ),
                    Category(
                        id = "3",
                        name = "Dessert",
                        thumb = null
                    )
                )
            ),
            onCategoryClick = {}
        )
    }
}

@Preview(name = "Loading", showBackground = true)
@Composable
fun CategoriesListLoadingPreview() {
    MealAppTheme {
        CategoriesListContent(
            uiState = CategoriesViewState(),
            onCategoryClick = {}
        )
    }
}

@Preview(name = "Error", showBackground = true)
@Composable
fun CategoriesListErrorPreview() {
    MealAppTheme {
        CategoriesListContent(
            uiState = CategoriesViewState(
                isLoading = false,
                error = "HTTP error 500"
            ),
            onCategoryClick = {}
        )
    }
}

@Preview(name = "Empty", showBackground = true)
@Composable
fun CategoriesListEmptyPreview() {
    MealAppTheme {
        CategoriesListContent(
            uiState = CategoriesViewState(
                isLoading = false
            ),
            onCategoryClick = {}
        )
    }
}