package upca.example.mealapp.ui.screen.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import upca.example.mealapp.data.model.Category
import upca.example.mealapp.ui.components.Tag
import upca.example.mealapp.ui.theme.MealAppTheme

@Composable
fun CategoryItem(
    category: Category,
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
            contentAlignment = Alignment.BottomCenter
        ){
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.onTertiary)
                    .height(200.dp),
                model = category.thumb,
                contentDescription = category.name
            )
            Tag(
                text = category.name,
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    MealAppTheme {
        Column {
            CategoryItem(
                category = Category(
                    name = "Beef",
                    id = "1",
                    thumb = "https://www.themealdb.com/images/category/beef.png"
                ),
                onClick = {}
            )
        }
    }
}