package app.sizzle.cmp.meals.presentation.meal_detail

import androidx.compose.runtime.Immutable
import app.sizzle.cmp.meals.data.MealDetails
import app.sizzle.cmp.meals.presentation.meals_list.ScreenState

@Immutable
data class MealDetailUiState(
    val mealDetails: MealDetails,
    val screenState: ScreenState = ScreenState.LOADING
)