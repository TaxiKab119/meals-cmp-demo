package app.sizzle.cmp.meals.presentation.meal_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MealDetailRoot(
    viewModel: MealDetailViewModel,
    mealId: String,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.mealDetailUiState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        MealsDetailScreen(
            uiState = uiState,
            mealId = mealId,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun MealsDetailScreen(
    uiState: MealDetailUiState,
    mealId: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("You have reached the Meal Detail Screen for Meal: $mealId")
    }
}