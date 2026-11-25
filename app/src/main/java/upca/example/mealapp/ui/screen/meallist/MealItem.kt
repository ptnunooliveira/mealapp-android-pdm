package upca.example.mealapp.ui.screen.meallist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import upca.example.mealapp.data.model.Meal
import upca.example.mealapp.ui.components.Tag
import upca.example.mealapp.ui.theme.MealAppTheme

@Composable
fun MealItem(
    meal: Meal,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable( onClick = onClick)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.onTertiary)
                    .height(200.dp),
                model = meal.thumb,
                contentDescription = meal.name,
                contentScale = ContentScale.Crop
            )
            meal.country?.let { country ->
                Tag(
                    text = "From: $country",
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
            }
        }
        Text(
            meal.name ?: "",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MealItemPreview() {
    MealAppTheme {
        Column() {
            MealItem(
                meal = Meal(
                    name = "Bacalhau à Brás",
                    id = "1",
                    country = "Portugal",
                    thumb = "https://www.themealdb.com/images/media/meals/jg4r991779916649.jpg"
                ),
                onClick = {}
            )
        }
    }
}

