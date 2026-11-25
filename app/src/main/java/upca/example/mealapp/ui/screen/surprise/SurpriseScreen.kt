package upca.example.mealapp.ui.screen.surprise

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import upca.example.mealapp.data.model.Ingredient
import upca.example.mealapp.data.model.MealDetail
import upca.example.mealapp.ui.screen.mealdetail.MealDetailContent
import upca.example.mealapp.ui.theme.MealAppTheme
import upca.example.mealapp.ui.viewmodel.mealdetail.MealDetailViewModel
import upca.example.mealapp.ui.viewmodel.mealdetail.MealDetailViewState


@Composable
fun SurpriseContent(
    uiState: MealDetailViewState,
    onFavoriteClick: () -> Unit,
    onClickSurprise: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        MealDetailContent(
            uiState = uiState,
            onFavoriteClick = onFavoriteClick
        )

        Button(
            onClick = onClickSurprise,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(
                text = "Surprise Me!"
            )
        }
    }

}


@Composable
fun SurpriseScreen(
    modifier: Modifier = Modifier,
    viewModel: MealDetailViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRandomMeal()
    }

    SurpriseContent(
        uiState = uiState,
        onClickSurprise = { viewModel.fetchRandomMeal() },
        onFavoriteClick = { viewModel.toggleFavorite() },
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun SurpriseContentPreview() {
    MealAppTheme {
        SurpriseContent(
            uiState = MealDetailViewState(
                isLoading = false,
                meal = MealDetail(
                    id = "52959",
                    name = "Baked salmon with fennel & tomatoes",
                    category = "Seafood",
                    country = "British",
                    instructions = "Heat oven to 180C. Trim the fronds from the fennel and set aside. " +
                            "Cut the fennel bulbs in half, then cut each half into 3 wedges. " +
                            "Cook in boiling salted water for 10 mins, then drain well.",
                    thumb = "https://www.themealdb.com/images/media/meals/1548772327.jpg",
                    youtube = null,
                    ingredients = listOf(
                        Ingredient(name = "Fennel", measure = "2 medium"),
                        Ingredient(name = "Parsley", measure = "1 tbs chopped"),
                        Ingredient(name = "Salmon", measure = "2 fillets"),
                        Ingredient(name = "Salt", measure = null)
                    ),
                    source = null
                ),
                error = null
            ),
            onClickSurprise = {},
            onFavoriteClick = {}
        )
    }
}