package app.sizzle.cmp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.sizzle.cmp.data.Meal
import app.sizzle.cmp.data.SampleMealsData
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DisplayMealsRoot(
    viewModel: DisplayMealsViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.displayMealsUiState.collectAsStateWithLifecycle(initialValue = DisplayMealsUiState())
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        when(uiState.screenState) {
            ScreenState.LOADING -> LoadingScreen()
            ScreenState.ERROR -> ErrorScreen() { viewModel.getMealsData() }
            ScreenState.SUCCESS -> {
                DisplayMealsScreen(
                    uiState = uiState,
                    modifier = modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun DisplayMealsScreen(
    uiState: DisplayMealsUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(uiState.meals) { meal ->
            MealItem(meal)
        }
    }
}

@Composable
fun MealItem(meal: Meal) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        val context = LocalPlatformContext.current
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Display the meal image
            Text(meal.strMealThumb)
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(meal.strMealThumb)
                    .crossfade(true)
                    .build(),
                contentDescription = meal.strMeal,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Meal name
            Text(
                text = meal.strMeal,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}




@Composable
fun ErrorScreen(onRetry: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "An error occurred while loading meals.")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onRetry() }) {
                Text("Retry")
            }
        }
    }
}



@Preview
@Composable
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