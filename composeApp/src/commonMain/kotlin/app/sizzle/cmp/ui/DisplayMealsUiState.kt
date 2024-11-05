package app.sizzle.cmp.ui

import app.sizzle.cmp.data.Meal

data class DisplayMealsUiState(
    val meals: List<Meal> = listOf(),
    val screenState: ScreenState = ScreenState.LOADING
)

enum class ScreenState {
    LOADING, SUCCESS, ERROR
}
