package app.sizzle.cmp.meals.presentation.meal_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sizzle.cmp.meals.data.MealDetails
import app.sizzle.cmp.meals.data.MealsDbClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MealDetailViewModel(private val client: MealsDbClient): ViewModel() {
    private val initialMealDetailsState = MealDetails(
        idMeal = "",
        strMeal = "",
        strCategory = "",
        strArea = "",
        strInstructions = "",
        strMealThumb = "",
        strIngredient1 = null,
        strIngredient2 = null,
        strIngredient3 = null,
        strIngredient4 = null,
        strIngredient5 = null,
        strIngredient6 = null,
        strIngredient7 = null,
        strIngredient8 = null,
        strIngredient9 = null
    )

    private val _mealDetailUiState = MutableStateFlow(
        MealDetailUiState(
            mealDetails = initialMealDetailsState
        )
    )
    val mealDetailUiState = _mealDetailUiState
        .onStart { loadMealDetailData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            MealDetailUiState(initialMealDetailsState)
        )

    fun loadMealDetailData() {
        viewModelScope.launch {
            //TODO
        }
    }
}