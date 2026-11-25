package upca.example.mealapp.ui.screen.mealdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import upca.example.mealapp.R
import upca.example.mealapp.data.model.Ingredient
import upca.example.mealapp.data.model.MealDetail
import upca.example.mealapp.ui.components.Tag
import upca.example.mealapp.ui.theme.MealAppTheme
import upca.example.mealapp.ui.viewmodel.mealdetail.MealDetailViewModel
import upca.example.mealapp.ui.viewmodel.mealdetail.MealDetailViewState


@Composable
fun MealDetailContent(
    uiState: MealDetailViewState,
    onFavoriteClick: () -> Unit,
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

            uiState.meal == null -> {
                Text(
                    "Details not found.",
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            else -> {
                MealDetailBody(
                    meal = uiState.meal,
                    isFavorite = uiState.isFavorite,
                    onFavoriteClick = onFavoriteClick,
                )
            }
        }
    }
}


@Composable
fun MealDetailScreen(
    id: String,
    modifier: Modifier = Modifier,
    viewModel: MealDetailViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(id) {
        viewModel.fetchMeal(id = id)
    }

    MealDetailContent(
        uiState = uiState,
        onFavoriteClick = { viewModel.toggleFavorite() },
        modifier = modifier
    )
}


@Composable
fun MealDetailBody(
    meal: MealDetail,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Box {
            AsyncImage(
                model = meal.thumb,
                contentDescription = meal.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = painterResource(
                        if (isFavorite) {
                            R.drawable.ic_full_heart
                        } else {
                            R.drawable.ic_empty_heart
                        }
                    ),
                    contentDescription = "Favorite"
                )
            }
        }

        Text(
            text = meal.name ?: "",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.End)
        ) {
            meal.category?.let { category ->
                Tag(text = category)
            }

            meal.country?.let { country ->
                Tag(text = country)
            }
        }

        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(16.dp, 4.dp)
        )

        meal.ingredients.forEach { ingredient ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp)
            ) {
                Text(
                    text = ingredient.name,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = ingredient.measure ?: "",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Text(
            text = "Instructions",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
        )

        Text(
            text = meal.instructions ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 24.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MealDetailPreview() {
    MealAppTheme {
        MealDetailContent(
            uiState = MealDetailViewState(
                isLoading = false,
                isFavorite = true,
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
                )
            ),
            onFavoriteClick = {}
        )
    }
}