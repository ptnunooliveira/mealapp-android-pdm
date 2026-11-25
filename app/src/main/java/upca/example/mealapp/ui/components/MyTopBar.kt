package upca.example.mealapp.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import upca.example.mealapp.R
import upca.example.mealapp.ui.theme.MealAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String,
    onBackClick: () -> Unit,
    onLogOutClick: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = false
) {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back_arrow),
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onLogOutClick) {
                IconButton(
                    onClick = onLogOutClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.right_from_bracket_solid_full),
                        contentDescription = "Logout"
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun MyTopBarPreview() {
    MealAppTheme {
        MyTopBar(
            title = "MealApp",
            canNavigateBack = false,
            onBackClick = {},
            onLogOutClick = {}
        )
    }
}