package app.sizzle.cmp.previews

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.sizzle.cmp.data.SampleMealsData
import app.sizzle.cmp.ui.DisplayMealsScreen
import app.sizzle.cmp.ui.DisplayMealsUiState
import app.sizzle.cmp.ui.ScreenState

@Composable
@Preview
fun DisplayMealsScreenPreview() {
    MaterialTheme {
        DisplayMealsScreen(
            uiState = DisplayMealsUiState(
                screenState = ScreenState.SUCCESS,
                meals = SampleMealsData.sampleMeals
            )
        )
    }
}