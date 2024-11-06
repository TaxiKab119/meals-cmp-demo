package app.sizzle.cmp.meals.presentation.meals_list

import androidx.compose.runtime.Immutable
import app.sizzle.cmp.meals.data.Meal

@Immutable
data class DisplayMealsUiState(
    val meals: List<Meal> = listOf(),
    val screenState: ScreenState = ScreenState.SUCCESS
)

enum class ScreenState {
    LOADING, SUCCESS, ERROR
}
