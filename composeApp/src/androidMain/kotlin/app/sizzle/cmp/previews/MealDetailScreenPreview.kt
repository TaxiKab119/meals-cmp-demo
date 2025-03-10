package app.sizzle.cmp.previews

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.sizzle.cmp.meals.data.SampleMealsData
import app.sizzle.cmp.meals.presentation.meal_detail.MealDetailUiState
import app.sizzle.cmp.meals.presentation.meal_detail.MealsDetailScreen
import app.sizzle.cmp.meals.presentation.meals_list.ScreenState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview(showBackground = true)
fun MealDetailsScreenPreview() {
    MaterialTheme {
        MealsDetailScreen(
            uiState = MealDetailUiState(
                mealDetails = SampleMealsData.sampleMealDetails,
                screenState = ScreenState.SUCCESS
            ),
            modifier = Modifier.background(MaterialTheme.colors.background),
            onBackPress = {},
            sharedTransitionScope = TODO(),
            animatedContentScope = TODO(),
        )
    }
}