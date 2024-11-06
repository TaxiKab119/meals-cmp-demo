package app.sizzle.cmp.meals.presentation.meal_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun MealDetailRoot(
    viewModel: MealDetailViewModel,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.mealDetailUiState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        MealsDetailScreen(
            uiState = uiState,
            modifier = Modifier.padding(innerPadding),
            onClose = onClose
        )
    }
}

@Composable
fun MealsDetailScreen(
    uiState: MealDetailUiState,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Meal image that fills the top portion of the screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Display meal image
            AsyncImage(
                model = uiState.mealDetails.strMealThumb,
                contentDescription = uiState.mealDetails.strMeal,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Gray) // Placeholder background
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display meal title, category, and area
            Text(
                text = uiState.mealDetails.strMeal,
                style = MaterialTheme.typography.h2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "${uiState.mealDetails.strCategory} - ${uiState.mealDetails.strArea}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display instructions
            Text(
                text = uiState.mealDetails.idMeal,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = uiState.mealDetails.strInstructions,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display ingredients
            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )

            val ingredients = listOf(
                uiState.mealDetails.strIngredient1,
                uiState.mealDetails.strIngredient2,
                uiState.mealDetails.strIngredient3,
                uiState.mealDetails.strIngredient4,
                uiState.mealDetails.strIngredient5,
                uiState.mealDetails.strIngredient6,
                uiState.mealDetails.strIngredient7,
                uiState.mealDetails.strIngredient8,
                uiState.mealDetails.strIngredient9
            ).filterNotNull()

            ingredients.forEach { ingredient ->
                Text(
                    text = ingredient,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        // Transparent TopAppBar over the image
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
            Spacer(Modifier.weight(1f)) // Pushes the icon to the start
        }
    }
}