package app.sizzle.cmp.previews

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.sizzle.cmp.meals.data.SampleMealsData
import app.sizzle.cmp.meals.presentation.meals_list.DisplayMealsScreen
import app.sizzle.cmp.meals.presentation.meals_list.DisplayMealsUiState
import app.sizzle.cmp.meals.presentation.meals_list.ScreenState

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