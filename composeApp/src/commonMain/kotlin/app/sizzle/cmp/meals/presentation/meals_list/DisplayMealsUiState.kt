package app.sizzle.cmp.meals.presentation.meals_list

import app.sizzle.cmp.meals.data.Meal

data class DisplayMealsUiState(
    val meals: List<Meal> = listOf(),
    val screenState: ScreenState = ScreenState.LOADING
)

enum class ScreenState {
    LOADING, SUCCESS, ERROR
}
