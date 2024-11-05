package app.sizzle.cmp.meals.presentation.meals_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sizzle.cmp.meals.data.MealsDbClient
import app.sizzle.cmp.util.onError
import app.sizzle.cmp.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DisplayMealsViewModel(private val client: MealsDbClient) : ViewModel() {
    private val _displayMealsUiState = MutableStateFlow(DisplayMealsUiState())
    val displayMealsUiState = _displayMealsUiState
        .onStart { getMealsData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            DisplayMealsUiState(
                screenState = ScreenState.LOADING
            )
        )


    fun getMealsData() {
        viewModelScope.launch {
            client.getBeefMeals()
                .onSuccess { apiResponse ->
                    _displayMealsUiState.update {
                        it.copy(
                            screenState = ScreenState.SUCCESS,
                            meals = apiResponse.meals
                        )
                    }
                }
                .onError {
                    _displayMealsUiState.update {
                        it.copy(
                            screenState = ScreenState.ERROR,
                        )
                    }
                }
        }
    }
}