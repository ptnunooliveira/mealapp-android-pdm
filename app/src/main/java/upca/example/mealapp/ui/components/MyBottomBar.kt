package upca.example.mealapp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import upca.example.mealapp.R
import upca.example.mealapp.ui.navigation.Routes
import upca.example.mealapp.ui.theme.MealAppTheme

data class BottomBarItem(
    val label: String,
    val icon: Int,
    val route: String
)

@Composable
fun MyBottomBar(
    selectedRoute: String?,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    
    val items = listOf(
        BottomBarItem("Home", R.drawable.ic_home, Routes.HOME),
        BottomBarItem("Categories", R.drawable.ic_categories, Routes.CATEGORIES),
        BottomBarItem("Surprise Me!", R.drawable.ic_dice_solid_full, Routes.SURPRISE)
    )

    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.route == selectedRoute,
                onClick = { onItemClick(item.route) },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                alwaysShowLabel = true
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyBottomBarPreview() {
    MealAppTheme {
        MyBottomBar(
            selectedRoute = "home",
            onItemClick = {}
        )
    }
}